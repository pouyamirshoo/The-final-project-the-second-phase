package com.example.finalprojectsecondphase.entity;

import com.example.finalprojectsecondphase.entity.enums.OfferCondition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@SoftDelete
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    Expert expert;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    Order order;
    @Column(name = "offer_condition")
    @Enumerated(EnumType.STRING)
    OfferCondition offerCondition;
    @Column(name = "offer_price")
    @NotNull(message = "offer price can not be null")
    Integer offerPrice;
    @Column(name = "take_long")
    @NotNull(message = "takeLong can not be null")
    Integer takeLong;
    @Column(name = "creat_Offer_Date", updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date creatOfferDate;
    @Column(name = "update_Offer_Date")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date updateOfferDate;

    @PrePersist
    public void defaultValues() {
        if (offerCondition == null) {
            offerCondition = OfferCondition.WAITING;
        }
    }
}
