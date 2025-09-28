package com.udacity.jdnd.course3.critter.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String notes;
    private String phoneNumber;

    @OneToMany(mappedBy = "owner",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    private List<Pet> pets =new ArrayList<>();

    public long getId() {
        return id;}

    public void setId(long id) {
        this.id = id;}

    public String getName() {
        return name;}

    public void setName(String name) {
        this.name = name;}

    public String getPhoneNumber() {
        return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;}

    public String getNotes() {
        return notes;}

    public void setNotes(String notes) {
        this.notes = notes;}

    public List<Pet> getPets() {
        if(pets == null){
            pets=new ArrayList<>();
        }
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}