package in.maithilart.common.aspect;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;     

import in.maithilart.common.annotation.Audit;
import in.maithilart.common.audit.store.AuditStore;
import in.maithilart.common.context.provider.CurrentUserProvider;
import in.maithilart.common.context.provider.MetadataProvider;
import in.maithilart.common.dto.AuditRecord;
import in.maithilart.common.security.MaithilPrincipal;

@Component
@Aspect
public class AuditAspect {

	@Value("${spring.application.name}")
	private String applicationName;
	


	private final AuditStore auditStore;
	private final CurrentUserProvider currentUserProvider;
	private final MetadataProvider metadataProvider;

	public AuditAspect(AuditStore auditStore, CurrentUserProvider currentUserProvider, MetadataProvider metadataProvider) {
		this.auditStore = auditStore;
		this.currentUserProvider = currentUserProvider;
		this.metadataProvider = metadataProvider;
	}

	@Around("@annotation(audit)")
	public Object audit(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {

		AuditRecord auditRecord = new AuditRecord();
		
		MaithilPrincipal principal = currentUserProvider.getCurrentUser();

		auditRecord.setAuditId(UUID.randomUUID().toString());

		auditRecord.setAction(audit.action());

		auditRecord.setEntityType(audit.entityType());

		auditRecord.setOccurredAt(Instant.now());

		auditRecord.setSourceService(applicationName);
		
		auditRecord.setPerformedBy(principal.getEmail());
		
		auditRecord.setMetadata(metadataProvider.getMetadata());

		try {

			Object result = joinPoint.proceed();

			Object payload = result;

			if (result instanceof ResponseEntity<?> responseEntity) {
				payload = responseEntity.getBody();
			}

			auditRecord.setEntityId(extractEntityId(payload, audit.entityIdField()));

			auditRecord.setStatus("SUCCESS");

			auditStore.save(auditRecord);

			return result;

		} catch (Exception ex) {

			auditRecord.setStatus("FAILED");

			auditStore.save(auditRecord);

			throw ex;
		}
	}

	private String extractEntityId(Object payload, String fieldName) {

		if (payload == null || fieldName == null || fieldName.isBlank()) {
			return null;
		}

		try {

			if (payload instanceof Map<?, ?> map) {

				Object value = map.get(fieldName);

				return value != null ? value.toString() : null;
			}

			Field field = payload.getClass().getDeclaredField(fieldName);

			field.setAccessible(true);

			Object value = field.get(payload);

			return value != null ? value.toString() : null;

		} catch (Exception ex) {
			return null;
		}
	}
}