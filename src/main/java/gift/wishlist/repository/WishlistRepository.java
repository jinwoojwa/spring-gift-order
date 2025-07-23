package gift.wishlist.repository;

import gift.wishlist.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @EntityGraph(attributePaths = {"product"})
    Page<Wishlist> findByMemberId(Long memberId, Pageable pageable);

    Optional<Wishlist> findByMemberIdAndProductId(Long memberId, Long productId);

    void deleteByMemberIdAndProductId(Long memberId, Long productId);
}
