package in.maithilart.common.dto;

import java.time.Instant;
import java.util.Map;

public class MaithilEventMessage {

	private String eventId;

	private String eventType;

	private String entityType;

	private String entityId;

	private Instant occurredAt;

	private String publisher;

	private Object payload;

	private Map<String, Object> metadata;

	private String status;

	private String correlationId;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Instant getOccurredAt() {
		return occurredAt;
	}

	public void setOccurredAt(Instant occurredAt) {
		this.occurredAt = occurredAt;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	public MaithilEventMessage(String eventId, String eventType, String entityType, String entityId, Instant occurredAt,
			String publisher, Object payload, Map<String, Object> metadata) {
		super();
		this.eventId = eventId;
		this.eventType = eventType;
		this.entityType = entityType;
		this.entityId = entityId;
		this.occurredAt = occurredAt;
		this.publisher = publisher;
		this.payload = payload;
		this.metadata = metadata;
	}

	public MaithilEventMessage() {
	}

	@Override
	public String toString() {
		return "MaithilEventMessage [" + (eventId != null ? "eventId=" + eventId + ", " : "")
				+ (eventType != null ? "eventType=" + eventType + ", " : "")
				+ (entityType != null ? "entityType=" + entityType + ", " : "")
				+ (entityId != null ? "entityId=" + entityId + ", " : "")
				+ (occurredAt != null ? "occurredAt=" + occurredAt + ", " : "")
				+ (publisher != null ? "publisher=" + publisher + ", " : "")
				+ (payload != null ? "payload=" + payload + ", " : "")
				+ (metadata != null ? "metadata=" + metadata + ", " : "") + (status != null ? "status=" + status : "")
				+ "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;

	}

	public String getCorrelationId() {
		return this.correlationId;
	}

}
