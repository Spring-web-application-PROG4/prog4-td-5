package com.example.prog4.service;

import com.example.prog4.controller.mapper.CnapsMapper;
import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.EmployeeRepository;
import com.example.prog4.repository.dao.EmployeeManagerDao;
import com.example.prog4.repository.database2.entity.EmployeeCnaps;
import com.example.prog4.repository.entity.Employee;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;
    private final EmployeeManagerDao employeeManagerDao;
    private final CnapsMapper cnapsMapper;
    private final com.example.prog4.repository.EmployeeRepositoryFacade employeeRepositoryFacade;
    private final com.example.prog4.repository.CnapsRepositoryFacade cnapsRepositoryFacade;

    public Employee getOne(String id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id=" + id));

        // Retrieve the CNAPS number from the "employee_cnaps" table
        EmployeeCnaps cnapsData = cnapsRepositoryFacade.findById(employee.getId()).orElse(null);

        // Update the employee's CNAPS number if it is different
        if (cnapsData != null && !employee.getCnaps().equals(cnapsData.getCnaps())) {
            employee.setCnaps(cnapsData.getCnaps());
        }

        return employee;
    }

    @Transactional
    public List<Employee> getAll(EmployeeFilter filter) {
        Sort sort = Sort.by(filter.getOrderDirection(), filter.getOrderBy().toString());
        Pageable pageable = PageRequest.of(filter.getIntPage() - 1, filter.getIntPerPage(), sort);
        return employeeManagerDao.findByCriteria(
                filter.getLastName(),
                filter.getFirstName(),
                filter.getCountryCode(),
                filter.getSex(),
                filter.getPosition(),
                filter.getEntrance(),
                filter.getDeparture(),
                pageable
        );
    }

    public void saveOne(Employee employee) {
        String cnapsNumber = "(this employee does not yet have a CNAPS number)";

        if (employee.getId() != null && !employee.getId().isEmpty()) {
            // The end_to_end_id is the employee name and his CNAPS number
            String endToEndId = employee.getId();
            EmployeeCnaps cnapsData = cnapsRepositoryFacade.findById(endToEndId).orElse(null);

            if (cnapsData != null) {
                cnapsNumber = cnapsData.getCnaps();
            }
        }

        employee.setCnaps(cnapsNumber);
        employeeRepositoryFacade.save(employee);
    }
}

