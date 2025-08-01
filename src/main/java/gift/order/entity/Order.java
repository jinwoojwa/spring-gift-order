package gift.order.entity;

import gift.member.entity.Member;
import gift.option.entity.Option;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_id", nullable = false)
    private Option option;

    @Column(nullable = false)
    private int quantity;

    @Column(length = 255)
    private String message;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    protected Order() {}

    public Order(Member member, Option option, int quantity, String message, LocalDateTime orderDateTime) {
        this.member = member;
        this.option = option;
        this.quantity = quantity;
        this.message = message;
        this.orderDateTime = orderDateTime;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Option getOption() {
        return option;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
}
