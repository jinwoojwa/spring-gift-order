package gift.option.entity;

import gift.product.entity.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "option")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OptionName name;

    @Embedded
    private OptionQuantity quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected Option() {}

    private Option(OptionName name, OptionQuantity quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public static Option of(String name, int quantity, Product product) {
        return new Option(new OptionName(name), new  OptionQuantity(quantity), product);
    }

    // 수량 감소 메서드
    public void decreaseQuantity(int amount) {
        this.quantity = this.quantity.decreseQuantity(amount);
    }

    public Long getId() {
        return id;
    }

    public OptionName getName() {
        return name;
    }

    public OptionQuantity getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
