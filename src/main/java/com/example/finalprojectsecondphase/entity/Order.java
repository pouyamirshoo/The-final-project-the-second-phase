package com.example.finalprojectsecondphase.entity;

import com.example.finalprojectsecondphase.entity.enums.BestTime;
import com.example.finalprojectsecondphase.entity.enums.OrderCondition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SoftDelete;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SoftDelete
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    Customer customer;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    SubDuty subDuty;
    @Column(name = "order_condition")
    @Enumerated(EnumType.STRING)
    OrderCondition orderCondition;
    @Column(name = "date_create_order")
    @NotNull
    @Temporal(TemporalType.DATE)
    Date dateCreatOrder;
    @NotNull(message = "you must enter price")
    int orderPrice;
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "enter description")
    String description;
    @Column(name = "need_expert")
    @NotNull
    @Temporal(TemporalType.DATE)
    Date needExpert;
    @Column(name = "best_time")
    @Enumerated(EnumType.STRING)
    @NotNull
    BestTime bestTime;
    @ToString.Exclude
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    List<Offer> offers;

    @PrePersist
    public void defaultValues(){
        if (orderCondition == null) {
            orderCondition = OrderCondition.RECEIVING_OFFERS;
        }
    }
}
