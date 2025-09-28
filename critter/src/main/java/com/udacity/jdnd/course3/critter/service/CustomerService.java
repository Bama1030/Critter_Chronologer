package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final PetRepository petRepo;

    public CustomerService(CustomerRepository customerRepo, PetRepository petRepo) {
        this.customerRepo = customerRepo;

        this.petRepo = petRepo;
    }

    public CustomerDTO saveOrUpdateCustomer(CustomerDTO customerDTO){
        Customer customer=new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());

        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        return convertToDto(customerRepo.save(customer));
    }

    public List<CustomerDTO> getAllCustomer(){
        return customerRepo.findAll().stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public CustomerDTO getOwnerByPetId(long petId){
        Pet pet= petRepo.findById(petId)
                .orElseThrow(()-> new IllegalArgumentException("No pet is found with id:"+petId));


        Customer owner=pet.getOwner();
        if (owner ==null){
            throw new IllegalStateException("pet with id : "+petId+" has no assigned owner");}
        return convertToDto(owner);
    }

    private CustomerDTO convertToDto(Customer customer){
        CustomerDTO dto= new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setNotes(customer.getNotes());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setPetIds(
                Optional.ofNullable(customer.getPets())
                        .map(pets->pets.stream().map(Pet::getId).toList()).orElse(List.of())
        );
        return dto;
    }
}
