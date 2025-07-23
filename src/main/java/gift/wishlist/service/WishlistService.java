package gift.wishlist.service;

import gift.wishlist.dto.WishlistRequestDto;
import gift.wishlist.dto.WishlistResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistService {

    WishlistResponseDto addProductToWishlist(Long memberId, WishlistRequestDto requestDto);
    Page<WishlistResponseDto> getWishlist(Long memberId, Pageable pageable);
    WishlistResponseDto deleteProductFromWishlist(Long memberId, Long productId);
    WishlistResponseDto updateProductQuantity(Long memberId, WishlistRequestDto requestDto);
}
