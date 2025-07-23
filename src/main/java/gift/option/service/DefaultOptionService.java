package gift.option.service;

import gift.common.exception.ProductNotFoundException;
import gift.option.dto.OptionRequestDto;
import gift.option.dto.OptionResponseDto;
import gift.option.entity.Option;
import gift.option.entity.OptionName;
import gift.option.repository.OptionRepository;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DefaultOptionService implements OptionService {

    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public DefaultOptionService(ProductRepository productRepository,  OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    @Override
    public List<OptionResponseDto> getOptionsByProductId(Long productId, Long memberId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        List<Option> options = optionRepository.findByProduct(product);

        return options.stream()
                .map(OptionResponseDto::from)
                .toList();
    }

    @Override
    public OptionResponseDto addOption(Long productId, OptionRequestDto requestDto, Long memberId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        OptionName optionName = new OptionName(requestDto.name());

        boolean isDuplicate = optionRepository.existsByProductAndName(product, optionName);
        if (isDuplicate) {
            throw new IllegalArgumentException(requestDto.name() + "이라는 이름의 옵션이 이미 존재합니다.");
        }

        Option option = Option.of(requestDto.name(), requestDto.quantity(), product);
        product.addOption(option);

        optionRepository.save(option);

        return OptionResponseDto.from(option);
    }
}
