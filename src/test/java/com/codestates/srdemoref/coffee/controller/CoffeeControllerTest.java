package com.codestates.srdemoref.coffee.controller;

import com.codestates.srdemoref.coffee.dto.CoffeePatchDto;
import com.codestates.srdemoref.coffee.dto.CoffeePostDto;
import com.codestates.srdemoref.coffee.entity.CoffeeEntity;
import com.codestates.srdemoref.coffee.mapper.CoffeeMapper;
import com.codestates.srdemoref.coffee.repository.CoffeeRepository;
import com.codestates.srdemoref.restDocUtils.RestDocsTestSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
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
class CoffeeControllerTest extends RestDocsTestSupport {

    @AfterEach
    public void cleanup() {
        coffeeRepository.deleteAll();
        coffeeRepository.flush();
    }

    @Autowired
    CoffeeMapper coffeeMapper;

    @Autowired
    CoffeeRepository coffeeRepository;

    @Test
    void postCoffee() throws Exception {
        //given
        String korName = "아메리카노";
        String engName = "Americano";
        int price = 2500;
        String coffeeCode = "aaa";

        CoffeePostDto coffeePostDto = CoffeePostDto.builder()
                .korName(korName)
                .engName(engName)
                .price(price)
                .coffeeCode(coffeeCode)
                .build();

        String url = "http://localhost:8080/coffees";

        //when
        ResultActions result = this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(coffeePostDto))

        );

        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("korName").description("아메리카노"),
                                        fieldWithPath("engName").description("Americano"),
                                        fieldWithPath("price").description(2500),
                                        fieldWithPath("coffeeCode").description("a10")
                                ),
                                responseFields(
                                        fieldWithPath("data.coffeeId").description(1),
                                        fieldWithPath("data.korName").description("아메리카노"),
                                        fieldWithPath("data.engName").description("Americano"),
                                        fieldWithPath("data.price").description(2500),
                                        fieldWithPath("data.coffeeStatus").description("FOR SALE")
                                )
                        )
                );


    }

    @Test
    void patchCoffee() throws Exception {
        //given
        String korName = "아메리카노";
        String engName = "Americano";
        int price = 2500;
        String coffeeCode = "aaa";

        CoffeePostDto coffeePostDto = CoffeePostDto.builder()
                .korName(korName)
                .engName(engName)
                .price(price)
                .coffeeCode(coffeeCode)
                .build();

        String url = "http://localhost:8080/coffees";

        coffeeRepository.save(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));

        long coffeeId = 2L;
        CoffeeEntity.CoffeeStatus coffeeStatus = CoffeeEntity.CoffeeStatus.COFFEE_SOLD_OUT;

        CoffeePatchDto coffeePatchDto = CoffeePatchDto.builder()
                .coffeeId(coffeeId)
                .korName(korName)
                .engName(engName)
                .price(price)
                .coffeeStatus(coffeeStatus)
                .coffeeCode(coffeeCode)
                .build();


        //when
        ResultActions result = this.mockMvc.perform(
                patch(url + "/{coffee-id}", coffeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffeePatchDto))
        );

        //then
        result.andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("coffeeId").description(1),
                                        fieldWithPath("korName").description("아메리카노"),
                                        fieldWithPath("engName").description("Americano"),
                                        fieldWithPath("price").description(2500),
                                        fieldWithPath("coffeeCode").description("aaa"),
                                        fieldWithPath("coffeeStatus").description("OUT OF STOCK")
                                ),
                                responseFields(
                                        fieldWithPath("data.coffeeId").description(1),
                                        fieldWithPath("data.korName").description("아메리카노"),
                                        fieldWithPath("data.engName").description("Americano"),
                                        fieldWithPath("data.price").description(2500),
                                        fieldWithPath("data.coffeeStatus").description("OUT OF STOCK")
                                )
                        )
                );

    }

    @Test
    void getCoffee() throws Exception {
        //given
        String korName = "아메리카노";
        String engName = "Americano";
        int price = 2500;
        String coffeeCode = "aaa";

        CoffeePostDto coffeePostDto = CoffeePostDto.builder()
                .korName(korName)
                .engName(engName)
                .price(price)
                .coffeeCode(coffeeCode)
                .build();

        String url = "http://localhost:8080/coffees";

        coffeeRepository.save(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));

        long coffeeId = 3L;

        //when
        ResultActions result = this.mockMvc.perform(
                get(url + "/{coffee-id}", coffeeId)
                        .contentType(MediaType.APPLICATION_JSON)
        );



        //then
        result.andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("coffee-id").description("coffeeId")
                                ),
                                responseFields(
                                        fieldWithPath("data.coffeeId").description(1),
                                        fieldWithPath("data.korName").description("아메리카노"),
                                        fieldWithPath("data.engName").description("Americano"),
                                        fieldWithPath("data.price").description(2500),
                                        fieldWithPath("data.coffeeStatus").description("FOR SALE")
                                )
                        )
                );


    }

    public void createCoffeeList() {
        for (int i = 10; i <= 99; i++) {
            String korName = "아메리카노";
            String engName = "Americano";
            int price = 2500;
            String coffeeCode = "a";

            korName += i;
            price += i;
            coffeeCode += i;

            CoffeePostDto coffeePostDto = CoffeePostDto.builder()
                    .korName(korName)
                    .engName(engName)
                    .price(price)
                    .coffeeCode(coffeeCode)
                    .build();

            coffeeRepository.save(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));
        }
    }

    @Test
    void getCoffees() throws Exception {
        //given
        this.createCoffeeList();

        String url = "http://localhost:8080/coffees";


        //when
        ResultActions result = this.mockMvc.perform(
                get(url)
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
    void deleteCoffee() throws Exception {
        //given
        String korName = "아메리카노";
        String engName = "Americano";
        int price = 2500;
        String coffeeCode = "aaa";

        CoffeePostDto coffeePostDto = CoffeePostDto.builder()
                .korName(korName)
                .engName(engName)
                .price(price)
                .coffeeCode(coffeeCode)
                .build();

        String url = "http://localhost:8080/coffees";

        coffeeRepository.save(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));

        long coffeeId = 1L;


        //when
        ResultActions result = this.mockMvc.perform(
                delete(url + "/{coffee-id}", coffeeId)
                        .contentType(MediaType.APPLICATION_JSON)
        );


        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("coffee-id").description("coffeeId")
                                )
                        )
                );
    }
}