package in.maithilart.common.event.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import in.maithilart.common.dto.DeliveryRecord;

public class DeliveryRecordRowMapper implements RowMapper<DeliveryRecord> {

    @Override
    public DeliveryRecord mapRow(ResultSet rs, int rowNum) throws SQLException {

        DeliveryRecord record = new DeliveryRecord();
        record.setDeliveryId(UUID.fromString(rs.getString("delivery_id")));
        record.setEventId(UUID.fromString(rs.getString("event_id")));
        record.setStatus(rs.getString("status"));
        record.setAttemptCount(rs.getInt("attempt_count"));
        record.setMaxRetry(rs.getInt("max_retry"));

        Timestamp nextRetry = rs.getTimestamp("next_retry_at");
        record.setNextRetryAt(nextRetry != null ? nextRetry.toInstant() : null);

        Timestamp createdAt = rs.getTimestamp("created_at");
        record.setCreatedAt(createdAt != null ? createdAt.toInstant() : null);

        record.setEventType(rs.getString("event_type"));
        record.setEntityType(rs.getString("entity_type"));
        record.setEntityId(rs.getString("entity_id"));
        record.setPayload(rs.getString("payload"));
        record.setMetadata(rs.getString("metadata"));
        
        return record;
    }
}