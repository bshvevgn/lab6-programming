package requests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestBuilder {
    private static final Logger logger = LogManager.getLogger();
    public Request buildRequest(String text, String[] args, Object object){
        logger.info("Создан запрос: " + text + ".");
        return new Request(text, args, object);
    }
}
