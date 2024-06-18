package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPostalCode(String postalCode);
    Optional<Customer> findByUsernameAndPassword(String username,String password);
}
