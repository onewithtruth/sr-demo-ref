package com.codestates.srdemoref.order.controller;

import com.codestates.srdemoref.coffee.dto.CoffeePostDto;
import com.codestates.srdemoref.coffee.entity.CoffeeEntity;
import com.codestates.srdemoref.coffee.mapper.CoffeeMapper;
import com.codestates.srdemoref.coffee.repository.CoffeeRepository;
import com.codestates.srdemoref.member.dto.MemberPostDto;
import com.codestates.srdemoref.member.entity.MemberEntity;
import com.codestates.srdemoref.member.mapper.MemberMapper;
import com.codestates.srdemoref.member.repository.MemberRepository;
import com.codestates.srdemoref.order.dto.OrderCoffeeDto;
import com.codestates.srdemoref.order.dto.OrderPatchDto;
import com.codestates.srdemoref.order.dto.OrderPostDto;
import com.codestates.srdemoref.order.entity.OrderEntity;
import com.codestates.srdemoref.order.mapper.OrderMapper;
import com.codestates.srdemoref.order.repository.OrderRepository;
import com.codestates.srdemoref.restDocUtils.RestDocsTestSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class OrderControllerTest extends RestDocsTestSupport {

    @AfterEach
    public void cleanup() {
        orderRepository.deleteAll();
        memberRepository.deleteAll();
        coffeeRepository.deleteAll();
        orderRepository.flush();
        memberRepository.flush();
        coffeeRepository.flush();


    }

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CoffeeRepository coffeeRepository;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    CoffeeMapper coffeeMapper;

    @Test
    void postOrder() throws Exception {
        //given

            //generating COFFEE
        String korName = "아메리카노1";
        String engName = "Americano";
        int price = 2500;
        String coffeeCode = "aa1";

        CoffeePostDto coffeePostDto = CoffeePostDto.builder()
                .korName(korName)
                .engName(engName)
                .price(price)
                .coffeeCode(coffeeCode)
                .build();

        CoffeeEntity coffee = coffeeRepository.save(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));

            //generating MEMBER
        String email = "hgd01@gmail.com";
        String name = "hong gil dong";
        String phone = "010-1111-1111";

        MemberPostDto memberPostDto = MemberPostDto.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .build();

        MemberEntity member = memberRepository.save(memberMapper.memberPostDtoToMember(memberPostDto));

        long memberId = member.getMemberId();;
        long coffeeId = coffee.getCoffeeId();
        int quantity = 3;

        List<OrderCoffeeDto> orderCoffees = new ArrayList<>();

        orderCoffees.add(OrderCoffeeDto.builder()
                .coffeeId(coffeeId)
                .quantity(quantity)
                .build()
        );

        OrderPostDto orderPostDto = OrderPostDto.builder()
                .memberId(memberId)
                .orderCoffees(orderCoffees)
                .build();

        String url = "http://localhost:8080/orders";

        //when
        ResultActions result = this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderPostDto))
        );


        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("memberId").description("memberId"),
                                        fieldWithPath("orderCoffees[].coffeeId").description("coffeeId"),
                                        fieldWithPath("orderCoffees[].quantity").description("quantity")
                                ),
                                responseFields(
                                        fieldWithPath("data.orderId").description("orderId"),
                                        fieldWithPath("data.memberId").description("memberId"),
                                        fieldWithPath("data.orderStatus").description("orderStatus"),
                                        fieldWithPath("data.orderCoffees[].coffeeId").description("coffeeId"),
                                        fieldWithPath("data.orderCoffees[].korName").description("korName"),
                                        fieldWithPath("data.orderCoffees[].engName").description("engName"),
                                        fieldWithPath("data.orderCoffees[].price").description("price"),
                                        fieldWithPath("data.orderCoffees[].quantity").description("quantity"),
                                        fieldWithPath("data.createdAt").description("createdAt")
                                )
                        )
                );


    }

    @Test
    void patchOrder() throws Exception {
        //given

        //generating COFFEE
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

        CoffeeEntity coffee = coffeeRepository.save(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));

        //generating MEMBER
        String email = "hgd02@gmail.com";
        String name = "hong gil dong";
        String phone = "010-1111-1111";

        MemberPostDto memberPostDto = MemberPostDto.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .build();

        MemberEntity member = memberRepository.save(memberMapper.memberPostDtoToMember(memberPostDto));

        long memberId = member.getMemberId();
        long coffeeId = coffee.getCoffeeId();
        int quantity = 3;

        List<OrderCoffeeDto> orderCoffees = new ArrayList<>();

        orderCoffees.add(OrderCoffeeDto.builder()
                .coffeeId(coffeeId)
                .quantity(quantity)
                .build()
        );

        OrderPostDto orderPostDto = OrderPostDto.builder()
                .memberId(memberId)
                .orderCoffees(orderCoffees)
                .build();
        OrderEntity order = orderRepository.save(orderMapper.orderPostDtoToOrder(orderPostDto));

        OrderEntity.OrderStatus orderStatus = OrderEntity.OrderStatus.ORDER_CANCEL;
        long orderId = order.getOrderId();

        OrderPatchDto orderPatchDto = OrderPatchDto.builder()
                .orderId(orderId)
                .orderStatus(orderStatus)
                .build();

        String url = "http://localhost:8080/orders";


        //when
        ResultActions result = this.mockMvc.perform(
                patch(url + "/{order-id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderPatchDto))
        );

        //then
        result.andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("orderId").description("orderId"),
                                        fieldWithPath("orderStatus").description("ORDER_CANCEL")
                                ),
                                responseFields(
                                        fieldWithPath("data.orderId").description("orderId"),
                                        fieldWithPath("data.memberId").description("memberId"),
                                        fieldWithPath("data.orderStatus").description("orderStatus"),
                                        fieldWithPath("data.orderCoffees[].coffeeId").description("coffeeId"),
                                        fieldWithPath("data.orderCoffees[].korName").description("korName"),
                                        fieldWithPath("data.orderCoffees[].engName").description("engName"),
                                        fieldWithPath("data.orderCoffees[].price").description("price"),
                                        fieldWithPath("data.orderCoffees[].quantity").description("quantity"),
                                        fieldWithPath("data.createdAt").description("createdAt")
                                )
                        )
                );


    }

    @Test
    void getOrder() throws Exception {
        //given

        //generating COFFEE
        String korName = "아메리카노3";
        String engName = "Americano";
        int price = 2500;
        String coffeeCode = "aaa";

        CoffeePostDto coffeePostDto = CoffeePostDto.builder()
                .korName(korName)
                .engName(engName)
                .price(price)
                .coffeeCode(coffeeCode)
                .build();

        CoffeeEntity coffee = coffeeRepository.save(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));

        //generating MEMBER
        String email = "hgd03@gmail.com";
        String name = "hong gil dong";
        String phone = "010-1111-1111";

        MemberPostDto memberPostDto = MemberPostDto.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .build();

        MemberEntity member = memberRepository.save(memberMapper.memberPostDtoToMember(memberPostDto));

        long memberId = member.getMemberId();
        long coffeeId = coffee.getCoffeeId();
        int quantity = 3;

        List<OrderCoffeeDto> orderCoffees = new ArrayList<>();

        orderCoffees.add(OrderCoffeeDto.builder()
                .coffeeId(coffeeId)
                .quantity(quantity)
                .build()
        );

        OrderPostDto orderPostDto = OrderPostDto.builder()
                .memberId(memberId)
                .orderCoffees(orderCoffees)
                .build();
        OrderEntity order = orderRepository.save(orderMapper.orderPostDtoToOrder(orderPostDto));

        long orderId = order.getOrderId();

        String url = "http://localhost:8080/orders";

        //when
        ResultActions result = this.mockMvc.perform(
                get(url + "/{order-id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("order-id").description("orderId")
                                ),
                                responseFields(
                                        fieldWithPath("data.orderId").description("orderId"),
                                        fieldWithPath("data.memberId").description("memberId"),
                                        fieldWithPath("data.orderStatus").description("orderStatus"),
                                        fieldWithPath("data.orderCoffees[].coffeeId").description("coffeeId"),
                                        fieldWithPath("data.orderCoffees[].korName").description("korName"),
                                        fieldWithPath("data.orderCoffees[].engName").description("engName"),
                                        fieldWithPath("data.orderCoffees[].price").description("price"),
                                        fieldWithPath("data.orderCoffees[].quantity").description("quantity"),
                                        fieldWithPath("data.createdAt").description("createdAt")
                                )
                        )
                );

    }

    @Test
    void getOrders() throws Exception {
        //given

        //generating COFFEE
        String korName = "아메리카노4";
        String engName = "Americano";
        int price = 2500;
        String coffeeCode = "aaa";

        CoffeePostDto coffeePostDto = CoffeePostDto.builder()
                .korName(korName)
                .engName(engName)
                .price(price)
                .coffeeCode(coffeeCode)
                .build();

        CoffeeEntity coffee = coffeeRepository.save(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));

        //generating MEMBER
        String email = "hgd04@gmail.com";
        String name = "hong gil dong";
        String phone = "010-1111-1111";

        MemberPostDto memberPostDto = MemberPostDto.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .build();

        MemberEntity member = memberRepository.save(memberMapper.memberPostDtoToMember(memberPostDto));

        long memberId = member.getMemberId();
        long coffeeId = coffee.getCoffeeId();
        int quantity = 3;

        List<OrderCoffeeDto> orderCoffees = new ArrayList<>();

        orderCoffees.add(OrderCoffeeDto.builder()
                .coffeeId(coffeeId)
                .quantity(quantity)
                .build()
        );

        OrderPostDto orderPostDto = OrderPostDto.builder()
                .memberId(memberId)
                .orderCoffees(orderCoffees)
                .build();
        for (int i = 0; i < 10; i++) {
            orderRepository.save(orderMapper.orderPostDtoToOrder(orderPostDto));
        }

        long orderId = 2L;

        String url = "http://localhost:8080/orders";

        //when
        ResultActions result = this.mockMvc.perform(
                get(url).param("size", "5")
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
    void cancelOrder() throws Exception {
        //given

        //generating COFFEE
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

        coffeeRepository.save(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));

        //generating MEMBER
        String email = "hgd01@gmail.com";
        String name = "hong gil dong";
        String phone = "010-1111-1111";

        MemberPostDto memberPostDto = MemberPostDto.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .build();

        memberRepository.save(memberMapper.memberPostDtoToMember(memberPostDto));

        long memberId = 1L;
        long coffeeId = 1L;
        int quantity = 3;

        List<OrderCoffeeDto> orderCoffees = new ArrayList<>();

        orderCoffees.add(OrderCoffeeDto.builder()
                .coffeeId(coffeeId)
                .quantity(quantity)
                .build()
        );

        OrderPostDto orderPostDto = OrderPostDto.builder()
                .memberId(memberId)
                .orderCoffees(orderCoffees)
                .build();
        orderRepository.save(orderMapper.orderPostDtoToOrder(orderPostDto));

        long orderId = 1L;

        String url = "http://localhost:8080/orders";

        //when
        ResultActions result = this.mockMvc.perform(
                delete(url + "/{order-id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("order-id").description("orderId")
                                )
                        )
                );
    }

}