package gift.auth.controller;

import gift.auth.config.KakaoOauthProperties;
import gift.auth.dto.KakaoTokenResponseDto;
import gift.common.exception.KakaoOAuthClientException;
import gift.common.exception.KakaoOAuthServerException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
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

        ResponseEntity<KakaoTokenResponseDto> res = restClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new KakaoOAuthClientException(response.getStatusCode().value(), response.getBody().toString());
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new KakaoOAuthServerException(response.getStatusCode().value(), response.getBody().toString());
                }))
                .toEntity(KakaoTokenResponseDto.class);

        return ResponseEntity
                .status(res.getStatusCode())
                .body(res.getBody());
    }


}
