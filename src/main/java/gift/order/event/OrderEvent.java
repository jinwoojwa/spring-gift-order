package gift.order.event;

import gift.order.dto.OrderInfoDto;

public record OrderEvent(OrderInfoDto orderInfoDto) {
}
