package in.maithilart.common.dto;

import java.time.Instant;
import java.util.UUID;

public class NotificationDeliveryRecord {

	private UUID deliveryId;

	private UUID notificationId;

	private String status;

	private Integer attemptCount;

	private Integer maxRetry;

	private Instant nextRetryAt;

	private Instant lastAttemptedAt;

	private Instant processedAt;

	private String errorMessage;

	private Instant createdAt;

	public UUID getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(UUID deliveryId) {
		this.deliveryId = deliveryId;
	}

	public UUID getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(UUID notificationId) {
		this.notificationId = notificationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAttemptCount() {
		return attemptCount;
	}

	public void setAttemptCount(Integer attemptCount) {
		this.attemptCount = attemptCount;
	}

	public Integer getMaxRetry() {
		return maxRetry;
	}

	public void setMaxRetry(Integer maxRetry) {
		this.maxRetry = maxRetry;
	}

	public Instant getNextRetryAt() {
		return nextRetryAt;
	}

	public void setNextRetryAt(Instant nextRetryAt) {
		this.nextRetryAt = nextRetryAt;
	}

	public Instant getLastAttemptedAt() {
		return lastAttemptedAt;
	}

	public void setLastAttemptedAt(Instant lastAttemptedAt) {
		this.lastAttemptedAt = lastAttemptedAt;
	}

	public Instant getProcessedAt() {
		return processedAt;
	}

	public void setProcessedAt(Instant processedAt) {
		this.processedAt = processedAt;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "NotificationDeliveryRecord [" + (deliveryId != null ? "deliveryId=" + deliveryId + ", " : "")
				+ (notificationId != null ? "notificationId=" + notificationId + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (attemptCount != null ? "attemptCount=" + attemptCount + ", " : "")
				+ (maxRetry != null ? "maxRetry=" + maxRetry + ", " : "")
				+ (nextRetryAt != null ? "nextRetryAt=" + nextRetryAt + ", " : "")
				+ (lastAttemptedAt != null ? "lastAttemptedAt=" + lastAttemptedAt + ", " : "")
				+ (processedAt != null ? "processedAt=" + processedAt + ", " : "")
				+ (errorMessage != null ? "errorMessage=" + errorMessage + ", " : "")
				+ (createdAt != null ? "createdAt=" + createdAt : "") + "]";
	}
}
