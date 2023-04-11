package com.helpet.service.pet.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.dto.request.CreatePetAnthropometryRequest;
import com.helpet.service.pet.dto.request.UpdatePetAnthropometryRequest;
import com.helpet.service.pet.mapper.PetAnthropometryMapper;
import com.helpet.service.pet.service.PetAnthropometryService;
import com.helpet.service.pet.storage.model.PetAnthropometry;
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

@RequestMapping("/user/pets/{pet-id}/medical-card/anthropometry-history")
@RestController
public class PetAnthropometryController {
    private final PetAnthropometryService petAnthropometryService;

    private final PetAnthropometryMapper petAnthropometryMapper;

    @Autowired
    public PetAnthropometryController(PetAnthropometryService petAnthropometryService, PetAnthropometryMapper petAnthropometryMapper) {
        this.petAnthropometryService = petAnthropometryService;
        this.petAnthropometryMapper = petAnthropometryMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getPetAnthropometries(@PathVariable("pet-id") UUID petId,
                                                              Pageable pageable,
                                                              JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Page<PetAnthropometry> anthropometryPage = petAnthropometryService.getPetAnthropometries(userId, petId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petAnthropometryMapper.mapPage(anthropometryPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{anthropometry-id}")
    public ResponseEntity<ResponseBody> getPetAnthropometry(@PathVariable("pet-id") UUID petId,
                                                            @PathVariable("anthropometry-id") UUID anthropometryId,
                                                            JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetAnthropometry petAnthropometry = petAnthropometryService.getPetAnthropometry(userId, petId, anthropometryId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petAnthropometryMapper.map(petAnthropometry));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createPetAnthropometry(@PathVariable("pet-id") UUID petId,
                                                               @RequestBody @Valid CreatePetAnthropometryRequest createPetAnthropometryRequest,
                                                               JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetAnthropometry newPetAnthropometry = petAnthropometryService.createPetAnthropometry(userId, petId, createPetAnthropometryRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petAnthropometryMapper.map(newPetAnthropometry));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{anthropometry-id}")
    public ResponseEntity<ResponseBody> updatePetAnthropometry(@PathVariable("pet-id") UUID petId,
                                                               @PathVariable("anthropometry-id") UUID anthropometryId,
                                                               @RequestBody @Valid UpdatePetAnthropometryRequest updatePetAnthropometryRequest,
                                                               JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetAnthropometry petAnthropometry = petAnthropometryService.updatePetAnthropometry(userId, petId, anthropometryId,
                                                                                           updatePetAnthropometryRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petAnthropometryMapper.map(petAnthropometry));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{anthropometry-id}")
    public ResponseEntity<ResponseBody> deletePetAnthropometry(@PathVariable("pet-id") UUID petId,
                                                               @PathVariable("anthropometry-id") UUID anthropometryId,
                                                               JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        petAnthropometryService.deletePetAnthropometry(userId, petId, anthropometryId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
