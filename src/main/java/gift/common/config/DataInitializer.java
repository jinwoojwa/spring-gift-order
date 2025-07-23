package gift.common.config;

import gift.member.entity.Role;
import gift.member.repository.MemberRepository;
import gift.option.dto.OptionRequestDto;
import gift.product.entity.Product;
import gift.member.entity.Member;
import gift.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public DataInitializer(ProductRepository productRepository,
                           MemberRepository memberRepository
                           ) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 제품 초기 데이터
        Product coldBrew = Product.createProduct(
                "콜드브루", 4500,
                "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[9200000000038]_20250626095744579.jpg",
                List.of(
                        new OptionRequestDto("Tall", 3)
                )
        );

        Product americano = Product.createProduct(
                "아메리카노", 4000,
                "https://image.istarbucks.co.kr/upload/store/skuimg/2025/06/[110563]_20250626094354080.jpg",
                List.of(
                        new OptionRequestDto("Tall", 10),
                        new OptionRequestDto("Grande", 5)
                )
        );

        Product cappuccino = Product.createProduct(
                "카푸치노", 5000,
                "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[38]_20210415154821991.jpg",
                List.of(
                        new OptionRequestDto("Venti", 2)
                )
        );

        productRepository.save(coldBrew);
        productRepository.save(americano);
        productRepository.save(cappuccino);

        // 회원 초기 데이터
        memberRepository.save(new Member("user1@example.com", "password1", Role.USER));
        memberRepository.save(new Member("admin@example.com", "adminpwd", Role.ADMIN));
        memberRepository.save(new Member("user2@example.com", "password2", Role.USER));
    }
}
