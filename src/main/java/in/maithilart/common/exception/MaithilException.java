package in.maithilart.common.exception;

public class MaithilException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String errorCode;

    public MaithilException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public MaithilException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        cause.printStackTrace();
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
