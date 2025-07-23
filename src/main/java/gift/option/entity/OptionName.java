package gift.option.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record OptionName(
        @Column(name = "name", length = 50, nullable = false)
        String name
) {
    private static final int MAX_LENGTH = 50;
    private static final Pattern VALID_PATTERN = Pattern.compile("^[\\p{L}\\p{N} ()\\[\\]+\\-&/_]{1,50}$");

    public OptionName {
        if (name == null) {
            throw new IllegalArgumentException("옵션 이름은 null일 수 없습니다.");
        }

        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("옵션 이름은 최대 " + MAX_LENGTH + "자까지 입력이 가능합니다.");
        }

        if (!VALID_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("옵션 이름에 허용되지 않은 문자가 포함되어 있습니다.");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
