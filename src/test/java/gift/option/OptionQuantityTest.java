package gift.option;

import gift.option.entity.OptionQuantity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OptionQuantityTest {

    @Test
    void 정상_수량_생성() {
        assertDoesNotThrow(() -> new OptionQuantity(100));
    }

    @Test
    void 음수_수량이면_예외() {
        assertThrows(IllegalArgumentException.class, () -> new OptionQuantity(-1));
    }

    @Test
    void 수량_감소_정상() {
        OptionQuantity quantity = new OptionQuantity(10);
        OptionQuantity result = quantity.decreseQuantity(5);

        assertEquals(5, result.value());
    }
}
