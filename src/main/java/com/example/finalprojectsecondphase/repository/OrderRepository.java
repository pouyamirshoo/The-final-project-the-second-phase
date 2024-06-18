package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Customer;
import com.example.finalprojectsecondphase.entity.Order;
import com.example.finalprojectsecondphase.entity.SubDuty;
import com.example.finalprojectsecondphase.entity.enums.OrderCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByCustomer(Customer customer);
    List<Order> findByOrderCondition(OrderCondition orderCondition);
    List<Order> findBySubDuty(SubDuty subDuty);
}
