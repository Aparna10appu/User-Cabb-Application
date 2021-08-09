package com.cabapplication.user.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.cabapplication.user.entity.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
	
	Employee findByEmployeeMail(String emailId);

	@Query("{isAdmin : 1}")
	List<Employee> findAllAdmins();
	
}
