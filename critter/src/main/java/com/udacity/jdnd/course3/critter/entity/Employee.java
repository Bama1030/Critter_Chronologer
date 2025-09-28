package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String name;

    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employees_availabilities")
    @Column(name = "days_of_week")
    private Set<DayOfWeek> dayAvailable;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employee_skills")
    @Column(name = "skills")
    private  Set<EmployeeSkill> skill;

    public long getId() {
        return id;}
    public void setId(long id) {
        this.id = id;}

    public String getName() {
        return name;}

    public void setName(String name) {
        this.name = name;}

    public Set<DayOfWeek> getDaysAvailable() {
        return dayAvailable;}

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.dayAvailable = daysAvailable;}

    public Set<EmployeeSkill> getSkills() {
        return skill;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skill = skills;}
}
