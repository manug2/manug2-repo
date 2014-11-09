package happy;

public class FaceNotMatchingException extends RuntimeException {
    public FaceNotMatchingException() {
    }

    public FaceNotMatchingException(String message) {
        super(message);
    }

    public FaceNotMatchingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FaceNotMatchingException(Throwable cause) {
        super(cause);
    }

    public FaceNotMatchingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
