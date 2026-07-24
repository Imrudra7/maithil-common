package in.maithilart.common.dto;

import java.time.Instant;
import java.util.UUID;

public class DeliveryRecord {

    private UUID deliveryId;
    private UUID eventId;
    private String status;
    private Integer attemptCount;
    private Integer maxRetry;
    private Instant nextRetryAt;
    private Instant createdAt;
    private String eventType;
    private String entityType;
    private String entityId;
    private String payload;
    private String metadata;

    
    // getters & setters
    public UUID getDeliveryId() { return deliveryId; }
    public void setDeliveryId(UUID deliveryId) { this.deliveryId = deliveryId; }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getAttemptCount() { return attemptCount; }
    public void setAttemptCount(Integer attemptCount) { this.attemptCount = attemptCount; }

    public Integer getMaxRetry() { return maxRetry; }
    public void setMaxRetry(Integer maxRetry) { this.maxRetry = maxRetry; }

    public Instant getNextRetryAt() { return nextRetryAt; }
    public void setNextRetryAt(Instant nextRetryAt) { this.nextRetryAt = nextRetryAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
    
    public String getMetadata() {return metadata;}
    public void setMetadata(String metadata) {this.metadata = metadata;}
	@Override
	public String toString() {
		return "DeliveryRecord [" + (deliveryId != null ? "deliveryId=" + deliveryId + ", " : "")
				+ (eventId != null ? "eventId=" + eventId + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (attemptCount != null ? "attemptCount=" + attemptCount + ", " : "")
				+ (maxRetry != null ? "maxRetry=" + maxRetry + ", " : "")
				+ (nextRetryAt != null ? "nextRetryAt=" + nextRetryAt + ", " : "")
				+ (createdAt != null ? "createdAt=" + createdAt + ", " : "")
				+ (eventType != null ? "eventType=" + eventType + ", " : "")
				+ (entityType != null ? "entityType=" + entityType + ", " : "")
				+ (entityId != null ? "entityId=" + entityId + ", " : "")
				+ (payload != null ? "payload=" + payload : "") + "]";
	}
    
    
}