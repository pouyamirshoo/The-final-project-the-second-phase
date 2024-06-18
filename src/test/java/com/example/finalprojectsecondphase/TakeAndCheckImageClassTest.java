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

    @DisplayName("test for take a correct image")
    @Order(1)
    @Test()
    public void correctImage() {
        File inputImage = new File(correctPath);
        byte[] expect;
        try {
            expect = Files.readAllBytes(inputImage.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] result = takeAndCheckImage.expertImage(correctPath);
        Assertions.assertArrayEquals(expect, result);
    }

    @DisplayName("test for not take a wrong image")
    @Order(2)
    @Test()
    public void wrongImage() {
        String path = "F:\\Maktab\\FinalProjectFirstPhase\\src\\main\\java\\images\\main-qimg-2409a441a71fdc85cf987c349d364cb4.webp";
        Throwable exception = Assertions.assertThrows(WrongImageInputException.class,
                () -> takeAndCheckImage.expertImage(path));
        Assertions.assertEquals("this file is not jpg", exception.getMessage());
    }



    // TODO: 6/17/2024 RUN AFTER AN EXPERT SAVED



    @DisplayName("test for save an expert image in HDD")
    @Order(3)
    @Test
    public void saveExpertImageInHDD(){
        Expert expert = expertService.findById(1);
        boolean flag = takeAndCheckImage.saveExpertImageToHDD(expert.getExpertImage(),
                expert.getFirstname(),expert.getLastname());
        Assertions.assertTrue(flag);
    }
}
