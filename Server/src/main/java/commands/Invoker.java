package commands;

import commands.consoleCommands.*;

import connection.Connection;
import connection.DatagramConnection;
import exceptions.InvalidArgsException;
import responces.ResponseBuilder;

import parameters.MusicBand;

import java.io.IOException;
import java.util.*;

/**
 * This class is necessary for reading and executing supported commands
 */

public class Invoker {
    public static final Map<String, Command> commands = new HashMap<>();
    private static final Map<String, Integer> intoFiles = new HashMap<>();

    public static Receiver receiver;


    public boolean isScriptRunning = false;

    static Command command;

    public static String[] arguments;

    public static String commandWord;
    public static Connection manager;
    public static ResponseBuilder builder;

    public Invoker(String command, String[] arguments, Receiver receiver, Connection manager, ResponseBuilder builder) {
        this.commandWord = command;
        this.arguments = arguments;
        this.manager = manager;
        this.builder = builder;
        commands.put("help", new Help(receiver));
        commands.put("add", new Add(receiver));
        commands.put("show", new Show(receiver));
        commands.put("exit", new Exit(receiver));
        commands.put("clear", new Clear(receiver));
        commands.put("reorder", new Reorder(receiver));
        commands.put("shuffle", new Shuffle(receiver));
        commands.put("sort", new Sort(receiver));
        commands.put("history", new History(receiver));
        commands.put("remove_by_id", new RemoveById(receiver));
        commands.put("update", new Update(receiver));
        commands.put("execute_script", new Execute(receiver));
        commands.put("average_of_number_of_participants", new Average(receiver));
        commands.put("average_of_nop", new Average(receiver));
        commands.put("info", new Info(receiver));
        commands.put("filter_by_genre", new Filter(receiver));
        commands.put("print_field_descending_genre", new PrintGenre(receiver));
        commands.put("save", new Save(receiver));
        commands.put("помогите", new Help(receiver));
    }

    static ArrayList<String> lastCommands = new ArrayList<String>();

    public static ArrayList<String> getLastCommands() {
        return lastCommands;
    }


    public static int inputsCount = 0;

    /*
     *This class has a static method push which reads commands
     *and executes them.
     */
    public static void push(ArrayList<MusicBand> list, String path, boolean isScriptRunning, Object object) throws InvalidArgsException, IOException {
        if(commands.containsKey(commandWord)) {
            if(lastCommands.size() == 15) {
                lastCommands.remove(0);
                lastCommands.add(commandWord);
            } else {
                lastCommands.add(commandWord);
            }
            command = commands.get(commandWord);
            try {
                    if (command.getArgs().length == arguments.length) {
                        command.execute(arguments, path, isScriptRunning, object);
                    } else {
                        String commandArgs = "";
                        for(int i = 0; i <= command.getArgs().length-1; i++) {
                            if(i != command.getArgs().length-1) {
                                commandArgs = commandArgs + command.getArgs()[i] + ", ";
                            } else {
                                commandArgs = commandArgs + command.getArgs()[i];
                            }
                        }
                        manager.send(builder.createResponse("Количество аргументов некорректно. Введено: " + arguments.length + ", необходимо: " + command.getArgs().length + " (" + commandArgs + ").", false, true));
                        System.out.println("Количество аргументов некорректно. Введено: " + arguments.length + ", необходимо: " + command.getArgs().length + " (" + commandArgs + ").");
                    }
                } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.print("Неизвестная команда " + commandWord + ". Введите help для просмотра списка доступных команд.\n");
            manager.send(builder.createResponse("Неизвестная команда " + commandWord + ". Введите help для просмотра списка доступных команд.\n", false, true));
        }

    }

    public static boolean isContains(String name){
        return commands.containsKey(name);
    }

    public static Command getCommand(String name){
        return commands.get(name);
    }

    public Map<String, Command> getCommands(){
        return commands;
    }
}
