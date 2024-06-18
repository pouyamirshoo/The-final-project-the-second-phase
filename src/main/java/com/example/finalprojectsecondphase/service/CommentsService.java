package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.entity.Comments;
import com.example.finalprojectsecondphase.entity.SubDuty;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.repository.CommentsRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentsService {
    private final CommentsRepository commentsRepository;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public void validate(Comments comment) {
        Set<ConstraintViolation<Comments>> violations = validator.validate(comment);
        if (violations.isEmpty()) {
            commentsRepository.save(comment);
            log.info("comment saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Comments> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new InvalidInputInformationException("some of inputs are not valid");
        }
    }
    public void saveComment(Comments comments){
        validate(comments);
    }


}
