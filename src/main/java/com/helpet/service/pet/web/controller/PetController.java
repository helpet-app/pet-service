package com.helpet.service.pet.web.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.service.PetService;
import com.helpet.service.pet.store.model.Pet;
import com.helpet.service.pet.web.dto.request.AddPetToFamilyRequest;
import com.helpet.service.pet.web.dto.request.CreatePetRequest;
import com.helpet.service.pet.web.dto.request.UpdateMedicalCardRequest;
import com.helpet.service.pet.web.mapper.MedicalCardMapper;
import com.helpet.service.pet.web.mapper.PetMapper;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RolesAllowed(Role.USER)
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

    @GetMapping("/user/pets")
    public ResponseEntity<ResponseBody> getUserPets(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        List<Pet> pets = petService.getUserPets(userId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petMapper.mapCollection(pets));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/user/pets/{pet-id}")
    public ResponseEntity<ResponseBody> getPet(@PathVariable("pet-id") UUID petId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Pet pet = petService.getPet(userId, petId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petMapper.map(pet));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/user/pets")
    public ResponseEntity<ResponseBody> createPet(@RequestBody CreatePetRequest request, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Pet newPet = petService.createPet(userId, request);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petMapper.map(newPet));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/user/families/{family-id}/pets")
    public ResponseEntity<ResponseBody> getFamilyPets(@PathVariable("family-id") UUID familyId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        List<Pet> pets = petService.getFamilyPets(userId, familyId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petMapper.mapCollection(pets));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/user/pets/{pet-id}/medical-card")
    public ResponseEntity<ResponseBody> getPetMedicalCard(@PathVariable("pet-id") UUID petId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Pet pet = petService.getPet(userId, petId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(medicalCardMapper.map(pet));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/user/pets/{pet-id}/medical-card")
    public ResponseEntity<ResponseBody> updatePetMedicalCard(@PathVariable("pet-id") UUID petId,
                                                             @RequestBody UpdateMedicalCardRequest request,
                                                             JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Pet pet = petService.updatePetMedicalCard(userId, petId, request);
        ResponseBody responseBody = new SuccessfulResponseBody<>(medicalCardMapper.map(pet));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/user/pets/{pet-id}/family")
    public ResponseEntity<ResponseBody> addPetToFamily(@PathVariable("pet-id") UUID petId,
                                                       @RequestBody AddPetToFamilyRequest request,
                                                       JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        petService.addPetToFamily(userId, petId, request);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
