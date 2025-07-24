package gift.auth.dto;

public record KakaoTokenResponseDto(
        String token_type,
        String access_token,
        String refresh_token,
        Integer expires_in,
        Integer refresh_token_expires_in
) { }
