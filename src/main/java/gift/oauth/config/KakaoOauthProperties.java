package gift.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao")
public record KakaoOauthProperties(
        String clientId,
        String redirectUri,
        String authUrl,
        String tokenUrl,
        String userInfoUrl
) {
    public String getAuthorizeUrl() {
        return authUrl +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri;
    }

    public String getTokenRequestBody(String code) {
        return "grant_type=authorization_code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&code=" + code;
    }
}
