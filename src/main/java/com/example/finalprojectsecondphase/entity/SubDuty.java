package com.example.finalprojectsecondphase.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SoftDelete
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class SubDuty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(unique = true, name = "sub_duty_name")
    @Pattern(regexp = "[a-zA-Z]+")
    @NotNull(message = "sub duty name can not be null")
    String subDutyName;
    @NotNull(message = "sub duty price can not be null")
    Integer price;
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "description most not null")
    String description;
    @ManyToOne(fetch = FetchType.EAGER)
    Duty duty;
    @OneToMany(mappedBy = "subDuty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Order> orders;
}
