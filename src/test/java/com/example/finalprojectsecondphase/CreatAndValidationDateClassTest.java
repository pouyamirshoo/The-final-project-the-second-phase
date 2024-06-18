package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.WrongDateInsertException;
import com.example.finalprojectsecondphase.util.validation.CreatAndValidationDate;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CreatAndValidationDateClassTest {

    @Autowired
    CreatAndValidationDate creatAndValidationDate;

    @DisplayName("test for check current time")
    @Order(1)
    @Test()
    public void currentTime() {
        DateTime dateTime = creatAndValidationDate.currentTime();
        Assertions.assertEquals(dateTime.getDayOfWeek(), DateTime.now().getDayOfWeek());
    }

    @DisplayName("test for check correct input date format")
    @Order(2)
    @Test()
    public void checkCorrectInputDateFormat() {
        String inputDate = "2024-05-08";
        boolean checkFormat = creatAndValidationDate.isValidStringInputDate(inputDate);
        Assertions.assertTrue(checkFormat);
    }

    @DisplayName("test for check incorrect input date format")
    @Order(3)
    @Test()
    public void checkWrongInputDateFormat() {
        String inputDate = "216546veejay";
        boolean checkFormat = creatAndValidationDate.isValidStringInputDate(inputDate);
        Assertions.assertFalse(checkFormat);
    }

    @DisplayName("test for check correct time return")
    @Order(4)
    @Test()
    public void insertDate() {
        String inputDate = "2024-05-08";
        DateTime returnDate = creatAndValidationDate.insertDate(inputDate);
        Assertions.assertEquals(returnDate.toString(DateTimeFormat.forPattern("yyyy-MM-dd")), inputDate);
    }

    @DisplayName("test for check wrong time return")
    @Order(5)
    @Test()
    public void notInsertDate() {
        String inputDate = "216546veejay";
        Throwable exception = Assertions.assertThrows(InvalidInputInformationException.class,
                () -> creatAndValidationDate.insertDate(inputDate));
        Assertions.assertEquals("invalid date format entered", exception.getMessage());
    }

    @DisplayName("test for check return a not past date")
    @Order(6)
    @Test()
    public void checkNowOrFutureDate() {
        String inputNotPastDate = "2024-10-13";
        DateTime notPastDate = creatAndValidationDate.insertDate(inputNotPastDate);
        boolean checkNotPastDate = creatAndValidationDate.checkNotPastTime(notPastDate);
        Assertions.assertTrue(checkNotPastDate);
    }

    @DisplayName("test for check not return a past date")
    @Order(7)
    @Test()
    public void checkNotPastDate() {
        String inputNotPastDate = "2024-05-13";
        DateTime notPastDate = creatAndValidationDate.insertDate(inputNotPastDate);
        Throwable exception = Assertions.assertThrows(WrongDateInsertException.class,
                () -> creatAndValidationDate.checkNotPastTime(notPastDate));
        Assertions.assertEquals("date can not be before today", exception.getMessage());
    }
}
