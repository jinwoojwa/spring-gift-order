package gift.oauth.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class KakaoRestClientConfig {

    private static final int TIMEOUT_MILLIS = 5000;

    @Bean
    @Qualifier("kakaoApiClient") // https://kapi.kakao.com
    public RestClient kakaoApiClient() {
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
        requestFactory.setReadTimeout(TIMEOUT_MILLIS);

        return RestClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .requestFactory(requestFactory)
                .build();
    }

    @Bean
    @Qualifier("kakaoAuthClient") // https://kauth.kakao.com
    public RestClient kakaoAuthClient() {
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
        requestFactory.setReadTimeout(TIMEOUT_MILLIS);

        return RestClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .requestFactory(requestFactory)
                .build();
    }
}
