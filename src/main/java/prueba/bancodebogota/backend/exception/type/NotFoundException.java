package prueba.bancodebogota.backend.exception.type;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable error) {
        super(message, error);
    }
}
