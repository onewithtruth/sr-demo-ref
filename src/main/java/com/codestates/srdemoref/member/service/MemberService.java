package com.codestates.srdemoref.member.service;

import com.codestates.srdemoref.exception.BusinessLogicException;
import com.codestates.srdemoref.exception.ExceptionCode;
import com.codestates.srdemoref.member.entity.MemberEntity;
import com.codestates.srdemoref.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberEntity createMember(MemberEntity member) {
        // validate email
        verifyExistsEmail(member.getEmail());

        return memberRepository.save(member);
    }

    public MemberEntity updateMember(MemberEntity member) {
        MemberEntity findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));
        Optional.ofNullable(member.getMemberStatus())
                .ifPresent(memberStatus -> findMember.setMemberStatus(memberStatus));
        findMember.setUpdatedAt(LocalDateTime.now());

        return memberRepository.save(findMember);
    }

    public MemberEntity findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<MemberEntity> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    public void deleteMember(long memberId) {
        MemberEntity findMember = findVerifiedMember(memberId);

        memberRepository.delete(findMember);
    }

    public MemberEntity findVerifiedMember(long memberId) {
        Optional<MemberEntity> optionalMember =
                memberRepository.findById(memberId);
        MemberEntity findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private void verifyExistsEmail(String email) {
        Optional<MemberEntity> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
}
