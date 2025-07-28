package gift.auth.controller;

import gift.auth.service.KakaoAuthService;
import gift.member.dto.MemberResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("auth/kakao")
public class KakaoAuthController {

    private final KakaoAuthService kakaoAuthService;

    public KakaoAuthController(KakaoAuthService kakaoAuthService) {
        this.kakaoAuthService = kakaoAuthService;
    }

    @GetMapping("/login")
    public void redirectToKakaoAuth(HttpServletResponse response) throws IOException {
        response.sendRedirect(kakaoAuthService.getKakaoAuthorizeUrl());
    }

    @GetMapping("/callback")
    public ResponseEntity<MemberResponseDto> kakaoCallback(@RequestParam("code") String code) {
        MemberResponseDto memberDto = kakaoAuthService.loginWithKakao(code);
        return ResponseEntity.status(HttpStatus.OK).body(memberDto);
    }
}
