package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.Duty;
import com.example.finalprojectsecondphase.exception.DuplicateInformationException;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.service.DutyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class DutyClassTest {

    @Autowired
    DutyService dutyService;

    private static Duty correctDuty;
    private static Duty correctSecondDuty;
    private static Duty invalidInfoTypeDuty;

    @BeforeAll
    public static void makeDuty(){

        correctDuty = Duty.builder()
                .dutyName("NEZAFAT")
                .build();

        correctSecondDuty = Duty.builder()
                .dutyName("TASISAT")
                .build();

        invalidInfoTypeDuty = Duty.builder()
                .dutyName("@#$hg")
                .build();
    }
}
