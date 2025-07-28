package gift.order.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrderRequestDto(
        @NotNull(message = "옵션 ID는 필수입니다.")
        Long optionId,

        @Min(value = 1, message = "옵션 수량은 최소 1개 이상이어야 합니다.")
        @Max(value = 100_000_000 - 1, message = "옵션 수량은 1억 미만이어야 합니다.")
        int quantity,

        @Size(max = 255, message = "메시지는 255자 이하여야 합니다.")
        String message
) { }
