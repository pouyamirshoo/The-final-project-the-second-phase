package com.example.finalprojectsecondphase.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@SoftDelete
@MappedSuperclass
public class Person {
    @Pattern(regexp = "[a-zA-Z]+")
    @NotNull(message = "firstname can not be null")
    String firstname;
    @Pattern(regexp = "[a-zA-Z]+")
    @NotNull(message = "lastname can not be null")
    String lastname;
    @Column(unique = true)
    @NotNull(message = "username can not be null")
    String username;
    @ToString.Exclude
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#!%&*])[A-Za-z0-9@#!%&*]{8}$"
            , message = "password has to be 8 size and must contain at least 1 lower and upper case and 1 digit and 1 char ")
    @NotNull(message = "password can not be null")
    String password;
    @Column(unique = true)
    @Email
    @NotNull(message = "email can not be null")
    String email;
    @NotNull(message = "phoneNumber can not be null")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
    @Column(unique = true)
    String phoneNumber;
    @Pattern(regexp = "[a-zA-Z]+")
    @NotNull(message = "city can not be null")
    String city;
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "address can not be null")
    String address;
    @Column(unique = true)
    @Pattern(regexp = "^(?!(\\d)\1{3})[13-9]{4}[1346-9][013-9]{5}$")
    @NotNull(message = "postal code can not be null")
    String postalCode;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date registerTime;
}
