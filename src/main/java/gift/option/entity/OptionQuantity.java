package gift.option.entity;

import jakarta.persistence.Column;

public record OptionQuantity(
        @Column(name = "quantity", nullable = false)
        int quantity
) {
    private static final int MIN = 1;
    private static final int MAX = 100_000_000;

    public OptionQuantity {
        if (quantity < MIN || quantity >= MAX) {
            throw new IllegalArgumentException("옵션 수량은 1개 이상 1억 개 미만이어야 합니다.");
        }
    }

    public OptionQuantity decreseQuantity(int amount) {
        if (quantity - amount < MIN) {
            throw new IllegalArgumentException("옵션 수량은 최소 1개 이상이어야 합니다.");
        }
        return new OptionQuantity(quantity - amount);
    }

    public int value() {
        return quantity;
    }
}
