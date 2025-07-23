package gift.product.entity;

import gift.common.exception.ProductOptionRequiredException;
import gift.option.dto.OptionRequestDto;
import gift.option.entity.Option;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    protected Product() {
    }

    private Product(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static Product createProduct(String name, int price, String imageUrl, List<OptionRequestDto> optionDto) {
        if (optionDto == null || optionDto.isEmpty()) {
            throw new ProductOptionRequiredException();
        }

        Product product = new Product(name, price, imageUrl);

        for (OptionRequestDto dto : optionDto) {
            Option option = Option.of(dto.name(), dto.quantity(), product);
            product.addOption(option);
        }

        return product;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // 상품 수정을 위한 메서드
    public void update(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void addOption(Option option) {
        this.options.add(option);
        option.setProduct(this);
    }
}
