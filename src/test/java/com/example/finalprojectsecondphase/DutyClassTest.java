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

    @DisplayName("test for save a duty")
    @Order(1)
    @Test
    public void saveDuty() {
        dutyService.saveDuty(correctDuty);
        Assertions.assertEquals(dutyService.findById(1).getDutyName(), correctDuty.getDutyName());
    }

    @DisplayName("test for not save a duplicate name duty")
    @Order(2)
    @Test
    public void dontSaveDuplicateNameDuty() {
        Throwable exception = Assertions.assertThrows(DuplicateInformationException.class,
                () -> dutyService.saveDuty(correctDuty));
        Assertions.assertEquals("duplicate duty name can not insert", exception.getMessage());
    }

    @DisplayName("test for can not find by duty id")
    @Order(3)
    @Test()
    public void canNotFindByDutyId() {
        int id = 2;
        Throwable exception = Assertions.assertThrows(NotFoundException.class,
                () -> dutyService.findById(id));
        Assertions.assertEquals("duty with id " + id + " not founded", exception.getMessage());
    }

    @DisplayName("test for invalid input duty")
    @Order(4)
    @Test()
    public void doNotSaveInvalidDutyInputInformation() {
        Throwable exception = Assertions.assertThrows(InvalidInputInformationException.class,
                () -> dutyService.validate(invalidInfoTypeDuty));
        Assertions.assertEquals("some of inputs are not valid", exception.getMessage());
    }
}
