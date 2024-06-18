package com.example.finalprojectsecondphase;

import com.example.finalprojectsecondphase.entity.Comments;
import com.example.finalprojectsecondphase.entity.Order;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.service.CommentsService;
import com.example.finalprojectsecondphase.service.OrderService;
import com.example.finalprojectsecondphase.service.SubDutyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CommentsClassTest {

    @Autowired
    CommentsService commentsService;
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    OrderService orderService;

    private static Comments trueComment;
    private static Comments wrongComment;

    @BeforeAll
    public static void makeComment(){
        trueComment = Comments.builder()
                .additionalComments("it was very good")
                .rate(4)
                .build();

        wrongComment = Comments.builder()
                .additionalComments("it was very good")
                .rate(8)
                .build();
    }

}
