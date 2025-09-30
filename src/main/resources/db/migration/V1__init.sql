-- Employee master data
CREATE TABLE employee (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    emp_number VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(200),
    phone VARCHAR(30),
    bank_name VARCHAR(100),
    bank_account VARCHAR(50),
    tax_id VARCHAR(50),
    date_hired DATE NOT NULL,
    date_terminated DATE NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contract (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    employee_id VARCHAR(36) NOT NULL,
    pay_type ENUM('MONTHLY','WEEKLY','DAILY') NOT NULL,
    base_salary DECIMAL(15,2) NOT NULL,
    currency CHAR(3) NOT NULL DEFAULT 'KES',
    effective_from DATE NOT NULL,
    effective_to DATE NULL,
    CONSTRAINT fk_contract_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);

-- Payroll periods (per month or week)
CREATE TABLE payroll_period (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    status ENUM('DRAFT','POSTED','CANCELLED') NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Payslip header
CREATE TABLE payslip (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    payroll_period_id CHAR(36) NOT NULL,
    employee_id CHAR(36) NOT NULL,
    gross_amount DECIMAL(15,2) NOT NULL,
    net_amount DECIMAL(15,2) NOT NULL,
    status ENUM('GENERATED','APPROVED','PAID') NOT NULL DEFAULT 'GENERATED',
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payslip_period FOREIGN KEY (payroll_period_id) REFERENCES payroll_period(id),
    CONSTRAINT fk_payslip_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);

-- Payslip line items (earnings/deductions)
CREATE TABLE payslip_line (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    payslip_id CHAR(36) NOT NULL,
    line_type ENUM('EARNING','DEDUCTION') NOT NULL,
    code VARCHAR(50) NOT NULL,
    description VARCHAR(200),
    amount DECIMAL(15,2) NOT NULL,
    taxable BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_payslip_line FOREIGN KEY (payslip_id) REFERENCES payslip(id)
);

-- Master table for earning types
CREATE TABLE earning_type (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    taxable BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Master table for deduction types
CREATE TABLE deduction_type (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    statutory BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tax rules (progressive slabs or flat)
CREATE TABLE tax_rule (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    rule_name VARCHAR(100) NOT NULL,
    min_income DECIMAL(15,2) NOT NULL,
    max_income DECIMAL(15,2) NULL,  -- NULL = no upper limit
    rate DECIMAL(5,2) NOT NULL,     -- percentage
    effective_from DATE NOT NULL,
    effective_to DATE NULL
);

-- Bank transfer files (used to pay employees)
CREATE TABLE bank_transfer_file (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    file_name VARCHAR(200) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    payroll_period_id CHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bank_file_period FOREIGN KEY (payroll_period_id) REFERENCES payroll_period(id)
);

-- Audit logs
CREATE TABLE audit_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    actor VARCHAR(100) NOT NULL,
    action VARCHAR(100) NOT NULL,
    entity_name VARCHAR(100) NOT NULL,
    entity_id CHAR(36) NOT NULL,
    details JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
