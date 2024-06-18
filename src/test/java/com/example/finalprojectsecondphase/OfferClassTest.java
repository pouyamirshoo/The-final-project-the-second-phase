package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.Customer;
import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.Offer;
import com.example.finalprojectsecondphase.entity.Order;
import com.example.finalprojectsecondphase.entity.SubDuty;
import com.example.finalprojectsecondphase.entity.enums.BestTime;
import com.example.finalprojectsecondphase.entity.enums.OfferCondition;
import com.example.finalprojectsecondphase.entity.enums.OrderCondition;
import com.example.finalprojectsecondphase.exception.DuplicateInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.exception.WrongInputPriceException;
import com.example.finalprojectsecondphase.service.*;
import com.example.finalprojectsecondphase.util.validation.CreatAndValidationDate;
import com.example.finalprojectsecondphase.util.validation.TakeAndCheckImage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class OfferClassTest {

    @Autowired
    OfferService offerService;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    RequestService requestService;
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    ExpertService expertService;
    @Autowired
    CreatAndValidationDate creatAndValidationDate;
    @Autowired
    TakeAndCheckImage takeAndCheckImage;

    private static Offer firstOffer;
    private static Offer secondOffer;
    private static Offer wrongPriceOffer;
    private static Offer dulicateOffer;

    private static com.example.finalprojectsecondphase.entity.Order secondOrder;

    private static Expert correctSecondExpert;

    @BeforeAll
    public static void makeOffer() {

        firstOffer = Offer.builder()
                .offerPrice(160000)
                .takeLong(1)
                .build();

        secondOffer = Offer.builder()
                .offerPrice(170000)
                .takeLong(3)
                .build();

        wrongPriceOffer = Offer.builder()
                .offerPrice(90000)
                .takeLong(3)
                .build();

        dulicateOffer = Offer.builder()
                .offerPrice(160000)
                .takeLong(2)
                .build();

        secondOrder = com.example.finalprojectsecondphase.entity.Order.builder()
                .description("need a very good person")
                .bestTime(BestTime.MORNING)
                .build();

        correctSecondExpert = Expert.builder()
                .firstname("ali")
                .lastname("mir")
                .username("ali123")
                .password("aaAA12!@")
                .email("ali@gmail.com")
                .phoneNumber("+989128574434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1393835763")
                .nationalCode("4115587695")
                .build();
    }

    @DisplayName("test for save an  offer")
    @org.junit.jupiter.api.Order(1)
    @Test
    public void saveOffer() {
        Expert expert = expertService.findById(1);
        com.example.finalprojectsecondphase.entity.Order order = orderService.findById(1);
        firstOffer.setExpert(expert);
        firstOffer.setOrder(order);
        offerService.saveOffer(firstOffer);
        Assertions.assertEquals(offerService.findById(1).getExpert().getUsername(), firstOffer.getExpert().getUsername());
    }
}
