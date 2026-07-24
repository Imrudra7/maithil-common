package in.maithilart.common.idempotency;

import java.util.UUID;

public class IdempotencyKey {

    private UUID userId;

    private UUID idempotencyKey;

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getIdempotencyKey() {
		return idempotencyKey;
	}

	public void setIdempotencyKey(UUID idempotencyKey) {
		this.idempotencyKey = idempotencyKey;
	}

}
