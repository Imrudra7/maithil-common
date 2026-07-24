package in.maithilart.common.event.publisherimpl;

import java.sql.Timestamp;
import java.util.UUID;

import in.maithilart.common.constants.MaithilConstants;
import in.maithilart.common.db.DatabaseExecutor;
import in.maithilart.common.dto.MaithilEventMessage;
import in.maithilart.common.event.publisher.MaithilEventPublisher;
import in.maithilart.common.event.util.Messenger;

public class DatabaseCommunicatorPublisher implements MaithilEventPublisher {

	private final DatabaseExecutor databaseExecutor;
	private final Messenger messenger;

	public DatabaseCommunicatorPublisher(DatabaseExecutor databaseExecutor, Messenger messenger) {

		this.databaseExecutor = databaseExecutor;
		this.messenger = messenger;
	}

	@Override
	public void publish(MaithilEventMessage event) throws Exception {

		String idempotencyKey = event.getEventType() + "-" + event.getEntityId();
		Object correlationIdRaw = event.getMetadata().get("correlationId");
		UUID correlationId = correlationIdRaw != null ? UUID.fromString(correlationIdRaw.toString()) : null;
		
		System.out.println("EVENT PAYLOAD  : " + event.getPayload());
		System.out.println("EVENT METADATA : " + event.getMetadata());
		
		databaseExecutor.update(MaithilConstants.EVENT_PUBLISH_QUERY, UUID.fromString(event.getEventId()), // event_id
				event.getEventType(), // event_type
				event.getEntityType(), // entity_type
				event.getEntityId(), // entity_id
				messenger.pack(event.getPayload()), // payload
				messenger.pack(event.getMetadata()),
				MaithilConstants.PAYLOAD_VERSION, // payload_version
				idempotencyKey, // idempotency_key
				event.getPublisher(), // publisher
				correlationId, // correlation_id
				event.getStatus(), // status
				Timestamp.from(event.getOccurredAt())); // created_at
	}

}
