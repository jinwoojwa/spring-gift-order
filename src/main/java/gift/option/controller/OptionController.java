package gift.option.controller;

import gift.common.annotation.LoginMember;
import gift.option.dto.OptionRequestDto;
import gift.option.dto.OptionResponseDto;
import gift.option.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<List<OptionResponseDto>> getOption(
            @PathVariable Long productId,
            @LoginMember Long memberId
    ) {
        List<OptionResponseDto> options = optionService.getOptionsByProductId(productId, memberId);

        return ResponseEntity.status(HttpStatus.OK).body(options);
    }

    @PostMapping
    public ResponseEntity<OptionResponseDto> addOption(
            @LoginMember Long memberId,
            @PathVariable Long productId,
            @Valid @RequestBody OptionRequestDto requestDto
    ) {
        OptionResponseDto responseDto = optionService.addOption(productId, requestDto, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
