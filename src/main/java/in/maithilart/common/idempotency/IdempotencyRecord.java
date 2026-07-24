package in.maithilart.common.idempotency;

import java.time.Instant;

public class IdempotencyRecord {

	private Enum status;

	private Object response;

	private int statusCode;

	private String requestHash;

	private Instant createdAt;

	public Enum getStatus() {
		return status;
	}

	public void setStatus(Enum status) {
		this.status = status;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public String getRequestHash() {
		return requestHash;
	}

	public void setRequestHash(String requestHash) {
		this.requestHash = requestHash;
	}

}
