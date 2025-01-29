package com.ageinghippy.springcache.service;

import com.ageinghippy.springcache.exception.ResourceNotFoundException;
import com.ageinghippy.springcache.model.Employee;
import com.ageinghippy.springcache.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    // can be used to inspect the cache during debug
    @Autowired
    CacheManager cacheManager;

    @Cacheable(value = "employees", key = "#employeeId", sync = true)
    public Employee getEmployee(Integer employeeId) {

        fakeDelay("Fetching employee from the database...");

        return employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found: " + employeeId));
    }

    @Cacheable(value = "allEmployees")
    public List<Employee> getAllEmployees() {
        fakeDelay("Fetching all employees from the database...");
        return employeeRepository.findAll();
    }

    //    @CachePut(value = "employees", key = "#employee.id")
//    @CacheEvict(value = "employees")
    @Caching(evict = @CacheEvict(value = "allEmployees", allEntries = true),
            put = @CachePut(value = "employees", key = "#employee.id"))
    public Employee saveEmployee(Employee employee) {
        System.out.println(
                "Saving employee to the database and updating cache");
        return employeeRepository.save(employee);
    }

    @Caching(evict = {
            @CacheEvict(value = "allEmployees", allEntries = true),
            @CacheEvict(value = "employees", key = "#employee.id")})
    public void deleteEmployee(Integer employeeId) {
        System.out.println(
                "Removing employee from the database and cache, and clearing the AllEmployees cache entry");
        employeeRepository.deleteById(employeeId);
    }

    public void fakeDelay(String message) {
        for (int i = 0; i < 2; i++) {
            System.out.println(message);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

