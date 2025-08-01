package gift.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.oauth.dto.KakaoTextMessageTemplate;
import org.springframework.stereotype.Component;

@Component
public class KakaoMessageTemplateMaker {

    private final ObjectMapper objectMapper;

    public KakaoMessageTemplateMaker(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String makeMessageTemplate(String message) {
        KakaoTextMessageTemplate.Link link = new KakaoTextMessageTemplate.Link(
                "https://myapp.com/orders/123",
                "https://myapp.com/orders/123"
        );

        KakaoTextMessageTemplate template = new KakaoTextMessageTemplate(
                "text",
                message,
                link,
                "주문 확인"
        );

        try {
            return objectMapper.writeValueAsString(template);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("❗ 템플릿 생성에 실패했습니다", e);
        }
    }
}
