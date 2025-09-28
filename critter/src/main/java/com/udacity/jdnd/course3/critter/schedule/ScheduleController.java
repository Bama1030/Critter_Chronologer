package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleServices;


    public ScheduleController(ScheduleService scheduleServices) {
        this.scheduleServices = scheduleServices;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        ScheduleDTO createdSchedule=scheduleServices.saveSchedule(scheduleDTO);
        return createdSchedule;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> schedule=scheduleServices.getsAll();
        return schedule;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> petSchedule=scheduleServices.getForPets(petId);
        return petSchedule;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        return scheduleServices.getForEmployees(employeeId);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> customerSchedule=scheduleServices.getForCustomers(customerId);
        return customerSchedule;
    }
}
