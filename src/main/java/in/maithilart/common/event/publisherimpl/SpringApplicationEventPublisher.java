package in.maithilart.common.event.publisherimpl;

import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Component;

import in.maithilart.common.dto.MaithilEventMessage;
import in.maithilart.common.event.publisher.MaithilEventPublisher;


public class SpringApplicationEventPublisher implements MaithilEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public SpringApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publish(MaithilEventMessage eventMessage) {
		applicationEventPublisher.publishEvent(eventMessage);
	}
}