package gift.oauth.client;

import gift.oauth.config.KakaoOauthProperties;
import gift.oauth.dto.KakaoTokenResponseDto;
import gift.oauth.dto.KakaoUserInfoResponseDto;
import gift.common.exception.KakaoOAuthClientException;
import gift.common.exception.KakaoOAuthServerException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class KakaoOAuthClient {

    private final RestClient kakaoAuthClient;
    private final RestClient kakaoApiClient;
    private final KakaoOauthProperties kakaoProps;

    public KakaoOAuthClient(
            RestClient kakaoAuthClient,
            RestClient kakaoApiClient,
            KakaoOauthProperties kakaoProps
    ) {
        this.kakaoAuthClient = kakaoAuthClient;
        this.kakaoApiClient = kakaoApiClient;
        this.kakaoProps = kakaoProps;
    }

    public String getAuthorizeUrl() {
        return kakaoProps.getAuthorizeUrl();
    }

    public KakaoTokenResponseDto requestToken(String code) {
        String body = kakaoProps.getTokenRequestBody(code);

        return kakaoAuthClient.post()
                .uri(kakaoProps.tokenUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new KakaoOAuthClientException(response.getStatusCode().value(), response.getBody().toString());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new KakaoOAuthServerException(response.getStatusCode().value(), response.getBody().toString());
                })
                .body(KakaoTokenResponseDto.class);
    }

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
        return kakaoApiClient.get()
                .uri(kakaoProps.userInfoUrl())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new KakaoOAuthClientException(res.getStatusCode().value(), res.getBody().toString());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new KakaoOAuthServerException(res.getStatusCode().value(), res.getBody().toString());
                })
                .body(KakaoUserInfoResponseDto.class);
    }
}
