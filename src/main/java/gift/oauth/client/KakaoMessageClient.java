package gift.oauth.client;

import gift.oauth.config.KakaoOauthProperties;
import gift.oauth.util.KakaoMessageTemplateMaker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class KakaoMessageClient {

    private final RestClient kakaoRestClient;
    private final KakaoOauthProperties kakaoProps;
    private final KakaoMessageTemplateMaker templateMaker;

    public KakaoMessageClient(
            RestClient kakaoApiClient,
            KakaoOauthProperties kakaoProps,
            KakaoMessageTemplateMaker templateMaker
    ) {
        this.kakaoRestClient = kakaoApiClient;
        this.kakaoProps = kakaoProps;
        this.templateMaker = templateMaker;
    }

    public void sendOrderMessage(String accessToken, String message) {

        String templateJson = templateMaker.makeMessageTemplate(message);

        String encoded;
        try {
            encoded = "template_object=" + URLEncoder.encode(templateJson, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("❗ 메시지 인코딩 실패", e);
        }

        kakaoRestClient.post()
                .uri(kakaoProps.messageUrl())
                .header("Authorization", "Bearer " + accessToken)
                .body(encoded)
                .retrieve()
                .toBodilessEntity();
    }
}
