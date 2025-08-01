package gift.oauth.dto;

public record KakaoLoginResponseDto(
        String jwtToken,
        String tokenType,
        String accessToken,
        String refreshToken,
        Integer expiresIn,
        Integer refreshTokenExpiresIn
) {
    public KakaoLoginResponseDto(String jwtToken, KakaoTokenResponseDto kakaoToken) {
        this(
                jwtToken,
                kakaoToken.tokenType(),
                kakaoToken.accessToken(),
                kakaoToken.refreshToken(),
                kakaoToken.expiresIn(),
                kakaoToken.refreshTokenExpiresIn()
        );
    }
}
