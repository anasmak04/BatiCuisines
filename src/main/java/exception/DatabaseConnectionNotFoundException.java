package main.java.exception;

public class DatabaseConnectionNotFoundException extends RuntimeException {
  public DatabaseConnectionNotFoundException(String message) {
    super(message);
  }
}
