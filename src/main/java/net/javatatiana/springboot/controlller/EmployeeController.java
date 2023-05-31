package net.javatatiana.springboot.controlller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import net.javatatiana.*;
import net.javatatiana.springboot.exception.ResourceNotFoundException;
import net.javatatiana.springboot.model.Employee;
import net.javatatiana.springboot.repository.EmployeeRepository;;





@RestController  // restfull indica controlador
@RequestMapping("/api/v1") // mapea solicitudes  http
public class EmployeeController {
	
	@Autowired //inyecta dependencia
    private EmployeeRepository employeeRepository;

	


	 // get  employees
	@GetMapping("/employees")//peticiones  get
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	
	
	
	 // get ID employees
	@GetMapping("/employees/{id}")
	//ResponseEntity sirve para manipular la respuesta http
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) // employeeId almacena  el id
			throws ResourceNotFoundException {
		//busca codigo
		Employee employee = employeeRepository.findById(employeeId)
				// manda la excepcion  llamamos al metodo y el mensaje
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}
	

	
	// guardar employees
	 @PostMapping("/employees")
		public Employee createEmployee(@Valid @RequestBody Employee employee) {
			return employeeRepository.save(employee);
		}
	 
	 
	 
	  
	//actualizar employees	 
	  @PutMapping("/employees/{id}") //peticion put
		public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
				@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException { //employeeDetails vatiable de   tipo Empleado
			Employee employee = employeeRepository.findById(employeeId)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));// llamamos al metodo de la  excepcion

			employee.setEmailId(employeeDetails.getEmailId());
			employee.setLastName(employeeDetails.getLastName());
			employee.setFirstName(employeeDetails.getFirstName());
			final Employee updatedEmployee = employeeRepository.save(employee);//  llamamos  al metodo save asignado a una variable de tipo Employee
			return ResponseEntity.ok(updatedEmployee);// como parametro la variable que cremaos
		}
	  
	  
	  
		// borrar  employees
	  @DeleteMapping("/employees/{id}")
		public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
				throws ResourceNotFoundException {
			Employee employee = employeeRepository.findById(employeeId)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

			employeeRepository.delete(employee);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response; // retorna  el hash
		}
	  
	  
	  
	  

}
