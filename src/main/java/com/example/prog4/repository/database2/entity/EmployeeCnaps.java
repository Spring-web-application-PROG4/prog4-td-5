package com.example.prog4.repository.database2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "\"employee_cnaps\"")
public class EmployeeCnaps {
    @Id
    private String id;
    private String cin;
    private String cnaps;
    private String image;
    private String address;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "professional_email")
    private String professionalEmail;

    @Column(name = "registration_number")
    private String registrationNumber;

    private LocalDate birthdate;

    @Column(name = "entrance_date")
    private LocalDate entranceDate;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "children_number")
    private Integer childrenNumber;
    @Column(name = "end_to_end_id")
    private String endToEndId;

}