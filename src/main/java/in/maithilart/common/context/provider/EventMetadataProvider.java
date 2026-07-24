package in.maithilart.common.context.provider;

import java.util.Map;

public interface EventMetadataProvider {

	Map<String, Object> getEventMetadata();
	
	default void clear() {
		getEventMetadata().clear();
    }
}