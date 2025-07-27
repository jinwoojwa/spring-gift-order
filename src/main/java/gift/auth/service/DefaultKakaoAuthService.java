package gift.auth.service;

import gift.auth.client.KakaoOAuthClient;
import gift.auth.dto.KakaoTokenResponseDto;
import gift.auth.dto.KakaoUserInfoResponseDto;
import gift.member.dto.MemberResponseDto;
import gift.member.entity.Member;
import gift.member.entity.Role;
import gift.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultKakaoAuthService implements KakaoAuthService {

    private final MemberRepository memberRepository;
    private final KakaoOAuthClient kakaoOAuthClient;

    public DefaultKakaoAuthService(MemberRepository memberRepository,  KakaoOAuthClient kakaoOAuthClient) {
        this.memberRepository = memberRepository;
        this.kakaoOAuthClient = kakaoOAuthClient;
    }

    public String getKakaoAuthorizeUrl() {
        return kakaoOAuthClient.getAuthorizeUrl();
    }

    @Override
    @Transactional
    public MemberResponseDto loginWithKakao(String code) {

        KakaoTokenResponseDto tokenResponse = kakaoOAuthClient.requestToken(code);

        KakaoUserInfoResponseDto userInfo = kakaoOAuthClient.getUserInfo(tokenResponse.accessToken());

        Member member = memberRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> {
                    Member newMember = new Member(userInfo.getEmail(), "password111", Role.USER);
                    return memberRepository.save(newMember);
                });

        return new MemberResponseDto(member);
    }
}
