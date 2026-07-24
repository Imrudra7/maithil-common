package in.maithilart.common.event.publisher;

import in.maithilart.common.dto.MaithilEventMessage;

public interface MaithilEventPublisher {

    void publish(MaithilEventMessage eventMessage) throws Exception;

}