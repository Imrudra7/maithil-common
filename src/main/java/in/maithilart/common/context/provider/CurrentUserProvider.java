package in.maithilart.common.context.provider;

import in.maithilart.common.security.MaithilPrincipal;

public interface CurrentUserProvider {

	 MaithilPrincipal getCurrentUser();
}
