package gift.oauth.service;

import gift.oauth.dto.KakaoLoginResponseDto;

public interface KakaoAuthService {
    KakaoLoginResponseDto loginWithKakao(String code);

    String getKakaoAuthorizeUrl();
}
