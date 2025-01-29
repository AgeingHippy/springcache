package com.ageinghippy.springcache.controller;

import com.ageinghippy.springcache.model.Employee;
import com.ageinghippy.springcache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    // add a new employee
    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {

        employee = employeeService.saveEmployee(employee);
        return ResponseEntity
                .created(URI.create("/employees/" + employee.getId()))
                .body(employee);
    }

    // get a list of all employees
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // find an employee via ID
    @GetMapping("employees/{employeeId}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable(value = "employeeId") Integer employeeId) {

        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    // update an existing employee
    @PutMapping("employees/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable(value = "employeeId") Integer employeeId,
            @RequestBody Employee employeeDetails) {

        Employee employee = employeeService.getEmployee(employeeId);
        employee.setName(employeeDetails.getName());
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    // delete an employee via ID
    @DeleteMapping("employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Integer employeeId) {

        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }
}

