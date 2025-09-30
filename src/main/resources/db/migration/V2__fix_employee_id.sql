-- 1. Drop the foreign key constraint
ALTER TABLE contract DROP FOREIGN KEY fk_contract_employee;

-- 2. Modify employee.id and contract.employee_id
ALTER TABLE employee MODIFY id VARCHAR(36);
ALTER TABLE contract MODIFY employee_id VARCHAR(36);

-- 3. Re-add the foreign key constraint
ALTER TABLE contract 
    ADD CONSTRAINT fk_contract_employee
    FOREIGN KEY (employee_id) REFERENCES employee(id);
