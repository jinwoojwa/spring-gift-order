package gift.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KakaoMessageTemplateMaker {

    private final ObjectMapper objectMapper;

    public KakaoMessageTemplateMaker(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String makeMessageTemplate(String message) {
        try {
            Map<String, Object> template = Map.of(
                    "object_type", "text",
                    "text", message,
                    "link", Map.of(
                            "web_url", "https://yourapp.com/orders/123", // 링크 추후 변경 예정
                            "mobile_web_url", "https://yourapp.com/orders/123" // 링크 추후 변경 예정
                    ),
                    "button_title", "주문 확인"
            );
            return objectMapper.writeValueAsString(template);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("❗ 템플릿 생성에 실패했습니다", e);
        }
    }
}
