package tools;

import exceptions.InvalidArgsException;
import responces.CommandType;
import java.util.Map;
import java.util.Objects;

public class CommandChecker {
    public boolean isObjectRequired(Map<String, Object[]> commands, String command) {
        if (commands.containsKey(command)) {
            return Objects.equals(commands.get(command)[0], CommandType.OBJECT);
        } else {
            return false;
        }
    }

    public boolean isPostObjectRequired(Map<String, Object[]> commands, String command) {
        if (commands.containsKey(command)) {
            return Objects.equals(commands.get(command)[0], CommandType.POST_OBJECT);
        } else {
            return false;
        }
    }

    public boolean isExit(Map<String, Object[]> commands, String command) {
        if (commands.containsKey(command)) {
            return Objects.equals(commands.get(command)[0], CommandType.EXIT);
        } else {
            return false;
        }
    }

    public boolean isExecute(Map<String, Object[]> commands, String command){
        if (commands.containsKey(command.split(" ")[0])) {
            if (Objects.equals(commands.get(command)[0], CommandType.EXECUTE)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isCorrectArgs(String[] needArgs, String[] providedArgs) throws InvalidArgsException {
        if (needArgs.length != providedArgs.length) throw new InvalidArgsException("Некорректное количество аргументов команды.");
        return true;
    }

    public boolean isCorrect(Map<String, Object[]> commands, String command, String[] arguments) {
        if(commands.containsKey(command)) {
            if ((Integer) commands.get(command)[1] == arguments.length) {
                //logger.info("Запуск выполнения команды.");
                return true;
            } else {
                //logger.warn("Введено некорректоное количество аргументов.");
                System.out.println("Количество аргументов некорректно. Введено: " + arguments.length + ", необходимо: " + (Integer) commands.get(command)[1]);
                return false;
            }
        } else {
            return true;
        }
    }
}
