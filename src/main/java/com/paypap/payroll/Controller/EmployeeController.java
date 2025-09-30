package com.paypap.payroll.Controller;

import com.paypap.payroll.entity.Employee;
import com.paypap.payroll.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepo;

    public EmployeeController(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeRepo.findAll());
        return "employee/list"; // renders employee/list.html
    }

    @GetMapping("/new")
    public String newEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/form"; // renders employee/form.html
    }

    @PostMapping
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeRepo.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable String id, Model model) {
        model.addAttribute("employee", employeeRepo.findById(id).orElseThrow());
        return "employee/form";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable String id, @ModelAttribute Employee employee) {
        //employee.setId(id);
        employeeRepo.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable String id) {
        employeeRepo.deleteById(id);
        return "redirect:/employees";
    }
}
