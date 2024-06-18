package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.entity.Duty;
import com.example.finalprojectsecondphase.exception.DuplicateInformationException;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.repository.DutyRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DutyService {
    private final DutyRepository dutyRepository;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public void validate(Duty duty) {
        Set<ConstraintViolation<Duty>> violations = validator.validate(duty);
        if (violations.isEmpty()) {
            dutyRepository.save(duty);
            log.info("duty saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Duty> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new InvalidInputInformationException("some of inputs are not valid");
        }
    }

    public void saveDuty(Duty duty) {
        if (dutyRepository.findByDutyName(duty.getDutyName()).isPresent()) {
            log.error("duplicate duty name can not insert");
            throw new DuplicateInformationException("duplicate duty name can not insert");
        } else {
            validate(duty);
        }
    }

    public Duty findById(int id) {
        return dutyRepository.findById(id).orElseThrow(() ->
                new NotFoundException("duty with id " + id + " not founded"));
    }
}
