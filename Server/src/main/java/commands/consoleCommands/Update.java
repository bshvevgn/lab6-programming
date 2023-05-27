package commands.consoleCommands;

import commands.Receiver;

import java.io.IOException;
import java.io.Serializable;

import static java.lang.Long.parseLong;

/**
 * This command updates element of collection ArrayList<MusicBand> which ID equals argument.
 */

public class Update implements Command, Serializable {
    public String name = "update_id";
    public String getName(){
        return name;
    }

    public String required = "obj";
    public String getRequired(){
        return required;
    }

    public final static String[] args = {"id элемента"};
    public static String[] inputs = new String[6];
    public String[] getInputs() {
        return inputs;
    }
    public String[] getArgs() {
        return args;
    }

    public boolean complicated = true;

    public boolean isComplicated(){
        return complicated;
    }

    Receiver receiver;
    public Update(Receiver receiver){
        this.receiver = receiver;
    }

    public void execute(String[] arguments, String path, boolean isScript, Object object) throws IOException, ClassNotFoundException {
        receiver.updateByIdCommand(arguments, path, isScript, object);
    }

    @Override
    public String[] args() {
        return args;
    }

}
