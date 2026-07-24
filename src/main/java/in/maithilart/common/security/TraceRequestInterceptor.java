package in.maithilart.common.security;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import in.maithilart.common.constants.MaithilConstants;
import in.maithilart.common.context.provider.MicroserviceNameProvider;

@Component
public class TraceRequestInterceptor implements RequestInterceptor {
	private final MicroserviceNameProvider microserviceNameProvider;
	public TraceRequestInterceptor (MicroserviceNameProvider microserviceNameProvider) {
		this.microserviceNameProvider = microserviceNameProvider;
		
	}
 
    @Override
    public void apply(RequestTemplate template) {

        String traceId = MDC.get(MaithilConstants.MDC_KEY);

        if (traceId != null) {
            template.header(
                MaithilConstants.CORRELATION_ID_HEADER,
                traceId
            );
        }
        
        String service = microserviceNameProvider.getMicroservicename();

        if (service != null && !service.isBlank()) {
            template.header(
                MaithilConstants.CALLER_SERVICE_HEADER,
                service
            );
        }
        template.header(
        	    MaithilConstants.REQUEST_START_TIME,
        	    String.valueOf(System.currentTimeMillis())
        	);
    }
}