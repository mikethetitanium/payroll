package com.paypap.payroll.Service;

import com.paypap.payroll.repository.ContractRepository;
import com.paypap.payroll.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paypap.payroll.entity.Employee;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ContractRepository contractRepository;

    public void deleteEmployee(String employeeId) {
        // First, delete all contracts associated with the employee
        contractRepository.deleteAll(contractRepository.findByEmployeeId(employeeId));
        // Then, delete the employee
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

}
