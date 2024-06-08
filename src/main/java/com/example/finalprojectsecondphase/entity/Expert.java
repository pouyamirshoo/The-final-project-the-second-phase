package com.example.finalprojectsecondphase.entity;

import com.example.finalprojectsecondphase.entity.enums.ExpertCondition;
import com.example.finalprojectsecondphase.entity.enums.UpdatedField;
import com.example.finalprojectsecondphase.util.validation.ValidationCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@SoftDelete
@Entity
public class Expert  extends Person{
    @Column(unique = true,name = "national_code")
    @NotNull(message = "Expert national code can not be null")
    @ValidationCode
    String nationalCode;
    @ToString.Exclude
    @Column(name = "expert_image")
    @Lob
    @Size(max = 300000,message = "it is too big file")
    @NotEmpty(message = "must upload an image")
    byte[] expertImage;
    @Column(name = "expert_condition")
    @Enumerated(EnumType.STRING)
    ExpertCondition expertCondition;
    @Column(columnDefinition = "TEXT",name = "reject_reason")
    String rejectReason;
    @Max(5)
    Integer rate;
    @Min(0)
    Integer balance;
    @Enumerated(EnumType.STRING)
    UpdatedField updatedField;
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    List<SubDuty> subDuties;
    @ToString.Exclude
    @OneToMany(mappedBy = "expert",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    List<Offer> offers;

    @PrePersist
    public void defaultValues(){
        if (expertCondition == null){
            expertCondition = ExpertCondition.AWAITING;
        }
        if (balance == null){
            balance = 0;
        }
        if (rate == null) {
            rate = 0;
        }
    }
}
