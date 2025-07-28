package gift.common.exception;

public class OptionNotFoundException extends RuntimeException {
    public OptionNotFoundException(Long optionId) { super("❗ 상품 옵션을 찾을 수 없습니다. ID: " + optionId + " ❗"); }
}
