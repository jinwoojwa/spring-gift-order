package gift.auth.dto;

public record KakaoTokenResponseDto(
        String tokenType,
        String accessToken,
        String refreshToken,
        Integer expiresId,
        Integer refreshTokenExpiresIn
) { }
