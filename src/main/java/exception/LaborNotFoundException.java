package main.java.exception;

public class LaborNotFoundException extends RuntimeException {
    public LaborNotFoundException(String message) {
        super(message);
    }
}
