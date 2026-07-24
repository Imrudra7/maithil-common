package in.maithilart.common.event.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import in.maithilart.common.dto.NotificationDeliveryRecord;

public class NotificationDeliveryMapper implements RowMapper<NotificationDeliveryRecord> {

    @Override
    public NotificationDeliveryRecord mapRow(ResultSet rs, int rowNum) throws SQLException {

    	NotificationDeliveryRecord record = new NotificationDeliveryRecord();
        record.setDeliveryId(UUID.fromString(rs.getString("delivery_id")));
        record.setNotificationId(UUID.fromString(rs.getString("notification_id")));
        record.setStatus(rs.getString("status"));
        record.setAttemptCount(rs.getInt("attempt_count"));
        record.setMaxRetry(rs.getInt("max_retry"));

        Timestamp nextRetry = rs.getTimestamp("next_retry_at");
        record.setNextRetryAt(nextRetry != null ? nextRetry.toInstant() : null);
        
        Timestamp lastAttemptedAt = rs.getTimestamp("last_attempted_at");
        record.setLastAttemptedAt(lastAttemptedAt != null ? lastAttemptedAt.toInstant() : null);
        

        Timestamp processedAt = rs.getTimestamp("processed_at");
        record.setProcessedAt(processedAt != null ? processedAt.toInstant() : null);

        Timestamp createdAt = rs.getTimestamp("created_at");
        record.setCreatedAt(createdAt != null ? createdAt.toInstant() : null);
        
        record.setErrorMessage(rs.getString("error_message"));

        return record;
    }
}