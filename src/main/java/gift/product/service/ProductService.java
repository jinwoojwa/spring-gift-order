package gift.product.service;

import gift.product.dto.ProductRequestDto;
import gift.product.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponseDto addProduct(ProductRequestDto requestDto);

    ProductResponseDto getProductById(Long id);

    ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto);

    ProductResponseDto deleteProduct(Long id);

    Page<ProductResponseDto> getAllProducts(Pageable pageable);
}
