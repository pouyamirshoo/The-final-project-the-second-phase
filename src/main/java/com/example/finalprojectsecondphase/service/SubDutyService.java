package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.entity.Duty;
import com.example.finalprojectsecondphase.entity.SubDuty;
import com.example.finalprojectsecondphase.exception.DuplicateInformationException;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.repository.SubDutyRepository;
import jakarta.transaction.Transactional;
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
public class SubDutyService {
    private final SubDutyRepository subDutyRepository;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public void validate(SubDuty subDuty) {
        Set<ConstraintViolation<SubDuty>> violations = validator.validate(subDuty);
        if (violations.isEmpty()) {
            subDutyRepository.save(subDuty);
            log.info("subDuty saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<SubDuty> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new InvalidInputInformationException("some of inputs are not valid");
        }
    }

    @Transactional
    public void saveSubDuty(SubDuty subDuty) {
        if (subDutyRepository.findBySubDutyName(subDuty.getSubDutyName()).isPresent()) {
            log.error("duplicate subDuty name can not insert");
            throw new DuplicateInformationException("duplicate subDuty name can not insert");
        } else {
            validate(subDuty);
        }
    }

    public SubDuty findById(int id) {
        return subDutyRepository.findById(id).orElseThrow(() ->
                new NotFoundException("subDuty with id " + id + " not founded"));
    }

    public void updateSubDutyPrice(int price, int id) {
        SubDuty subDuty = findById(id);
        subDuty.setPrice(price);
        validate(subDuty);
    }

    public void updateSubDutyDescription(String description, int id) {
        SubDuty subDuty = findById(id);
        subDuty.setDescription(description);
        validate(subDuty);
    }

    public List<SubDuty> findByDuty(Duty duty) {
        List<SubDuty> subDuties = subDutyRepository.findByDuty(duty);
        if (subDuties.size() > 0)
            return subDuties;
        else
            throw new NullPointerException();
    }

    public void removeSubDuty(SubDuty subDuty) {
        subDutyRepository.delete(subDuty);
    }
}
