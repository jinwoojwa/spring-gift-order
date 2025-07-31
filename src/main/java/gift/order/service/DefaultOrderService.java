package gift.order.service;

import gift.common.exception.MemberNotFoundException;
import gift.common.exception.OptionNotFoundException;
import gift.member.entity.Member;
import gift.member.repository.MemberRepository;
import gift.oauth.entity.UserKakaoToken;
import gift.option.entity.Option;
import gift.option.repository.OptionRepository;
import gift.order.dto.OrderInfoDto;
import gift.order.dto.OrderRequestDto;
import gift.order.dto.OrderResponseDto;
import gift.order.entity.Order;
import gift.order.event.OrderEvent;
import gift.order.repository.OrderRepository;
import gift.wishlist.entity.Wishlist;
import gift.wishlist.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DefaultOrderService implements OrderService {

    private final MemberRepository memberRepository;
    private final OptionRepository optionRepository;
    private final WishlistRepository wishlistRepository;
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    public DefaultOrderService(
            MemberRepository memberRepository,
            OptionRepository optionRepository,
            WishlistRepository wishlistRepository,
            OrderRepository orderRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.memberRepository = memberRepository;
        this.optionRepository = optionRepository;
        this.wishlistRepository = wishlistRepository;
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(Long memberId, OrderRequestDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        Option option = optionRepository.findById(requestDto.optionId())
                .orElseThrow(() -> new OptionNotFoundException(requestDto.optionId()));

        UserKakaoToken kakaoToken = member.getKakaoToken();
        if (kakaoToken == null || kakaoToken.getAccessToken() == null) {
            throw new IllegalStateException("❗ 카카오 액세스 토큰이 존재하지 않습니다.");
        }

        // 수량 차감
        option.decreaseQuantity(requestDto.quantity());

        // 위시리스트에 상품 있을 시 삭제
        Optional<Wishlist> wishlist = wishlistRepository.findByMemberIdAndProductId(memberId, option.getProduct().getId());
        wishlist.ifPresent(wishlistRepository::delete);

        // 주문 저장
        Order order = new Order(member, option, requestDto.quantity(), requestDto.message(), LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        // 주문 이벤트 발행
        OrderInfoDto infoDto = new OrderInfoDto(
                savedOrder.getId(),
                savedOrder.getOption().getProduct().getName(),
                savedOrder.getOption().getName().name(),
                savedOrder.getQuantity(),
                savedOrder.getMessage(),
                savedOrder.getOrderDateTime(),
                kakaoToken.getAccessToken()
        );
        eventPublisher.publishEvent(new OrderEvent(infoDto));

        return new OrderResponseDto(
                savedOrder.getId(),
                option.getId(),
                savedOrder.getQuantity(),
                savedOrder.getOrderDateTime(),
                savedOrder.getMessage()
        );
    }
}
