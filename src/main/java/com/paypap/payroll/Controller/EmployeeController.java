package com.paypap.payroll.Controller;

import com.paypap.payroll.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.paypap.payroll.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee/list"; // renders employee/list.html
    }

    @GetMapping("/new")
    public String newEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/form"; // renders employee/form.html
    }

    @PostMapping
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.createEmployee(employee); // use service instead of repo
        return "redirect:/employees";
    }


    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable String id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        return "employee/form";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable String id, @ModelAttribute Employee employee) {
        employeeService.updateEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}
