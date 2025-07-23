package gift.product.service;

import gift.product.dto.ProductRequestDto;
import gift.product.dto.ProductResponseDto;
import gift.product.entity.Product;
import gift.common.exception.ForbiddenWordException;
import gift.common.exception.ProductNotFoundException;
import gift.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 생성
    @Override
    public ProductResponseDto addProduct(ProductRequestDto requestDto) {
        if (requestDto.name().contains("카카오")) {
            throw new ForbiddenWordException("카카오");
        }

        Product product = Product.createProduct(
                requestDto.name(),
                requestDto.price(),
                requestDto.imageUrl(),
                requestDto.options()
        );

        Product savedProduct = productRepository.save(product);
        return new ProductResponseDto(savedProduct);
    }

    // 특정 상품 조회
    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return new ProductResponseDto(product);
    }

    // 모든 상품 조회
    @Override
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponseDto::new);
    }

    // 특정 상품 수정
    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.update(requestDto.name(), requestDto.price(), requestDto.imageUrl());
        return new ProductResponseDto(product);
    }

    // 특정 상품 삭제
    @Override
    public ProductResponseDto deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
        return new ProductResponseDto(product);
    }
}
