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
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "duty_name")
    @Pattern(regexp = "[a-zA-Z]+")
    @NotNull(message = "duty name can not be null")
    String dutyName;
    @OneToMany(mappedBy = "duty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)

    List<SubDuty> subDuties;
}
