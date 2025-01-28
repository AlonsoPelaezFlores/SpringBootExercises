package org.example.springboot_postgresql.repository;

import org.example.springboot_postgresql.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EmployeeRepo extends CrudRepository<Employee, Long> {

}
