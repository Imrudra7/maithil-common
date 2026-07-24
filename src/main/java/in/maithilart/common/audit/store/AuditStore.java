package in.maithilart.common.audit.store;

import in.maithilart.common.dto.AuditRecord;

public interface AuditStore {

    void save(AuditRecord auditRecord);

}