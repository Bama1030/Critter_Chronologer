package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepo;
    private final EmployeeRepository employeeRepo;
    private final PetRepository petRepo;
    private final CustomerRepository customerRepo;

    public ScheduleService(ScheduleRepository scheduleRepo, EmployeeRepository employeeRepo, 
                           PetRepository petRepo, CustomerRepository customerRepo) {
        this.scheduleRepo = scheduleRepo;
        this.employeeRepo = employeeRepo;
        this.petRepo = petRepo;
        this.customerRepo = customerRepo;
    }

    public ScheduleDTO saveSchedule(ScheduleDTO dto){
        Schedule schedule=new Schedule();
        schedule.setActivities(dto.getActivities());
        schedule.setDate(dto.getDate());

        schedule.setPets(fetchPets(dto.getPetIds()));
        schedule.setEmployees(fetchEmployees(dto.getEmployeeIds()));

        return mapToDTO(scheduleRepo.save(schedule));
    }

    public List<ScheduleDTO>getsAll(){
        return scheduleRepo.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getForPets(long petId){
        Pet pet=petRepo.findById(petId)
                .orElseThrow(()->new IllegalArgumentException("Pet is not found:"+petId));
        return scheduleRepo.findByPetsId(petId).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getForEmployees(Long employeeId){
        Employee employee= employeeRepo.findById(employeeId)
                .orElseThrow(()-> new IllegalArgumentException("Employee is not found:"+employeeId));


        return scheduleRepo.findByEmployeesId(employeeId).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getForCustomers(long customerId){
        Customer customer= customerRepo.findById(customerId)
                .orElseThrow(()-> new IllegalArgumentException("customer is not found:"+customerId));
        if (customer.getPets()==null || customer.getPets().isEmpty()){
            return Collections.emptyList();
        }
        return customer.getPets().stream()
                .flatMap(pet-> scheduleRepo.findByPetsId(pet.getId()).stream())
                .distinct().map(this::mapToDTO).collect(Collectors.toList());
    }

    private List<Pet> fetchPets(List<Long> ids){
        if(ids == null || ids.isEmpty()) return Collections.emptyList();

        return ids.stream()
                .map(id-> petRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Pet is not found:"+id)))
                .collect(Collectors.toList());
    }

    private List<Employee> fetchEmployees(List<Long> ids){
        if (ids==null || ids.isEmpty()) return Collections.emptyList();
        return ids.stream().map(id -> employeeRepo.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Employees is not found:"+id)))
                .collect(Collectors.toList());
    }
    
    private ScheduleDTO mapToDTO(Schedule schedule){
        ScheduleDTO dto= new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setActivities(schedule.getActivities());
        dto.setDate(schedule.getDate());
        dto.setEmployeeIds(schedule.getEmployees()==null
        ? Collections.emptyList():schedule.getEmployees()
                .stream().map(Employee::getId).collect(Collectors.toList()));

        dto.setPetIds(schedule.getPets()==null
        ?Collections.emptyList():schedule.getPets()
                .stream().map(Pet::getId).collect(Collectors.toList()));
        return dto;
    }
}
