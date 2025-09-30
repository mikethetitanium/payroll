package com.paypap.payroll.repository;

import com.paypap.payroll.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, String> {
    List<Contract> findByEmployeeId(String employeeId);
}
