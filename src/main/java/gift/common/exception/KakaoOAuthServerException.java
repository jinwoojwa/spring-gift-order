package gift.common.exception;

public class KakaoOAuthServerException extends RuntimeException {
    public KakaoOAuthServerException(int statusCode, String responseBody) {
        super(String.format("서버 오류 발생 (status=%d): %s", statusCode, responseBody));
    }
}
