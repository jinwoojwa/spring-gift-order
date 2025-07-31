package gift.order.event;

import gift.oauth.client.KakaoMessageClient;
import gift.order.dto.OrderInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventListener {

    @Value("${order.message-template}")
    private String messageTemplate;

    private final KakaoMessageClient kakaoMessageClient;

    public OrderEventListener(KakaoMessageClient kakaoMessageClient) {
        this.kakaoMessageClient = kakaoMessageClient;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderEvent(OrderEvent orderEvent) {
        OrderInfoDto order = orderEvent.orderInfoDto();
        kakaoMessageClient.sendOrderMessage(order.kakaoAccessToken(), buildMessage(order));
    }

    private String buildMessage(OrderInfoDto order) {
        return messageTemplate.formatted(
                order.productName(),
                order.optionName(),
                order.quantity(),
                order.message(),
                order.orderDateTime().toString()
        );
    }
}
