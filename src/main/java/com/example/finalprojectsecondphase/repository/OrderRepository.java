package com.example.finalprojectsecondphase.repository;

import com.example.finalprojectsecondphase.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
