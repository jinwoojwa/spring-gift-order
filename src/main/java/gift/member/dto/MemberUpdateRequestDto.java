package gift.member.dto;

import gift.member.entity.Role;

public record MemberUpdateRequestDto(
        String email,
        String password,
        Role role
) { }
