package gift.member.entity;

import gift.member.dto.MemberUpdateRequestDto;
import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // USER, ADMIN

    protected Member() {
    }

    public Member(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }

    public void updateEmail(String email) {
        this.email = email;
    }
    public void updatePassword(String password) {
        this.password = password;
    }
    public void updateRole(Role role) {
        this.role = role;
    }
}
