package requests;

public class RequestHandler {
    public String getText(Request request){
        return request.getText();
    }
    public String[] getArgs(Request request){
        return request.getArgs();
    }
    public Object getObject(Request request){
        return request.getObject();
    }
}
