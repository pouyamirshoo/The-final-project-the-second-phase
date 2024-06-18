package com.example.finalprojectsecondphase.entity;

import com.example.finalprojectsecondphase.entity.enums.BestTime;
import com.example.finalprojectsecondphase.entity.enums.OrderCondition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SoftDelete
@Table(name = "Orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    Customer customer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    SubDuty subDuty;
    @Column(name = "order_condition")
    @Enumerated(EnumType.STRING)
    OrderCondition orderCondition;
    @Column(name = "date_create_order")
    @NotNull
    DateTime dateCreatOrder;
    @NotNull(message = "you must enter price")
    int orderPrice;
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "enter description")
    String description;
    @Column(name = "need_expert")
    @NotNull
    DateTime needExpert;
    @Column(name = "best_time")
    @Enumerated(EnumType.STRING)
    @NotNull
    BestTime bestTime;
    @Column(name = "update_Order_Date")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date updateOrderDate;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    List<Offer> offers;

    @PrePersist
    public void defaultValues() {
        if (orderCondition == null) {
            orderCondition = OrderCondition.RECEIVING_OFFERS;
        }
    }
}
