package gift.common.exception;

public class KakaoOAuthClientException extends RuntimeException {
    public KakaoOAuthClientException(int statusCode, String responseBody) {
      super("클라이언트 오류 (status=" + statusCode + "): " + responseBody);
    }
}
