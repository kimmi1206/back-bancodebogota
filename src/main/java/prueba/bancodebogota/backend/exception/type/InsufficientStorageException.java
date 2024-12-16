package prueba.bancodebogota.backend.exception.type;

public class InsufficientStorageException extends RuntimeException {

    public InsufficientStorageException() {
        super();
    }

    public InsufficientStorageException(String message) {
        super(message);
    }

    public InsufficientStorageException(String message, Throwable error) {
        super(message, error);
    }
}
