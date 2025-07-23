package gift.wishlist.dto;

import gift.wishlist.entity.Wishlist;

public record WishlistResponseDto(
        Long productId,
        String productName,
        int quantity,
        int price,
        String imageUrl
) {
    public WishlistResponseDto(Wishlist wishlist) {
        this(
                wishlist.getProduct().getId(),
                wishlist.getProduct().getName(),
                wishlist.getQuantity(),
                wishlist.getProduct().getPrice(),
                wishlist.getProduct().getImageUrl()
        );
    }
}
