package com.apress.my.retro.exception;

@SuppressWarnings(value = "unused")
// When searching for a UUID in the map of RetroBoards, if it doesn't exist, most likely
// it will throw a RuntimeException, in order to make this exception more specific and
// easier to debug we can define and throw a specific (custom) exception.
// So an instance of this exception (object) will be created and thrown when searching for a
// nonexistent uuid.
public class RetroBoardNotFoundException extends RuntimeException {
    public RetroBoardNotFoundException(){
        super("RetroBoard Not Found");
    }
    // replaced "{}" with "%s" as argument convention for String.format() requires
    public RetroBoardNotFoundException(String message) {
        super(String.format("RetroBoard Not Found: %s", message));
    }

    // replaced "{}" with "%s" as well
    public RetroBoardNotFoundException(String message, Throwable cause) {
        super(String.format("RetroBoard Not found: %s", message), cause);
    }
}
