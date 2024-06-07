package com.curd.demo.Routes.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.BeforeEach;
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
class EmployeeClassControllerTest {

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
	EntityClass entClass;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

	}

	@BeforeEach
	public void Before() {
		entClass = new EntityClass();
		entClass.setEmail_id("aa@gmail.com");
		entClass.setId(11);
		entClass.setMarks(100);
		entClass.setName("anish");
	}

	@Test
	public void testGetAllEmployees() throws Exception {
		when(producerTemplate.requestBody(any(String.class), any(), any(Class.class))).thenReturn("some");

		mockMvc.perform(get("/employees/get")).andExpect(status().isOk()).andExpect(content().string("some"));

	}

	/*
	 * @PostMapping("/add") public String addEmployee(@RequestBody EntityClass
	 * employee) { // Invoke the Camel route to add an employee
	 * producerTemplate.sendBody("direct:addEmployee", employee); return
	 * "Employee added successfully!"; }
	 */

	@Test
	public void addEmployeeTest() throws Exception {
		/*
		 * EntityClass entClass = new EntityClass();
		 * entClass.setEmail_id("aa@gmail.com"); entClass.setId(11);
		 * entClass.setMarks(100); entClass.setName("anish");
		 */

		String empJson = objectMapper.writeValueAsString(entClass);
		mockMvc.perform(post("/employees/add").contentType(MediaType.APPLICATION_JSON).content(empJson))
				.andExpect(status().isOk()).andExpect(content().string("Employee added successfully!"));

	}

//	@PutMapping("/{id}")
//    public String updateEmployee(@PathVariable int id, @RequestBody EntityClass employee) {
//        // Invoke the Camel route to update an employee
//        producerTemplate.sendBodyAndHeader("direct:updateEmployee", employee, "employeeId", id);
//        return "Employee updated successfully!";
//    }

	@Test
	public void updateEmployeeTest() throws Exception {

		var entClass1 = new EntityClass();
		entClass.setEmail_id("aa@gmail.com");

		String EmpJson = objectMapper.writeValueAsString(entClass1);

		mockMvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON).content(EmpJson))
				.andExpect(status().isOk()).andExpect(content().string("Employee updated successfully!"));
	}

//	@DeleteMapping("/{id}")
//    public String deleteEmployee(@PathVariable int id) {
//        // Invoke the Camel route to delete an employee
//        producerTemplate.sendBodyAndHeader("direct:deleteEmployee", null, "employeeId", id);
//        return "Employee deleted successfully!";
//    }

	@Test
	public void deleteEmployeeTest() throws Exception {
		// when(producerTemplate.sendBodyAndHeader(any(String.class),any(),anyString(),1));
		mockMvc.perform(delete("/employees/1")).andExpect(content().string("Employee deleted successfully!"));
	}

}
