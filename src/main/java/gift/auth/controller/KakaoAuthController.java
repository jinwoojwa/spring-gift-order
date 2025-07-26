package gift.auth.controller;

import gift.auth.config.KakaoOauthProperties;
import gift.auth.dto.KakaoTokenResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@RestController
@RequestMapping("auth/kakao")
public class KakaoAuthController {

    private final RestClient restClient = RestClient.create();
    private final KakaoOauthProperties kakaoProps;

    public KakaoAuthController(KakaoOauthProperties kakaoProps) {
        this.kakaoProps = kakaoProps;
    }

    @GetMapping("/login")
    public void redirectToKakaoAuth(HttpServletResponse response) throws IOException {
        response.sendRedirect(kakaoProps.getAuthorizeUrl());
    }

    @GetMapping("/callback")
    public ResponseEntity<KakaoTokenResponseDto> kakaoCallback(@RequestParam("code") String code) {
        String body = kakaoProps.getTokenRequestBody(code);

        ResponseEntity<KakaoTokenResponseDto> response = restClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .toEntity(KakaoTokenResponseDto.class);

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }


}
