package in.maithilart.common.audit.store.impl;

import org.springframework.stereotype.Component;

import in.maithilart.common.audit.store.AuditStore;
import in.maithilart.common.dto.AuditRecord;

@Component
public class ConsoleAuditStore implements AuditStore {

	@Override
	public void save(AuditRecord auditRecord) {

		System.out.println("================AUDIT================");
		System.out.println(auditRecord);
		System.out.println("=====================================");
	}
}