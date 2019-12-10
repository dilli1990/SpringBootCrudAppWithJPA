package com.example.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springboot.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>  {

		
    @Query(value="SELECT * FROM Employees e where e.first_name = :firstName",nativeQuery=true) 
    List<Employee> findByFirstName(@Param("firstName") String firstName);
    
	@Query(value="SELECT * FROM Employees e where e.last_name = :lastName",nativeQuery=true) 
    List<Employee> findByLastName(@Param("lastName") String lastName);
	
	@Query(value="SELECT * FROM Employees e where e.email_id like %:emailId%", nativeQuery=true) 
	List<Employee> findByEmailId(@Param("emailId") String emailId);
    
	
}
