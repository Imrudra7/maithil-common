package in.maithilart.common.dto;

import java.time.Instant;
import java.util.Map;

public class AuditRecord {

    private String auditId;

    private String action;

    private String entityType;

    private String entityId;

    private String performedBy;

    private String status;

    private Instant occurredAt;

    private String sourceService;

    private Map<String, Object> metadata;

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getSourceService() {
        return sourceService;
    }

    public void setSourceService(String sourceService) {
        this.sourceService = sourceService;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "AuditRecord [auditId=" + auditId +
                ", action=" + action +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", performedBy=" + performedBy +
                ", status=" + status +
                ", occurredAt=" + occurredAt +
                ", sourceService=" + sourceService +
                ", metadata=" + metadata +
                "]";
    }
}