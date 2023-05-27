package requests;

import java.io.Serializable;

public class Request implements Serializable {
    String text;
    String[] args;
    Object object;

    private static final long serialVersionUID = 8842532001314005038L;

    public Request(String text, String[] args, Object object){
        this.text = text;
        this.args = args;
        this.object = object;
    }

    public String getText() {
        return text;
    }
    public String[] getArgs(){
        return args;
    }
    public Object getObject() {
        return object;
    }
}
