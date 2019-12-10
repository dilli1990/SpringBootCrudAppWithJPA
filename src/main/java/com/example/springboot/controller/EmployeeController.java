package com.example.springboot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.exception.ResourceNotFoundException;
import com.example.springboot.model.Employee;
import com.example.springboot.repository.EmployeeRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
@Api(value="Employee Management System", description="Operations pertaining to employee in Employee Management System")
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@ApiOperation(value = "View a list of available employees", response = List.class)
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Successfully retrieved list"),
	    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		logger.debug("Get all Employee Details ************"+this.getClass());
		return (List<Employee>) employeeRepository.findAll();
		
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable(value="id") Long employeeId) throws ResourceNotFoundException{
		Employee employee = employeeRepository.findById(employeeId)
			.orElseThrow(() -> new ResourceNotFoundException("Employee is not found for this id ::"+employeeId));
				
		return ResponseEntity.ok().body(employee);
	}
	
	@ApiOperation(value = "Get an employee by Id")
	@GetMapping("/employees/{searchByKey}/{searchText}")
	public ResponseEntity<List<Employee>> getEmployeeWithKey(@PathVariable(value="searchByKey") String searchByKey,@PathVariable(value="searchText") String searchText) throws ResourceNotFoundException{
		
		//System.out.println("getEmployeeWithkey");
		List<Employee> employees = null;
		System.out.println(searchByKey);
		if(searchByKey.equals("Id")){
			Employee employee = employeeRepository.findById(Long.parseLong(searchText))
					.orElseThrow(() -> new ResourceNotFoundException("Employee is not found for this id ::"+searchText));
			employees = new ArrayList<Employee>();
			employees.add(employee);				
		}else if(searchByKey.equals("EmailId")){
			employees = employeeRepository.findByEmailId(searchText);
		}else if(searchByKey.equals("FirstName")){
			System.out.println("firstname details");
			employees = employeeRepository.findByFirstName(searchText);

		}else if(searchByKey.equals("LastName")){
			employees = employeeRepository.findByLastName(searchText);
		}
		return ResponseEntity.ok().body(employees);
		
	}
	
	@ApiOperation(value = "Add an employee")
	@PostMapping("/employees")
	public Employee createEmployee(@ApiParam(value = "Employee object store in database table", required = true) @Valid @RequestBody Employee employee){
		return employeeRepository.save(employee);
		
	}
	
	 @ApiOperation(value = "Update an employee")
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@ApiParam(value="mployee Id to update employee object", required = true) @PathVariable(value="id") Long employeeId,
			@ApiParam(value = "Update employee object", required = true)@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id:: "+employeeId));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updateEmployee = employeeRepository.save(employee);
		
		return ResponseEntity.ok(updateEmployee);
		
	}
	
	@ApiOperation(value = "Delete an employee")
	@DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@ApiParam(value = "Employee Id from which employee object will delete from database table",required=true) @PathVariable(value = "id") Long employeeId)
         throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
	

}