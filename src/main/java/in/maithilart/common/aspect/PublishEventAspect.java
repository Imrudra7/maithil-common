package in.maithilart.common.aspect;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;

import in.maithilart.common.annotation.PublishMaithilEvent;
import in.maithilart.common.constants.MaithilConstants;
import in.maithilart.common.context.provider.EventMetadataProvider;
import in.maithilart.common.context.provider.MetadataProvider;
import in.maithilart.common.context.provider.MicroserviceNameProvider;
import in.maithilart.common.dto.MaithilEventMessage;
import in.maithilart.common.dto.MaithilResponse;
import in.maithilart.common.event.publisher.MaithilEventPublisher;

@Aspect
@ConditionalOnProperty(name = "maithil.event.enabled", havingValue = "true")
public class PublishEventAspect {

	private final MaithilEventPublisher eventPublisher;
	private final MetadataProvider metadataProvider;
	private final EventMetadataProvider eventMetadataProvider;
	private final MicroserviceNameProvider microserviceNameProvider;

	public PublishEventAspect(MaithilEventPublisher eventPublisher, MetadataProvider metadataProvider,
			MicroserviceNameProvider microserviceNameProvider, EventMetadataProvider eventMetadataProvider) {
		this.eventPublisher = eventPublisher;
		this.metadataProvider = metadataProvider;
		this.eventMetadataProvider = eventMetadataProvider;
		this.microserviceNameProvider = microserviceNameProvider;
	}

	@Around("@annotation(publishMaithilEvent)")
	public Object publishEvent(ProceedingJoinPoint joinPoint, PublishMaithilEvent publishMaithilEvent)
			throws Throwable {
		try {
			Object result = joinPoint.proceed();

			Object payload = unwrapPayload(result);

			if (result instanceof ResponseEntity<?> responseEntity) {
				payload = responseEntity.getBody();
			}

			MaithilEventMessage eventMessage = new MaithilEventMessage();

			eventMessage.setEventId(UUID.randomUUID().toString());
			eventMessage.setEventType(publishMaithilEvent.eventType());
			eventMessage.setEntityType(publishMaithilEvent.entityType());
			eventMessage.setEntityId(extractEntityId(payload, publishMaithilEvent.entityIdField()));
			eventMessage.setOccurredAt(Instant.now());
			eventMessage.setPublisher(microserviceNameProvider.getMicroservicename());
			eventMessage.setPayload(payload);
			eventMessage.setMetadata(buildMetadata());
			eventMessage.setStatus(MaithilConstants.PENDING_DISPATCH);
			eventMessage.setCorrelationId(extractEntityId(payload, publishMaithilEvent.correlationIdField()));
			eventPublisher.publish(eventMessage);

			return result;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			eventMetadataProvider.clear();
		}
	}

	private String extractEntityId(Object payload, String fieldName) {
		Field field = ReflectionUtils.findField(payload.getClass(), fieldName);

		if (field == null) {
			return null;
		}

		ReflectionUtils.makeAccessible(field);

		Object value = ReflectionUtils.getField(field, payload);

		return value != null ? value.toString() : null;
	}

	private Object unwrapPayload(Object result) {

		if (result instanceof ResponseEntity<?> response) {
			return unwrapPayload(response.getBody());
		}

		if (result instanceof MaithilResponse<?> maithil) {
			return maithil.getData();
		}

		return result;
	}

	private Map<String, Object> buildMetadata() {

		Map<String, Object> metadata = new HashMap<>();

		Map<String, Object> commonMetadata = metadataProvider.getMetadata();

		if (commonMetadata != null) {
			metadata.putAll(commonMetadata);
		}

		Map<String, Object> eventMetadata = eventMetadataProvider.getEventMetadata();

		if (eventMetadata != null) {
			metadata.putAll(eventMetadata);
		}

		return metadata;
	}
}