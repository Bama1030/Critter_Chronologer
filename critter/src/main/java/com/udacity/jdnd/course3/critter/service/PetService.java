package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {
    private final PetRepository petRepo;
    private final CustomerRepository customerRepo;

    public PetService(PetRepository petRepo, CustomerRepository customerRepo) {
        this.petRepo = petRepo;
        this.customerRepo = customerRepo;
    }

    public PetDTO updatePet(PetDTO petDTO){
        Pet pet= new Pet();
        pet.setId(petDTO.getId());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setType(petDTO.getType());
        pet.setNotes(petDTO.getNotes());
        pet.setOwner(petDTO.getOwnerId()!=0?
                customerRepo.findById(petDTO.getOwnerId())
                        .orElseThrow(()->new IllegalArgumentException("No customer Found with id:"+petDTO.getOwnerId())):null);
        if (pet.getOwner()!= null)pet.getOwner().getPets().add(pet);
        Pet savedPet=petRepo.save(pet);
        return convertToDo(savedPet);
    }

    public List<PetDTO> getAllPet(){
        return petRepo.findAll().stream().map(this::convertToDo)
                .collect(Collectors.toList());
    }

    public PetDTO findPetById(long petId){
        Pet pet=petRepo.findById(petId)
                .orElseThrow(()-> new IllegalArgumentException("no pet found by id:"+petId));
        return convertToDo(pet);
    }

    public  List<PetDTO> getPetByOwner(long customerId){
        Customer owner= customerRepo.findById(customerId)
                .orElseThrow(()->new IllegalArgumentException("customer not found by id:"+ customerId)
        );
        return petRepo.findByOwnerId(owner.getId()).stream().map(this::convertToDo)
                .collect(Collectors.toList());
    }

    private PetDTO convertToDo(Pet pet){
        PetDTO dto= new PetDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        dto.setType(pet.getType());
        dto.setNotes(pet.getNotes());
        Optional.ofNullable(pet.getOwner()).map(Customer::getId)
                .ifPresent(dto::setOwnerId);
        return dto;
    }
}
