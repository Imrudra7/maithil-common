package in.maithilart.common.dto;

import java.time.Instant;
import java.util.Map;

public class MaithilResponse<T> {

	private boolean success;

	private String code;

	private String message;

	private T data;

	private Map<String, String> notification;

	private Instant timestamp;

	// ==========================
	// Helper Methods
	// ==========================

	public static <T> MaithilResponse<T> success(String code, String message, T data) {

		MaithilResponse<T> response = new MaithilResponse<>();

		response.setSuccess(true);
		response.setCode(code);
		response.setMessage(message);
		response.setData(data);

		return response;
	}

	public static <T> MaithilResponse<T> success(String code, String message, T data,
			Map<String, String> notification) {

		MaithilResponse<T> response = success(code, message, data);

		response.setNotification(notification);

		return response;
	}

	public static <T> MaithilResponse<T> failure(String code, String message) {

		MaithilResponse<T> response = new MaithilResponse<>();

		response.setSuccess(false);
		response.setCode(code);
		response.setMessage(message);

		return response;
	}

	public static <T> MaithilResponse<T> failure(String code, String message, Map<String, String> notification) {

		MaithilResponse<T> response = failure(code, message);

		response.setNotification(notification);

		return response;
	}

	// Constructor
	public MaithilResponse() {
		this.timestamp = Instant.now();
	}

	// Getter - Setters
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Map<String, String> getNotification() {
		return notification;
	}

	public void setNotification(Map<String, String> notification) {
		this.notification = notification;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

}
