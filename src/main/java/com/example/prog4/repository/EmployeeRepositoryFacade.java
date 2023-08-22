package com.example.prog4.repository;

import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.entity.Employee;
import lombok.AllArgsConstructor;
@org.springframework.stereotype.Repository
@AllArgsConstructor
public class EmployeeRepositoryFacade implements Repository<Employee>{

    private EmployeeRepository employeeRepository;
    @Override
    public Employee findById(String id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    @Override
    public Employee save(Employee toSave){
        return  employeeRepository.saveAndFlush(toSave);
    }



}
