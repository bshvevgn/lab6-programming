package commands.consoleCommands;

import exceptions.InvalidArgsException;

import java.io.IOException;

/**
 * This interface is implemented by all commands.
 */

public interface Command {
    public void execute(String[] arguments, String path, boolean isScript, Object object) throws InvalidArgsException, IOException, ClassNotFoundException;

    String[] args();

    String name = null;

    public String required = null;

    boolean complicated = false;

    static boolean isCorrectArgs(String[] needArgs, String[] providedArgs) throws InvalidArgsException {
        if (needArgs.length != providedArgs.length) throw new InvalidArgsException("Некорректное количество аргументов команды.");
        return true;
    }

    boolean isComplicated();

    public String getName();

    public String[] getArgs();
    public String[] getInputs();

    public String getRequired();
}
