package com.helpet.service.pet.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.dto.request.CreatePetDiseaseRequest;
import com.helpet.service.pet.dto.request.UpdatePetDiseaseRequest;
import com.helpet.service.pet.mapper.PetDiseaseMapper;
import com.helpet.service.pet.service.PetDiseaseService;
import com.helpet.service.pet.storage.model.PetDisease;
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

@RequestMapping("/user/pets/{pet-id}/medical-card/disease-history")
@RestController
public class PetDiseaseController {
    private final PetDiseaseService petDiseaseService;

    private final PetDiseaseMapper petDiseaseMapper;

    @Autowired
    public PetDiseaseController(PetDiseaseService petDiseaseService, PetDiseaseMapper petDiseaseMapper) {
        this.petDiseaseService = petDiseaseService;
        this.petDiseaseMapper = petDiseaseMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getPetDiseases(@PathVariable("pet-id") UUID petId,
                                                       Pageable pageable,
                                                       JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Page<PetDisease> diseasePage = petDiseaseService.getPetDiseases(userId, petId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petDiseaseMapper.mapPage(diseasePage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{disease-id}")
    public ResponseEntity<ResponseBody> getPetDisease(@PathVariable("pet-id") UUID petId,
                                                      @PathVariable("disease-id") UUID diseaseId,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetDisease petDisease = petDiseaseService.getPetDisease(userId, petId, diseaseId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petDiseaseMapper.map(petDisease));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createPetDisease(@PathVariable("pet-id") UUID petId,
                                                         @RequestBody @Valid CreatePetDiseaseRequest createPetDiseaseRequest,
                                                         JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetDisease newPetDisease = petDiseaseService.createPetDisease(userId, petId, createPetDiseaseRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petDiseaseMapper.map(newPetDisease));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{disease-id}")
    public ResponseEntity<ResponseBody> updatePetDisease(@PathVariable("pet-id") UUID petId,
                                                         @PathVariable("disease-id") UUID diseaseId,
                                                         @RequestBody @Valid UpdatePetDiseaseRequest updatePetDiseaseRequest,
                                                         JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetDisease petDisease = petDiseaseService.updatePetDisease(userId, petId, diseaseId, updatePetDiseaseRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petDiseaseMapper.map(petDisease));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{disease-id}")
    public ResponseEntity<ResponseBody> deletePetDisease(@PathVariable("pet-id") UUID petId,
                                                         @PathVariable("disease-id") UUID diseaseId,
                                                         JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        petDiseaseService.deletePetDisease(userId, petId, diseaseId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
