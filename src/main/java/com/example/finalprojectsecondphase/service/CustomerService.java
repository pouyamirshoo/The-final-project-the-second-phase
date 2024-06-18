package com.example.finalprojectsecondphase.service;

import com.example.finalprojectsecondphase.entity.Customer;
import com.example.finalprojectsecondphase.exception.DuplicateInformationException;
import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.NotFoundException;
import com.example.finalprojectsecondphase.repository.CustomerRepository;
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
public class CustomerService {
    private final CustomerRepository customerRepository;
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public void validate(Customer customer) {
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (violations.isEmpty()) {
            customerRepository.save(customer);
            log.info("customer saved");
        } else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Customer> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new InvalidInputInformationException("some of inputs are not valid");
        }
    }
    public void saveCustomer(Customer customer) {
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            log.error("duplicate username can not insert");
            throw new DuplicateInformationException("duplicate username can not insert");
        } else if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            log.error("duplicate email can not insert");
            throw new DuplicateInformationException("duplicate email can not insert");
        } else if (customerRepository.findByPhoneNumber(customer.getPhoneNumber()).isPresent()) {
            log.error("duplicate phoneNumber can not insert");
            throw new DuplicateInformationException("duplicate phoneNumber can not insert");
        } else if (customerRepository.findByPostalCode(customer.getPostalCode()).isPresent()) {
            log.error("duplicate postalCode can not insert");
            throw new DuplicateInformationException("duplicate postalCode can not insert");
        } else {
            validate(customer);
        }
    }

    public Customer findById(int id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new NotFoundException("customer with id " + id + " not founded"));
    }

    public Customer singInCustomer(String username, String password) {
        return customerRepository.findByUsernameAndPassword(username, password).orElseThrow(() ->
                new NotFoundException("wrong username or password"));
    }

    public void UpdatePassword(String password, int id) {
        Customer customer = findById(id);
        customer.setPassword(password);
        validate(customer);
    }
}
