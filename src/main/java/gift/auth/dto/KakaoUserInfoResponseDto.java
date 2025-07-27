package gift.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserInfoResponseDto(
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {
    public String getEmail() {
        return kakaoAccount != null ? kakaoAccount.email() : null;
    }

    public record KakaoAccount(@JsonProperty("email") String email) {}
}
