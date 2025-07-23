package gift.option.dto;

import jakarta.validation.constraints.*;

public record OptionRequestDto(
        @NotBlank(message = "옵션 이름은 비어 있을 수 없습니다.")
        @Size(max = 50, message = "옵션 이름은 최대 50자까지 입력할 수 있습니다.")
        @Pattern(
                regexp = "^[\\p{L}\\p{N} ()\\[\\]+\\-&/_]{1,50}$",
                message = "옵션 이름에는 (), [], +, -, &, /, _ 외의 특수 문자는 사용할 수 없습니다."
        )
        String name,

        @Min(value = 1, message = "옵션 수량은 최소 1개 이상이어야 합니다.")
        @Max(value = 100_000_000 - 1, message = "옵션 수량은 1억 미만이어야 합니다.")
        int quantity
) {
}
