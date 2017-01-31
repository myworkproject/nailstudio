package com.projects.salon.repository;

import com.projects.salon.entity.SalaryInfo;

import java.util.List;

public interface SalaryRepository {
    List<SalaryInfo> getEmployeesInfo(int month);

    SalaryInfo getAdminInfo(int month);
}
