package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.enums.ExpertCondition;
import com.example.finalprojectsecondphase.exception.DuplicateInformationException;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.service.ExpertService;
import com.example.finalprojectsecondphase.service.SubDutyService;
import com.example.finalprojectsecondphase.util.validation.TakeAndCheckImage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ExpertClassTest {

    @Autowired
    ExpertService expertService;
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    TakeAndCheckImage takeAndCheckImage;

    String correctPath = "F:\\Maktab\\FinalProjectFirstPhase\\src\\main\\java\\images\\15639454.jpg";

    private static Expert correctExpert;
    private static Expert deleteExpert;
    private static Expert duplicateUsernameExpert;
    private static Expert duplicateEmailExpert;
    private static Expert duplicatePhoneNumberExpert;
    private static Expert duplicatePostalCodeExpert;
    private static Expert duplicateNationalCodeExpert;
    private static Expert invalidInfoTypeExpert;

    @BeforeAll
    public static void makeExpert() {

        correctExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya123")
                .password("aaAA12!@")
                .email("pouya@gmail.com")
                .phoneNumber("+989124184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1395613373")
                .nationalCode("0440186412")
                .build();

        deleteExpert = Expert.builder()
                .firstname("mohammd")
                .lastname("mir")
                .username("moh123")
                .password("aaAA12!@")
                .email("moh@gmail.com")
                .phoneNumber("+989128544434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1665743471")
                .nationalCode("5126973215")
                .build();

        duplicateUsernameExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya123")
                .password("aaAA12!@")
                .email("pouyaaa@gmail.com")
                .phoneNumber("+989125184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1417635471")
                .nationalCode("2509203669")
                .build();

        duplicateEmailExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya1234")
                .password("aaAA12!@")
                .email("pouya@gmail.com")
                .phoneNumber("+989127184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("4654190118")
                .nationalCode("7829168131")
                .build();

        duplicatePhoneNumberExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya1235")
                .password("aaAA12!@")
                .email("pouyaaa@gmail.com")
                .phoneNumber("+989124184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1665743471")
                .nationalCode("8583552363")
                .build();

        duplicatePostalCodeExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya1236")
                .password("aaAA12!@")
                .email("pouysaa@gmail.com")
                .phoneNumber("+989127184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("1395613373")
                .nationalCode("1964576288")
                .build();

        duplicateNationalCodeExpert = Expert.builder()
                .firstname("pouya")
                .lastname("mir")
                .username("pouya1239")
                .password("aaAA12!@")
                .email("pouyyyya@gmail.com")
                .phoneNumber("+989124294434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("3336131927")
                .nationalCode("0440186412")
                .build();

        invalidInfoTypeExpert = Expert.builder()
                .firstname("pouya@#")
                .lastname("mir")
                .username("pouya1236")
                .password("aaA1!@")
                .email("pouysaadfagmail.com")
                .phoneNumber("+989127184434")
                .city("tehran")
                .address("ekbatan-A!1")
                .postalCode("135613373")
                .nationalCode("15464818971")
                .build();
    }
}
