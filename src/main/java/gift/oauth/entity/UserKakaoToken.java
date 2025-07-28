package gift.oauth.entity;

import gift.member.entity.Member;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class UserKakaoToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;
    private String refreshToken;
    private Instant accessTokenExpiresAt;
    private Instant refreshTokenExpiresAt;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    protected UserKakaoToken() {}

    public UserKakaoToken(Member member, String accessToken, String refreshToken,
                          Instant accessTokenExpiresAt, Instant refreshTokenExpiresAt) {
        this.member = member;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresAt = accessTokenExpiresAt;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public void update(String accessToken, Instant accessTokenExpiresAt) {
        this.accessToken = accessToken;
        this.accessTokenExpiresAt = accessTokenExpiresAt;
    }
}
