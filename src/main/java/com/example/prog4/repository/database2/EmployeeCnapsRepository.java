package com.example.prog4.repository.database2;

import com.example.prog4.repository.database2.entity.EmployeeCnaps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface EmployeeCnapsRepository extends JpaRepository<EmployeeCnaps, String> {
    Optional<EmployeeCnaps> findByPersonalEmail(String personalEmail);

    Optional<EmployeeCnaps> findByEndToEndId(String id);
}
