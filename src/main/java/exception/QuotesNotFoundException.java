package main.java.exception;

public class QuotesNotFoundException extends RuntimeException {
    public QuotesNotFoundException(String message) {
        super(message);
    }
}
