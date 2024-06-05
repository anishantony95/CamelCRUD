package com.curd.demo.Routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class EmployeeRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:fetchEmployees")
            //.to("sql:SELECT * FROM camel.entity?dataSource=mydataSource")
            .to("sql:SELECT * FROM camel.entity")
            .log("Fetched employees: ${body}");
        
        from("direct:addEmployee")
        .log("Adding Employee:${body}")
        .to("sql:INSERT INTO camel.entity (id,email_id,marks,name) VALUES("
        + ":#${body.id},:#${body.email_id},:#${body.marks},:#${body.name})");
        
        
        from("direct:updateEmployee")
        .log("Updating Employee with ID ${header.employeeId}: ${body.email_id}")
        //.setHeader("employeeId",simple("${body.id}"))
        .to("sql:UPDATE camel.entity SET email_id=:#${body.email_id} WHERE id=:#${header.employeeId}");
    
        
        
        
        from("direct:deleteEmployee")
        .log("Deleting Employee with ID ${header.employeeId}: ${body.employeeId}")
        //.setHeader("employeeId",simple("${body.id}"))
        .to("sql:DELETE FROM camel.entity WHERE id=:#${header.employeeId}");
        
        
      
    
    
    
    }
}




