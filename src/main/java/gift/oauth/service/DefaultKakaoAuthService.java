package gift.oauth.service;

import gift.oauth.client.KakaoOAuthClient;
import gift.oauth.dto.KakaoLoginResponseDto;
import gift.oauth.dto.KakaoTokenResponseDto;
import gift.oauth.dto.KakaoUserInfoResponseDto;
import gift.common.security.JwtUtil;
import gift.member.entity.Member;
import gift.member.entity.Role;
import gift.member.repository.MemberRepository;
import gift.oauth.entity.UserKakaoToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class DefaultKakaoAuthService implements KakaoAuthService {

    private final MemberRepository memberRepository;
    private final KakaoOAuthClient kakaoOAuthClient;
    private final JwtUtil jwtUtil;

    public DefaultKakaoAuthService(MemberRepository memberRepository,  KakaoOAuthClient kakaoOAuthClient,  JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.kakaoOAuthClient = kakaoOAuthClient;
        this.jwtUtil = jwtUtil;
    }

    public String getKakaoAuthorizeUrl() {
        return kakaoOAuthClient.getAuthorizeUrl();
    }

    @Override
    @Transactional
    public KakaoLoginResponseDto loginWithKakao(String code) {

        KakaoTokenResponseDto tokenResponse = kakaoOAuthClient.requestToken(code);

        KakaoUserInfoResponseDto userInfo = kakaoOAuthClient.getUserInfo(tokenResponse.accessToken());

        Member member = memberRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> {
                    Member newMember = new Member(userInfo.getEmail(), "password111", Role.USER);
                    return memberRepository.save(newMember);
                });

        Instant now = Instant.now();
        Instant accessTokenExpiresAt = now.plusSeconds(tokenResponse.expiresIn());
        Instant refreshTokenExpiresAt = now.plusSeconds(tokenResponse.refreshTokenExpiresIn());

        UserKakaoToken kakaoToken = member.getKakaoToken();
        if (kakaoToken == null) {
            kakaoToken = new UserKakaoToken(member, tokenResponse.accessToken(), tokenResponse.refreshToken(),
                    accessTokenExpiresAt, refreshTokenExpiresAt);
            member.setKakaoToken(kakaoToken);
        } else {
            kakaoToken.update(tokenResponse.accessToken(), accessTokenExpiresAt);
        }

        // JWT 발급
        String jwt = jwtUtil.generateToken(member.getEmail(), member.getId(), member.getRole().name());

        return new KakaoLoginResponseDto(jwt, tokenResponse);
    }
}
