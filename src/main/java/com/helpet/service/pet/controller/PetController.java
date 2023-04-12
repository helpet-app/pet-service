package com.helpet.service.pet.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.dto.request.CreatePetRequest;
import com.helpet.service.pet.dto.request.UpdateMedicalCardRequest;
import com.helpet.service.pet.mapper.MedicalCardMapper;
import com.helpet.service.pet.mapper.PetMapper;
import com.helpet.service.pet.service.PetService;
import com.helpet.service.pet.storage.model.Pet;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/user/pets")
@RestController
public class PetController {
    private final PetService petService;

    private final PetMapper petMapper;

    private final MedicalCardMapper medicalCardMapper;

    @Autowired
    public PetController(PetService petService, PetMapper petMapper, MedicalCardMapper medicalCardMapper) {
        this.petService = petService;
        this.petMapper = petMapper;
        this.medicalCardMapper = medicalCardMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getUserPets(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<Pet> pets = petService.getUserPets(userId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petMapper.mapCollection(pets));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{pet-id}")
    public ResponseEntity<ResponseBody> getPet(@PathVariable("pet-id") UUID petId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Pet pet = petService.getDetailedUserPet(userId, petId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petMapper.map(pet));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createPet(@RequestBody @Valid CreatePetRequest createPetRequest,
                                                  JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Pet newPet = petService.createPet(userId, createPetRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petMapper.map(newPet));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/{pet-id}/medical-card")
    public ResponseEntity<ResponseBody> getPetMedicalCard(@PathVariable("pet-id") UUID petId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Pet pet = petService.getDetailedUserPet(userId, petId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(medicalCardMapper.map(pet));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/{pet-id}/medical-card")
    public ResponseEntity<ResponseBody> updatePetMedicalCard(@PathVariable("pet-id") UUID petId,
                                                             @RequestBody @Valid UpdateMedicalCardRequest request,
                                                             JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Pet pet = petService.updatePetMedicalCard(userId, petId, request);
        ResponseBody responseBody = new SuccessfulResponseBody<>(medicalCardMapper.map(pet));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{pet-id}")
    public ResponseEntity<ResponseBody> deletePet(@PathVariable("pet-id") UUID petId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        petService.deleteUserPet(userId, petId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
