package responces;

import java.io.Serializable;

public enum CommandType implements Serializable {
    OBJECT,
    POST_OBJECT,
    NON_ARGUMENT,
    ARGUMENT,
    EXIT,
    EXECUTE
}
