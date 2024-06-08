package com.example.finalprojectsecondphase.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SoftDelete
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Customer extends Person{
    @Column(name = "customer_balance")
    Integer customerBalance;
    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Order> orders;

    @PrePersist
    public void defaultValues() {
        if (customerBalance == null)
            customerBalance = 0;
    }
}
