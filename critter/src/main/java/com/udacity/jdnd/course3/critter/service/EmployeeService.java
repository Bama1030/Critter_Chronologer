package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepo;

    public EmployeeService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public EmployeeDTO savedEmployees(EmployeeDTO employeeDTO){
        Employee emp= new Employee();
        emp.setId(employeeDTO.getId());
        emp.setName(employeeDTO.getName());
        if (employeeDTO.getDaysAvailable()!=null) emp.setDaysAvailable(employeeDTO.getDaysAvailable());

        if(employeeDTO.getSkills()!=null) emp.setSkills(employeeDTO.getSkills());

        return  converToDto(employeeRepo.save(emp));
    }

    public EmployeeDTO getEmployeeById(long id){
        Employee employee=employeeRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Employee is not found with the id:"+id));
        return converToDto(employee);
    }

    public void updateAvailability(long employeeId, Set<DayOfWeek> availability){
        Employee employee= employeeRepo.findById(employeeId)
                .orElseThrow(()->new IllegalArgumentException("Employee not found with id:"+employeeId)
        );
        employee.setDaysAvailable(availability);

        employeeRepo.save(employee);
    }
    public List<EmployeeDTO> findAvailableEmployee(Set<EmployeeSkill>requiredSkills, DayOfWeek day){
        return employeeRepo.findAll().stream().filter(em->em.getDaysAvailable() != null && em.getDaysAvailable()
                        .contains(day)).filter(em->em.getSkills()!= null && em.getSkills().containsAll(requiredSkills))
                .map(this::converToDto).collect(Collectors.toList());
    }

    private EmployeeDTO converToDto(Employee employee){

        EmployeeDTO dto= new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());

        dto.setDaysAvailable(employee.getDaysAvailable());
        dto.setSkills(employee.getSkills());
        return dto;
    }
}
