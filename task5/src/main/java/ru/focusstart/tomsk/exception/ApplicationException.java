package ru.focusstart.tomsk.exception;

abstract class ApplicationException extends RuntimeException {

    ApplicationException(String message) {
        super(message);
    }

    ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
