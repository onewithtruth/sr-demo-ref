package com.codestates.srdemoref.member.mapper;

import com.codestates.srdemoref.member.dto.MemberPatchDto;
import com.codestates.srdemoref.member.dto.MemberPostDto;
import com.codestates.srdemoref.member.dto.MemberResponseDto;
import com.codestates.srdemoref.member.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

//componentModel = "spring" -> config for spring bean
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    MemberEntity memberPostDtoToMember(MemberPostDto memberPostDto);
    MemberEntity memberPatchDtoToMember(MemberPatchDto memberPatchDto);
    MemberResponseDto memberToMemberResponseDto(MemberEntity member);
    List<MemberResponseDto> membersToMemberResponseDtos(List<MemberEntity> members);
}
