package gift;

import gift.member.entity.Member;
import gift.member.entity.Role;
import gift.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        Member data = new Member("test@example.com", "testpassword", Role.USER);
        Member savedData = memberRepository.save(data);

        assertAll(
                () -> assertThat(savedData.getId()).isNotNull(),
                () -> assertThat(savedData.getEmail()).isEqualTo(data.getEmail()),
                () -> assertThat(savedData.getPassword()).isEqualTo(data.getPassword()),
                () -> assertThat(savedData.getRole()).isEqualTo(data.getRole())
        );
    }

    @Test
    void findByEmail() {
        Member data = new Member("admintest@example.com", "admintest", Role.ADMIN);
        memberRepository.save(data);

        Member found = memberRepository.findByEmail(data.getEmail()).orElseThrow();
        assertThat(found.getEmail()).isEqualTo(data.getEmail());
    }
}
