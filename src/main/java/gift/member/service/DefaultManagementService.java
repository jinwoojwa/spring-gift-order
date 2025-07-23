package gift.member.service;

import gift.common.exception.MemberNotFoundException;
import gift.member.dto.MemberRequestDto;
import gift.member.dto.MemberResponseDto;
import gift.member.dto.MemberUpdateRequestDto;
import gift.member.entity.Member;
import gift.member.repository.MemberRepository;
import gift.common.security.PasswordUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultManagementService implements MemberManagementService {

    private final MemberRepository memberRepository;

    public DefaultManagementService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public MemberResponseDto addMember(MemberRequestDto dto) {

        String encodedPassword = PasswordUtil.sha256(dto.password());
        Member member = new Member(dto.email(), encodedPassword, dto.role());
        Member savedMember = memberRepository.save(member);

        return new MemberResponseDto(savedMember);
    }

    @Override
    public MemberResponseDto updateMember(Long id, MemberRequestDto requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));

        String encodedPassword = PasswordUtil.sha256(requestDto.password());

        member.updateEmail(requestDto.email());
        member.updatePassword(encodedPassword);
        member.updateRole(requestDto.role());
        memberRepository.save(member);

        return new MemberResponseDto(member);
    }

    @Override
    public MemberResponseDto deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));

        memberRepository.delete(member);
        return new MemberResponseDto(member);
    }

}
