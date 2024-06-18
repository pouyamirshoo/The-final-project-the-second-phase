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
}
