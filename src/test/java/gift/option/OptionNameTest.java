package gift.option;

import gift.option.entity.OptionName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OptionNameTest {

    @Test
    void 정상_옵션_이름_생성() {
        assertDoesNotThrow(() -> new OptionName("Tall"));
    }

    @Test
    void 옵션_이름이_50자를_초과하면_예외() {
        String longName = "a".repeat(51);
        assertThrows(IllegalArgumentException.class, () -> new OptionName(longName));
    }

    @Test
    void 옵션_이름에_허용되지_않은_문자가_있으면_예외() {
        assertThrows(IllegalArgumentException.class, () -> new OptionName("이름@오류"));
    }

    @Test
    void 옵션_이름이_null이면_예외() {
        assertThrows(IllegalArgumentException.class, () -> new OptionName(null));
    }
}
