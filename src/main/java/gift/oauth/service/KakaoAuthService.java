package gift.auth.service;

import gift.member.dto.MemberResponseDto;

public interface KakaoAuthService {
    MemberResponseDto loginWithKakao(String code);

    String getKakaoAuthorizeUrl();
}
