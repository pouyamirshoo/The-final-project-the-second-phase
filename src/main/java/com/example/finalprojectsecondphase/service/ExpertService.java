package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.entity.Expert;
import com.example.finalprojectsecondphase.entity.enums.ExpertCondition;
import com.example.finalprojectsecondphase.exception.DuplicateInformationException;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.repository.ExpertRepository;
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
public class ExpertService {
    private final ExpertRepository expertRepository;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public void validate(Expert expert) {
        Set<ConstraintViolation<Expert>> violations = validator.validate(expert);
        if (violations.isEmpty()) {
            expertRepository.save(expert);
            log.info("expert saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Expert> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new InvalidInputInformationException("some of inputs are not valid");
        }
    }

    @Transactional
    public void saveExpert(Expert expert) {
        if (expertRepository.findByUsername(expert.getUsername()).isPresent()) {
            log.error("duplicate username can not insert");
            throw new DuplicateInformationException("duplicate username can not insert");
        } else if (expertRepository.findByEmail(expert.getEmail()).isPresent()) {
            log.error("duplicate email can not insert");
            throw new DuplicateInformationException("duplicate email can not insert");
        } else if (expertRepository.findByPhoneNumber(expert.getPhoneNumber()).isPresent()) {
            log.error("duplicate phoneNumber can not insert");
            throw new DuplicateInformationException("duplicate phoneNumber can not insert");
        } else if (expertRepository.findByPostalCode(expert.getPostalCode()).isPresent()) {
            log.error("duplicate postalCode can not insert");
            throw new DuplicateInformationException("duplicate postalCode can not insert");
        } else if (expertRepository.findByNationalCode(expert.getNationalCode()).isPresent()) {
            log.error("duplicate nationalCode can not insert");
            throw new DuplicateInformationException("duplicate nationalCode can not insert");
        } else
            validate(expert);
    }

    @Transactional
    public Expert signInExpert(String username, String password) {
        return expertRepository.findByUsernameAndPassword(username, password).orElseThrow(() ->
                new NotFoundException("wrong username or password"));
    }

    public Expert findById(int id) {
        return expertRepository.findById(id).orElseThrow(() ->
                new NotFoundException("expert with id " + id + " not founded"));
    }

    public void UpdatePassword(String password, int id) {
        Expert expert = findById(id);
        expert.setPassword(password);
        validate(expert);
    }

    public void updateExpertCondition(ExpertCondition expertCondition, int id) {
        Expert expert = findById(id);
        expert.setExpertCondition(expertCondition);
        validate(expert);
    }

    @Transactional
    public List<Expert> findByExpertCondition(ExpertCondition expertCondition) {
        List<Expert> experts = expertRepository.findByExpertCondition(expertCondition);
        if (experts.size() > 0)
            return experts;
        else
            throw new NullPointerException();
    }

    public boolean accessDenied(Expert expert) {
        ExpertCondition expertCondition = expert.getExpertCondition();
        return expertCondition == ExpertCondition.ACCEPTED;
    }

    public void removeExpert(int id) {
        Expert expert = findById(id);
        expertRepository.delete(expert);
    }
}
