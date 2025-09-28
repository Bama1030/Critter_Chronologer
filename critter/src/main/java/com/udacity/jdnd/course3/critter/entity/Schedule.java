package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "schedule_employees", joinColumns = {@JoinColumn(name = "schedule_id")},

            inverseJoinColumns ={ @JoinColumn(name = "employee_id")}
    )
    private List<Employee> employees;

    @ManyToMany
    @JoinTable(
            name = "schedule_pets", joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "pet_id")}
    )
    private List<Pet>pets;
    private LocalDate date;
    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "schedule_activities")
    @Column(name = "activity", nullable = false)
    private Set<EmployeeSkill> activities;

    public long getId() {
        return id;}
    public void setId(long id) {
        this.id = id;}

    public List<Employee> getEmployees() {
        return employees;}

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;}

    public LocalDate getDate() {
        return date;}

    public void setDate(LocalDate date) {
        this.date = date;}

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;}

    public Set<EmployeeSkill> getActivities() {
        return activities;}
    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;}
}
