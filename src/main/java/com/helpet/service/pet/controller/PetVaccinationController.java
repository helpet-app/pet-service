package com.helpet.service.pet.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.dto.request.CreatePetVaccinationRequest;
import com.helpet.service.pet.dto.request.UpdatePetVaccinationRequest;
import com.helpet.service.pet.mapper.PetVaccinationMapper;
import com.helpet.service.pet.service.PetVaccinationService;
import com.helpet.service.pet.storage.model.PetVaccination;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/user/pets/{pet-id}/medical-card/vaccination-history")
@RestController
public class PetVaccinationController {
    private final PetVaccinationService petVaccinationService;

    private final PetVaccinationMapper petVaccinationMapper;

    @Autowired
    public PetVaccinationController(PetVaccinationService petVaccinationService, PetVaccinationMapper petVaccinationMapper) {
        this.petVaccinationService = petVaccinationService;
        this.petVaccinationMapper = petVaccinationMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getPetVaccinations(@PathVariable("pet-id") UUID petId,
                                                           Pageable pageable,
                                                           JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Page<PetVaccination> vaccinationPage = petVaccinationService.getPetVaccinations(userId, petId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petVaccinationMapper.mapPage(vaccinationPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{vaccination-id}")
    public ResponseEntity<ResponseBody> getPetVaccination(@PathVariable("pet-id") UUID petId,
                                                          @PathVariable("vaccination-id") UUID vaccinationId,
                                                          JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetVaccination petVaccination = petVaccinationService.getPetVaccination(userId, petId, vaccinationId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petVaccinationMapper.map(petVaccination));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createPetVaccination(@PathVariable("pet-id") UUID petId,
                                                             @RequestBody @Valid CreatePetVaccinationRequest createPetVaccinationRequest,
                                                             JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetVaccination newPetVaccination = petVaccinationService.createPetVaccination(userId, petId, createPetVaccinationRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petVaccinationMapper.map(newPetVaccination));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{vaccination-id}")
    public ResponseEntity<ResponseBody> updatePetVaccination(@PathVariable("pet-id") UUID petId,
                                                             @PathVariable("vaccination-id") UUID vaccinationId,
                                                             @RequestBody @Valid UpdatePetVaccinationRequest updatePetVaccinationRequest,
                                                             JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetVaccination petVaccination = petVaccinationService.updatePetVaccination(userId, petId, vaccinationId, updatePetVaccinationRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petVaccinationMapper.map(petVaccination));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{vaccination-id}")
    public ResponseEntity<ResponseBody> deletePetVaccination(@PathVariable("pet-id") UUID petId,
                                                             @PathVariable("vaccination-id") UUID vaccinationId,
                                                             JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        petVaccinationService.deletePetVaccination(userId, petId, vaccinationId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
