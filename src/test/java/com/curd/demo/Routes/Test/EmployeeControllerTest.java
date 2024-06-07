package com.curd.demo.Routes.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.curd.demo.Entity.EntityClass;
import com.curd.demo.Routes.EmployeeController;
import com.curd.demo.repo.RepositoryForCurd;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProducerTemplate producerTemplate;

    @MockBean
    private RepositoryForCurd repo;

    @InjectMocks
    private EmployeeController employeeController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    
    public void testGetAllEmployees() throws Exception {
        when(producerTemplate.requestBody(any(String.class), any(), any(Class.class)))
                .thenReturn("[]");

        mockMvc.perform(get("/employees/get"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void testGetAllEmployees1() throws Exception {
        EntityClass employee = new EntityClass();
        List<EntityClass> employees = Collections.singletonList(employee);
        when(repo.findAll()).thenReturn(employees);

        mockMvc.perform(get("/employees/get1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    @Disabled
    public void testAddEmployee() throws Exception {
        EntityClass employee = new EntityClass();
        String employeeJson = objectMapper.writeValueAsString(employee);

        mockMvc.perform(post("/employees/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Employee added successfully!"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        EntityClass employee = new EntityClass();
        String employeeJson = objectMapper.writeValueAsString(employee);

        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee updated successfully!"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully!"));
    }
}
