package gift.option.repository;

import gift.option.entity.Option;
import gift.option.entity.OptionName;
import gift.product.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    // 특정 상품의 옵션 목록을 조회하는 메서드
    @EntityGraph(attributePaths = {"product"})
    List<Option> findByProduct(Product product);

    // 옵션 이름 중복 체크 메서드
    boolean existsByProductAndName(Product product, OptionName name);
}
