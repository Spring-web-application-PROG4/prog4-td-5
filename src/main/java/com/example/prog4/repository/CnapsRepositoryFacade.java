package com.example.prog4.repository;

import com.example.prog4.repository.database2.EmployeeCnapsRepository;
import com.example.prog4.repository.database2.entity.EmployeeCnaps;
import lombok.AllArgsConstructor;

import java.util.Optional;

@org.springframework.stereotype.Repository
@AllArgsConstructor
public class CnapsRepositoryFacade  implements Repository<EmployeeCnaps>{

    private EmployeeCnapsRepository employeeCnapsRepository;

    @Override
    public Optional<EmployeeCnaps> findById(String id) {
        return employeeCnapsRepository.findByEndToEndId(id);
    }


    @Override
    public EmployeeCnaps save(EmployeeCnaps toSave){
        return employeeCnapsRepository.saveAndFlush(toSave);
    }

    @Override
    public boolean existsById(String id){
        return employeeCnapsRepository.existsById(id);
    }

}
