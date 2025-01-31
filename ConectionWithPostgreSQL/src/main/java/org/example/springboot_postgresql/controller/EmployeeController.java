package org.example.springboot_postgresql.controller;

import org.example.springboot_postgresql.model.Employee;
import org.example.springboot_postgresql.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeController {
    public final EmployeeRepo employeeRepo;
    @Autowired
    public EmployeeController(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    /*
     * GET Devuelve todos los empleados
     * */
    @GetMapping
    public Iterable<Employee> findAll(){
        return employeeRepo.findAll();
    }

    /*
     * GET Devuelve empleado por id
     * */
    @GetMapping(value = "/{id}")
    public Optional<Employee> findById(@PathVariable Long id){
        return employeeRepo.findById(id);
    }

    /*
    * POST Crea empleado
    * */
    @PostMapping(value = {"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepo.save(employee);
    }
    /*
     * DELETE elimina empleado por id
     * */
    @DeleteMapping(value = "/{id}")
    public void deleteEmployee(@PathVariable Long id){
        if(employeeRepo.existsById(id)){
            employeeRepo.deleteById(id);
        }
    }


    /*
     * PUT modifica empleado por su id
     * */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee,
                                                   @PathVariable Long id){
        try {

            Employee emp = employeeRepo.findById(id).orElse(null);
            if (emp == null) {
                return ResponseEntity.notFound().build();
            }
            emp.setName(employee.getName());
            employeeRepo.save(emp);
            return ResponseEntity.ok(emp);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
