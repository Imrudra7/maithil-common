package in.maithilart.common.event.poller;

import java.util.List;
import java.util.UUID;

import in.maithilart.common.dto.DeliveryRecord;
import in.maithilart.common.dto.NotificationDeliveryRecord;

public interface MaithilEventPoller {
    List<DeliveryRecord> pollPendingDeliveries(String subscriberName, int batchSize);
    
    void markSuccess(UUID deliveryId);

	void markFailed(DeliveryRecord record, String errorMessage, Exception e);

	List<NotificationDeliveryRecord> pollPendingNotificationDeliveries( int batchSize);

	void markNotificationDeliveryFailed(NotificationDeliveryRecord record, String errorMessage, Exception e);

	void markNotificationDeliverySuccess(UUID deliveryId, String providerMessageId);
}