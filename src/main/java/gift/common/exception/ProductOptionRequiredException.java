package gift.common.exception;

public class ProductOptionRequiredException extends RuntimeException {
  public ProductOptionRequiredException() {
    super("상품은 하나 이상의 옵션을 가져야 합니다.");
  }
}
