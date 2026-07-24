package in.maithilart.common.dto;

import java.time.Instant;

public class MaithilErrorResponse {

    private String errorCode;
    private String message;
    private Instant timestamp;

    public MaithilErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
