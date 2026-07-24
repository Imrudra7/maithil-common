package in.maithilart.common.event.pollerimpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import in.maithilart.common.constants.MaithilConstants;
import in.maithilart.common.db.DatabaseExecutor;
import in.maithilart.common.dto.DeliveryRecord;
import in.maithilart.common.dto.NotificationDeliveryRecord;
import in.maithilart.common.event.mapper.DeliveryRecordRowMapper;
import in.maithilart.common.event.mapper.NotificationDeliveryMapper;
import in.maithilart.common.event.poller.MaithilEventPoller;

public class DatabaseEventPoller implements MaithilEventPoller {

	private final DatabaseExecutor databaseExecutor;
	private final DeliveryRecordRowMapper rowMapper = new DeliveryRecordRowMapper();
	private final NotificationDeliveryMapper notifyMapper = new NotificationDeliveryMapper();

	public DatabaseEventPoller(DatabaseExecutor databaseExecutor) {
		this.databaseExecutor = databaseExecutor;
	}

	@Override
	@Transactional
	public List<DeliveryRecord> pollPendingDeliveries(String subscriberName, int batchSize) {

		List<DeliveryRecord> records = databaseExecutor.query(MaithilConstants.POLL_PENDING_DELIVERY_QUERY, rowMapper,
				subscriberName, batchSize);

		for (DeliveryRecord record : records) {
			databaseExecutor.update(MaithilConstants.MARK_DELIVERY_IN_PROGRESS_QUERY,
					java.sql.Timestamp.from(Instant.now()), record.getDeliveryId());
		}
		return records;
	}

	@Override
	@Transactional
	public List<NotificationDeliveryRecord> pollPendingNotificationDeliveries(int batchSize) {

		List<NotificationDeliveryRecord> records = databaseExecutor.query(
				MaithilConstants.POLL_PENDING_NOTIFICATION_DELIVERY_QUERY, notifyMapper,
				java.sql.Timestamp.from(Instant.now()), batchSize);

		for (NotificationDeliveryRecord record : records) {

			databaseExecutor.update(MaithilConstants.MARK_NOTIFICATION_DELIVERY_IN_PROGRESS_QUERY,
					java.sql.Timestamp.from(Instant.now()), record.getDeliveryId());
		}

		return records;
	}

	@Override
	public void markSuccess(UUID deliveryId) {
		databaseExecutor.update(MaithilConstants.MARK_DELIVERY_SUCCESS_QUERY, Timestamp.from(Instant.now()),
				deliveryId);
	}
	
	@Override
	public void markNotificationDeliverySuccess(UUID deliveryId, String providerMesageId) {
		databaseExecutor.update(MaithilConstants.MARK_NOTIFICATION_DELIVERY_SUCCESS_QUERY, Timestamp.from(Instant.now()),
				providerMesageId,deliveryId);
	}

	@Override
	public void markFailed(DeliveryRecord record, String errorMessage, Exception e) {

		long backoffSeconds = 30L * (long) Math.pow(2, record.getAttemptCount());
		Instant nextRetry = Instant.now().plusSeconds(backoffSeconds);

		databaseExecutor.update(MaithilConstants.MARK_DELIVERY_FAILED_QUERY, errorMessage + "::" + e.getCause(),
				Timestamp.from(nextRetry), record.getDeliveryId());
	}
	@Override
	public void markNotificationDeliveryFailed(NotificationDeliveryRecord record, String errorMessage, Exception e) {

		long backoffSeconds = 30L * (long) Math.pow(2, record.getAttemptCount());
		Instant nextRetry = Instant.now().plusSeconds(backoffSeconds);

		databaseExecutor.update(MaithilConstants.MARK_NOTIFICATION_DELIVERY_FAILED_QUERY, errorMessage + "::" + e.getCause(),
				Timestamp.from(nextRetry), record.getDeliveryId());
	}
}