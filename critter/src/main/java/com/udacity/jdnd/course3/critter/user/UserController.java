package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final EmployeeService employeeServices;

    private final CustomerService customerServices;

    public UserController(EmployeeService employeeServices, CustomerService customerServices) {
        this.employeeServices = employeeServices;
        this.customerServices = customerServices;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO savedCustomer=customerServices.saveOrUpdateCustomer(customerDTO);
        return savedCustomer;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerServices.getAllCustomer();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        CustomerDTO owner=customerServices.getOwnerByPetId(petId);
        return owner;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployee=employeeServices.savedEmployees(employeeDTO);

        return savedEmployee;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        EmployeeDTO employee= employeeServices.getEmployeeById(employeeId);
        return employee;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> availableDays,
                                @PathVariable long employeeId) {

        employeeServices.updateAvailability(employeeId,availableDays);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        List<EmployeeDTO> matchEmployees= employeeServices.findAvailableEmployee(
                employeeDTO.getSkills(),
                employeeDTO.getDate().getDayOfWeek()
        );

        return matchEmployees;
    }

}
