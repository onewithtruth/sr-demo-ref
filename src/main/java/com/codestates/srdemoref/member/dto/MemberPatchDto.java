package com.codestates.srdemoref.member.dto;

import com.codestates.srdemoref.member.entity.MemberEntity;
import com.codestates.srdemoref.validator.NotSpace;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.lang.reflect.Member;

@Getter
@NoArgsConstructor
public class MemberPatchDto {
    private long memberId;

    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "Name should not be empty")
    private String name;

    @NotBlank(message = "phone number should not be empty")
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
            message = "phone number should have 11 digits, " +
                    "start with 010 and seperated with '-'.")
    private String phone;

    private MemberEntity.MemberStatus memberStatus;

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    @Builder
    public MemberPatchDto(long memberId,
                          String email,
                          String name,
                          String phone,
                          MemberEntity.MemberStatus memberStatus) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.memberStatus = memberStatus;
    }
}
