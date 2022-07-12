package com.codestates.srdemoref.member.controller;

import com.codestates.srdemoref.member.dto.MemberPatchDto;
import com.codestates.srdemoref.member.dto.MemberPostDto;
import com.codestates.srdemoref.member.entity.MemberEntity;
import com.codestates.srdemoref.member.mapper.MemberMapper;
import com.codestates.srdemoref.member.repository.MemberRepository;
import com.codestates.srdemoref.member.service.MemberService;
import com.codestates.srdemoref.restDocUtils.RestDocsTestSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.codestates.srdemoref.restDocUtils.RestDocsConfig.field;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class MemberControllerTest extends RestDocsTestSupport {

    @AfterEach
    public void cleanup() {
        memberRepository.deleteAll();
        memberRepository.flush();
    }

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("post_Member TEST")
    public void member_post() throws Exception {
        //given
        String email = "hgd01@gmail.com";
        String name = "hong gil dong";
        String phone = "010-1111-1111";

        MemberPostDto requestDto = MemberPostDto.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .build();

        String url = "http://localhost:8080/members";

        //when
        ResultActions result = this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))

        );

        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("email").description("email").attributes(field("constraints", "길이 10 이하")),
                                        fieldWithPath("name").description("name").attributes(field("constraints", "Name should not be empty")),
                                        fieldWithPath("phone").description("pone number").attributes(field("constraints", "phone number should have 11 digits, " +
                                                "start with 010 and seperated with '-'."))
                                ),
                                responseFields(
                                        fieldWithPath("data.memberId").description(1),
                                        fieldWithPath("data.email").description("hgd01@gmail.com"),
                                        fieldWithPath("data.name").description("hong gil dong"),
                                        fieldWithPath("data.phone").description("010-1111-1111"),
                                        fieldWithPath("data.memberStatus").description("ACTIVE MEMBER")
                                )
                        )
                );
    }

    @Test
    @DisplayName("Member not found TEST")
    public void member_notFound() throws Exception {
        //given

        //when
        ResultActions result = this.mockMvc.perform(
                        get("/members/{member-id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                );

        //then
        result.andExpect(status().is4xxClientError())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("member-id").description("memberId")
                                ),
                                responseFields(
                                        fieldWithPath("status").description("404"),
                                        fieldWithPath("message").description("Member not found"),
                                        fieldWithPath("fieldErrors").description("null"),
                                        fieldWithPath("violationErrors").description("null")
                                )
                        )
                );
    }

    @Test
    @DisplayName("GET_Member TEST")
    public void member_get() throws Exception {
        //given
        String email = "hgd01@gmail.com";
        String name = "hong gil dong";
        String phone = "010-1111-1111";

        MemberPostDto requestDto = MemberPostDto.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .build();

        MemberEntity member = memberRepository.save(memberMapper.memberPostDtoToMember(requestDto));
        long memberId = member.getMemberId();

        //when

        ResultActions result = this.mockMvc.perform(
                        get("/members/{member-id}", memberId)
                                .contentType(MediaType.APPLICATION_JSON)


        );

        //then
        result.andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("member-id").description("memberId")
                                ),
                                responseFields(
                                        fieldWithPath("data.memberId").description(1),
                                        fieldWithPath("data.email").description("hgd01@gmail.com"),
                                        fieldWithPath("data.name").description("hong gil dong"),
                                        fieldWithPath("data.phone").description("010-1111-1111"),
                                        fieldWithPath("data.memberStatus").description("ACTIVE MEMBER")
                                )
                        )
                );
    }

    public void createMemberList() {
       for (int i = 10; i <= 99; i++) {
            String email = "@gmail.com";
            String name = "hong gil dong";
            String phone = "010-1111-11";

            email = "hgd" + i + email;
            name = name + i;
            phone = phone + i;

            MemberPostDto requestDto = MemberPostDto.builder()
                    .email(email)
                    .name(name)
                    .phone(phone)
                    .build();

            memberRepository.save(memberMapper.memberPostDtoToMember(requestDto));
        }
    }

    @Test
    @DisplayName("GET_Members TEST")
    public void members_get() throws Exception {
        //given
        //when
        this.createMemberList();

        ResultActions result = this.mockMvc.perform(
                get("/members")
                        .param("size", "5")
                        .param("page", "1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParameters(
                                        parameterWithName("size").description(5),
                                        parameterWithName("page").description(1)
                                )
                        )
                );
    }

    @Test
    @DisplayName("PATCH_Member TEST")
    public void member_patch() throws Exception {
        //given
        String email = "hgd01@gmail.com";
        String name = "hong gil dong";
        String phone = "010-1111-1111";

        MemberPostDto postRequestDto = MemberPostDto.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .build();

        MemberEntity member = memberRepository.save(memberMapper.memberPostDtoToMember(postRequestDto));

        long memberId = member.getMemberId();
        MemberEntity.MemberStatus memberStatus = MemberEntity.MemberStatus.MEMBER_SLEEP;

        MemberPatchDto patchRequestDto = MemberPatchDto.builder()
                .memberId(memberId)
                .email(email)
                .name(name)
                .phone(phone)
                .memberStatus(memberStatus)
                .build();

        //when
        ResultActions result = this.mockMvc.perform(
                patch("/members/{member-id}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchRequestDto))
        );

        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("member-id").description("memberId")
                                ),
                                requestFields(
                                        fieldWithPath("memberId").description("1"),
                                        fieldWithPath("email").description("email").attributes(field("constraints", "길이 10 이하")),
                                        fieldWithPath("name").description("name").attributes(field("constraints", "Name should not be empty")),
                                        fieldWithPath("phone").description("pone number").attributes(field("constraints", "phone number should have 11 digits, " +
                                                "start with 010 and seperated with '-'.")),
                                        fieldWithPath("memberStatus").description("memberStatus")
                                ),
                                responseFields(
                                        fieldWithPath("data.memberId").description(1),
                                        fieldWithPath("data.email").description("hgd01@gmail.com"),
                                        fieldWithPath("data.name").description("hong gil dong"),
                                        fieldWithPath("data.phone").description("010-1111-1111"),
                                        fieldWithPath("data.memberStatus").description("SLEEP MEMBER")
                                )
                        )
                );


    }

    @Test
    @DisplayName("DELETE_Member TEST")
    public void member_delete() throws Exception {
        //given
        String email = "hgd01@gmail.com";
        String name = "hong gil dong";
        String phone = "010-1111-1111";

        MemberPostDto requestDto = MemberPostDto.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .build();

        MemberEntity member = memberRepository.save(memberMapper.memberPostDtoToMember(requestDto));
        long memberId = member.getMemberId();
        //when
        ResultActions result = this.mockMvc.perform(
                delete("/members/{member-id}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("member-id").description("memberId")
                                )
                        )
                );
    }

}