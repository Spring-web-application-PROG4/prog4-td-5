package com.example.prog4.repository;

import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.entity.Employee;
import lombok.AllArgsConstructor;

import java.util.Optional;

@org.springframework.stereotype.Repository

@AllArgsConstructor
public class EmployeeRepositoryFacade implements Repository<Employee> {

    private final EmployeeRepository employeeRepository;

    @Override
    public Optional<Employee> findById(String id) {
        return Optional.ofNullable(employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id=" + id)));
    }

    @Override
    public Employee save(Employee toSave) {
        return employeeRepository.saveAndFlush(toSave);
    }

    @Override
    public boolean existsById(String id) {
        return employeeRepository.existsById(id);
    }

    // Add custom methods if needed for your application
}

