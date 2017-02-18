package com.projects.salon.service;

import com.projects.salon.entity.SalaryInfo;
import com.projects.salon.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    @Secured("ADMIN")
    public List<SalaryInfo> getAllInfos(int month) {
        List<SalaryInfo> employeesInfo = salaryRepository.getEmployeesInfo(month);
        employeesInfo.add(salaryRepository.getAdminInfo(month));
        return employeesInfo;
    }
}
