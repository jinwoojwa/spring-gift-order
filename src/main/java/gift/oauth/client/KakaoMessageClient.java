package gift.oauth.client;

import gift.common.exception.KakaoOAuthClientException;
import gift.common.exception.KakaoOAuthServerException;
import gift.oauth.config.KakaoOauthProperties;
import gift.oauth.util.KakaoMessageTemplateMaker;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class KakaoMessageClient {

    private final RestClient kakaoApiClient;
    private final KakaoOauthProperties kakaoProps;
    private final KakaoMessageTemplateMaker templateMaker;

    public KakaoMessageClient(
            RestClient kakaoApiClient,
            KakaoOauthProperties kakaoProps,
            KakaoMessageTemplateMaker templateMaker
    ) {
        this.kakaoApiClient = kakaoApiClient;
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

        try {
            kakaoApiClient.post()
                    .uri(kakaoProps.messageUrl())
                    .header("Authorization", "Bearer " + accessToken)
                    .body(encoded)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new KakaoOAuthClientException(response.getStatusCode().value(), response.getBody().toString());
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        throw new KakaoOAuthServerException(response.getStatusCode().value(), response.getBody().toString());
                    })
                    .toBodilessEntity();
        } catch (RestClientException e) {
            throw new RuntimeException("카카오 API 요청 중 네트워크 오류가 발생했습니다.", e);
        }
    }
}
