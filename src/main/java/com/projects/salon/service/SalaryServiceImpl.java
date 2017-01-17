package com.projects.salon.service;

import com.projects.salon.entity.SalaryInfo;
import com.projects.salon.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {
    private final SalaryRepository salaryRepository;

    @Autowired
    public SalaryServiceImpl(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @Override
    public List<SalaryInfo> getAllInfos() {
        List<SalaryInfo> employeesInfo = salaryRepository.getEmployeesInfo();
        employeesInfo.add(salaryRepository.getAdminInfo());
        return employeesInfo;
    }
}
