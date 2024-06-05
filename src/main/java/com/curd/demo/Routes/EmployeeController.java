package com.curd.demo.Routes;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curd.demo.Entity.EntityClass;
import com.curd.demo.repo.RepositoryForCurd;
 
@RestController
@RequestMapping("/employees")
public class EmployeeController {
 
    @Autowired
    private ProducerTemplate producerTemplate; // Inject the Camel producer template
 
    @GetMapping("/get")
    public String getAllEmployees() {
        // Invoke the Camel route to fetch all employees
        return producerTemplate.requestBody("direct:fetchEmployees", null, String.class);
    }
    
    @Autowired
    RepositoryForCurd repo;
    @GetMapping("/get1")
    public List<EntityClass> getAllEmployees1() {
        // Invoke the Camel route to fetch all employees
        return repo.findAll();
    }
 
    @PostMapping("/add")
    public String addEmployee(@RequestBody EntityClass employee) {
        // Invoke the Camel route to add an employee
        producerTemplate.sendBody("direct:addEmployee", employee);
        return "Employee added successfully!";
    }
 
    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable int id, @RequestBody EntityClass employee) {
        // Invoke the Camel route to update an employee
        producerTemplate.sendBodyAndHeader("direct:updateEmployee", employee, "employeeId", id);
        return "Employee updated successfully!";
    }
 
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        // Invoke the Camel route to delete an employee
        producerTemplate.sendBodyAndHeader("direct:deleteEmployee", null, "employeeId", id);
        return "Employee deleted successfully!";
    }
}
