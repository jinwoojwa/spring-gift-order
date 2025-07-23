package gift;

import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Product data = new Product("Green tea", 3500, "exampleUrl");
        Product savedData = productRepository.save(data);

        assertAll(
                () -> assertThat(savedData.getId()).isNotNull(),
                () -> assertThat(savedData.getName()).isEqualTo(data.getName()),
                () -> assertThat(savedData.getPrice()).isEqualTo(data.getPrice()),
                () -> assertThat(savedData.getImageUrl()).isEqualTo(data.getImageUrl())
        );
    }

    @Test
    void findById() {
        Product data = new Product("Green tea", 3500, "exampleUrl");
        productRepository.save(data);

        Product found = productRepository.findById(data.getId()).orElseThrow();
        assertThat(found.getName()).isEqualTo(data.getName());
    }
}
