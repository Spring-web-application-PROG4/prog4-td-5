package com.example.prog4.repository;

import com.example.prog4.repository.database2.EmployeeCnapsRepository;
import com.example.prog4.repository.database2.entity.EmployeeCnaps;
import lombok.AllArgsConstructor;

@org.springframework.stereotype.Repository
@AllArgsConstructor
public class CnapsRepositoryFacade  implements Repository<EmployeeCnaps>{

    private EmployeeCnapsRepository employeeCnapsRepository;

    @Override
    public EmployeeCnaps findById(String id) {
        return employeeCnapsRepository.findById(id).get();
    }

    @Override
    public EmployeeCnaps save(EmployeeCnaps toSave){
        return employeeCnapsRepository.save(toSave);
    }


}
