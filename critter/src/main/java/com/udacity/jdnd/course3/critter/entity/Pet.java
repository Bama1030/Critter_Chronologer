package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer owner;
    private String notes;

    private LocalDate birthDate;

    public long getId() {
        return id;}

    public void setId(long id) {
        this.id = id;}

    public PetType getType() {
        return type;}
    public void setType(PetType type) {
        this.type = type;}

    public String getName() {
        return name;}

    public void setName(String name) {
        this.name = name;}

    public Customer getOwner() {
        return owner;}

    public void setOwner(Customer owner) {
        this.owner = owner;}

    public String getNotes() {
        return notes;}

    public void setNotes(String notes) {
        this.notes = notes;}

    public LocalDate getBirthDate() {
        return birthDate;}

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;}
}
