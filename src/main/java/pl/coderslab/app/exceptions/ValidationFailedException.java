package pl.coderslab.app.exceptions;

import java.util.function.Supplier;

public class ValidationFailedException extends Exception implements Supplier<ValidationFailedException> {
    public ValidationFailedException(String message) {
        super(message);
    }

    @Override
    public ValidationFailedException get() {
        return this;
    }
}
