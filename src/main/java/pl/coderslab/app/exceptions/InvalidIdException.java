package pl.coderslab.app.exceptions;


import java.util.function.Supplier;

public class InvalidIdException extends Exception implements Supplier<InvalidIdException> {
    public InvalidIdException(String message) {
        super(message);
    }

    @Override
    public InvalidIdException get() {
        return this;
    }
}
