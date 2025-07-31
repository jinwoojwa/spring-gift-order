package gift.order.dto;

import java.time.LocalDateTime;

public record OrderInfoDto(
        Long orderId,
        String productName,
        String optionName,
        int quantity,
        String message,
        LocalDateTime orderDateTime,
        String kakaoAccessToken
) { }
