package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.exception.WrongImageInputException;
import com.example.finalprojectsecondphase.service.ExpertService;
import com.example.finalprojectsecondphase.util.validation.TakeAndCheckImage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class TakeAndCheckImageClassTest {

    @Autowired
    TakeAndCheckImage takeAndCheckImage;
    @Autowired
    ExpertService expertService;

    String correctPath = "F:\\Maktab\\FinalProjectFirstPhase\\src\\main\\java\\images\\15639454.jpg";
}
