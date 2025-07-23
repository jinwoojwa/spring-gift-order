package gift.wishlist.service;

import gift.common.exception.MemberNotFoundException;
import gift.common.exception.ProductNotFoundException;
import gift.common.exception.WishlistItemNotFoundException;
import gift.member.entity.Member;
import gift.member.repository.MemberRepository;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import gift.wishlist.dto.WishlistRequestDto;
import gift.wishlist.dto.WishlistResponseDto;
import gift.wishlist.entity.Wishlist;
import gift.wishlist.repository.WishlistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultWishlistService implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public DefaultWishlistService(WishlistRepository wishlistRepository,
                                  MemberRepository memberRepository,
                                  ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Override
    public WishlistResponseDto addProductToWishlist(Long memberId, WishlistRequestDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        Product product = productRepository.findById(requestDto.productId())
                .orElseThrow(() -> new ProductNotFoundException(requestDto.productId()));

        Wishlist wishlist = wishlistRepository.findByMemberIdAndProductId(memberId, requestDto.productId())
                .orElseGet(() -> new Wishlist(member, product, 0));

        wishlist.updateQuantity(wishlist.getQuantity() + requestDto.quantity());
        wishlistRepository.save(wishlist);

        return new WishlistResponseDto(wishlist);
    }

    @Override
    public Page<WishlistResponseDto> getWishlist(Long memberId, Pageable pageable) {
        Page<Wishlist> wishlistPage = wishlistRepository.findByMemberId(memberId, pageable);
        return wishlistPage.map(WishlistResponseDto::new);
    }

    @Override
    public WishlistResponseDto deleteProductFromWishlist(Long memberId, Long productId) {
        Wishlist wishlist = wishlistRepository.findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(WishlistItemNotFoundException::new);

        wishlistRepository.delete(wishlist);
        return new WishlistResponseDto(wishlist);
    }

    @Override
    public WishlistResponseDto updateProductQuantity(Long memberId, WishlistRequestDto requestDto) {
        Wishlist wishlist = wishlistRepository.findByMemberIdAndProductId(memberId, requestDto.productId())
                .orElseThrow(WishlistItemNotFoundException::new);

        int newQuantity = requestDto.quantity();
        if (newQuantity == 0) {
            wishlistRepository.delete(wishlist);
            return null;
        }

        wishlist.updateQuantity(newQuantity);
        wishlistRepository.save(wishlist);

        return new WishlistResponseDto(wishlist);
    }
}
