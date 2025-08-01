package gift.oauth.config;

import gift.common.exception.KakaoOAuthClientException;
import gift.common.exception.KakaoOAuthServerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Configuration
public class KakaoRestClientConfig {

    private static final int TIMEOUT_MILLIS = 5000;

    @Bean
    public RestClient kakaoApiClient() {
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
        requestFactory.setReadTimeout(TIMEOUT_MILLIS);

        return RestClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .requestFactory(requestFactory)
                .defaultStatusHandler(
                        statusCode -> statusCode.is4xxClientError() || statusCode.is5xxServerError(),
                        (request, response) -> {
                            if (response.getStatusCode().is4xxClientError()) {
                                throw new KakaoOAuthClientException(response.getStatusCode().value(), response.getBody().toString());
                            }
                            if (response.getStatusCode().is5xxServerError()) {
                                throw new KakaoOAuthServerException(response.getStatusCode().value(), response.getBody().toString());
                            }
                            throw new RestClientException("Unexpected status: " + response.getStatusCode());
                        }
                )
                .build();
    }

    @Bean
    public RestClient kakaoAuthClient() {
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
        requestFactory.setReadTimeout(TIMEOUT_MILLIS);

        return RestClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .requestFactory(requestFactory)
                .defaultStatusHandler(
                        statusCode -> statusCode.is4xxClientError() || statusCode.is5xxServerError(),
                        (request, response) -> {
                            if (response.getStatusCode().is4xxClientError()) {
                                throw new KakaoOAuthClientException(response.getStatusCode().value(), response.getBody().toString());
                            }
                            if (response.getStatusCode().is5xxServerError()) {
                                throw new KakaoOAuthServerException(response.getStatusCode().value(), response.getBody().toString());
                            }
                            throw new RestClientException("Unexpected status: " + response.getStatusCode());
                        }
                )
                .build();
    }
}
