package requests;

public class RequestBuilder {
    public Request buildRequest(String text, String[] args, Object object){
        return new Request(text, args, object);
    }
}
