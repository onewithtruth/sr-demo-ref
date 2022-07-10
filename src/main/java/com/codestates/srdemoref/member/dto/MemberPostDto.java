package com.codestates.srdemoref.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class MemberPostDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "Name should not be empty")
    private String name;

    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
            message = "phone number should have 11 digits, " +
                    "start with 010 and seperated with '-'.")
    private String phone;

    @Builder
    public MemberPostDto(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
