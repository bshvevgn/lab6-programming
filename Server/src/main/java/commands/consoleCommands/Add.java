package commands.consoleCommands;

import commands.Receiver;

import java.io.IOException;
import java.io.Serializable;

import static java.lang.Long.parseLong;

/**
 * This command adds new element into collection ArrayList<MusicBand>
 */

public class Add implements Command, Serializable {
    public String name = "add";
    public String getName(){
        return name;
    }

    public String required = "obj";
    public String getRequired(){
        return required;
    }

    public static String[] args = new String[0];
    public static String[] scrArgs = new String[6];
    public String[] getArgs() {
        return args;
    }

    public static String[] inputs = new String[6];
    public String[] getInputs() {
        return inputs;
    }

    public boolean complicated = true;

    public boolean isComplicated(){
        return complicated;
    }

    Receiver receiver;
    public Add (Receiver receiver){
        this.receiver = receiver;
    }

    public void execute(String[] arguments, String path, boolean isScript, Object object) throws IOException, ClassNotFoundException {
        receiver.addCommand(arguments, path, isScript, object);
    }

    @Override
    public String[] args() {
        return args;
    }

}
