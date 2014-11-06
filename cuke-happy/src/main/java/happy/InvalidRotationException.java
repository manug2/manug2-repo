package happy;

public class InvalidRotationException extends RuntimeException {
    public InvalidRotationException() {
    }

    public InvalidRotationException(String message) {
        super(message);
    }

    public InvalidRotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRotationException(Throwable cause) {
        super(cause);
    }

    public InvalidRotationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
