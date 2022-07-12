package com.codestates.srdemoref.member.mapper;

import com.codestates.srdemoref.member.dto.MemberPatchDto;
import com.codestates.srdemoref.member.dto.MemberPostDto;
import com.codestates.srdemoref.member.dto.MemberResponseDto;
import com.codestates.srdemoref.member.dto.MemberResponseDto.MemberResponseDtoBuilder;
import com.codestates.srdemoref.member.entity.MemberEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-11T16:02:09+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.15 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberEntity memberPostDtoToMember(MemberPostDto memberPostDto) {
        if ( memberPostDto == null ) {
            return null;
        }

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setEmail( memberPostDto.getEmail() );
        memberEntity.setName( memberPostDto.getName() );
        memberEntity.setPhone( memberPostDto.getPhone() );

        return memberEntity;
    }

    @Override
    public MemberEntity memberPatchDtoToMember(MemberPatchDto memberPatchDto) {
        if ( memberPatchDto == null ) {
            return null;
        }

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMemberId( memberPatchDto.getMemberId() );
        memberEntity.setEmail( memberPatchDto.getEmail() );
        memberEntity.setName( memberPatchDto.getName() );
        memberEntity.setPhone( memberPatchDto.getPhone() );
        memberEntity.setMemberStatus( memberPatchDto.getMemberStatus() );

        return memberEntity;
    }

    @Override
    public MemberResponseDto memberToMemberResponseDto(MemberEntity member) {
        if ( member == null ) {
            return null;
        }

        MemberResponseDtoBuilder memberResponseDto = MemberResponseDto.builder();

        if ( member.getMemberId() != null ) {
            memberResponseDto.memberId( member.getMemberId() );
        }
        memberResponseDto.email( member.getEmail() );
        memberResponseDto.name( member.getName() );
        memberResponseDto.phone( member.getPhone() );
        memberResponseDto.memberStatus( member.getMemberStatus() );

        return memberResponseDto.build();
    }

    @Override
    public List<MemberResponseDto> membersToMemberResponseDtos(List<MemberEntity> members) {
        if ( members == null ) {
            return null;
        }

        List<MemberResponseDto> list = new ArrayList<MemberResponseDto>( members.size() );
        for ( MemberEntity memberEntity : members ) {
            list.add( memberToMemberResponseDto( memberEntity ) );
        }

        return list;
    }
}
