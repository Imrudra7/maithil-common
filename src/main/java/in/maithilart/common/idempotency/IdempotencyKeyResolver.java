package in.maithilart.common.idempotency;

import java.util.UUID;

import org.springframework.stereotype.Component;

import in.maithilart.common.constants.MaithilConstants;
import in.maithilart.common.context.provider.CurrentUserProvider;
import in.maithilart.common.context.provider.RequestContextProvider;
import in.maithilart.common.exception.MaithilException;
import in.maithilart.common.security.MaithilPrincipal;

@Component
public class IdempotencyKeyResolver {

	private final RequestContextProvider request;
	private final CurrentUserProvider currentUserProvider;

	public IdempotencyKeyResolver(RequestContextProvider request, CurrentUserProvider currentUserProvider) {
		this.request = request;
		this.currentUserProvider = currentUserProvider;
	}

	public IdempotencyKey resolve() {

		String header = request.getHeader("Idempotency-Key");

		if (header == null || header.isBlank()) {
			throw new MaithilException(MaithilConstants.INVALID_REQUEST, "Idempotency-Key header is required");
		}

		UUID idempotencyKey;

		try {

			idempotencyKey = UUID.fromString(header);

		} catch (IllegalArgumentException ex) {

			throw new MaithilException(MaithilConstants.INVALID_REQUEST, "Invalid Idempotency-Key");
		}

		UUID userId;

		try {
			MaithilPrincipal principal = currentUserProvider.getCurrentUser();
			userId = UUID.fromString(principal.getUserId());

		} catch (Exception ex) {

			throw new MaithilException(MaithilConstants.UNAUTHORIZED, "Invalid authenticated user");
		}

		IdempotencyKey key = new IdempotencyKey();
		key.setUserId(userId);
		key.setIdempotencyKey(idempotencyKey);

		return key;
	}
}