package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
