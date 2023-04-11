package com.helpet.service.pet.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.dto.request.CreatePetFeatureRequest;
import com.helpet.service.pet.dto.request.UpdatePetFeatureRequest;
import com.helpet.service.pet.mapper.PetFeatureMapper;
import com.helpet.service.pet.service.PetFeatureService;
import com.helpet.service.pet.storage.model.PetFeature;
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

@RequestMapping("/user/pets/{pet-id}/medical-card/features")
@RestController
public class PetFeatureController {
    private final PetFeatureService petFeatureService;

    private final PetFeatureMapper petFeatureMapper;

    @Autowired
    public PetFeatureController(PetFeatureService petFeatureService, PetFeatureMapper petFeatureMapper) {
        this.petFeatureService = petFeatureService;
        this.petFeatureMapper = petFeatureMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getPetFeatures(@PathVariable("pet-id") UUID petId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<PetFeature> petFeatures = petFeatureService.getPetFeatures(userId, petId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petFeatureMapper.mapCollection(petFeatures));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{feature-id}")
    public ResponseEntity<ResponseBody> getPetFeature(@PathVariable("pet-id") UUID petId,
                                                      @PathVariable("feature-id") UUID petFeatureId,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetFeature petFeature = petFeatureService.getPetFeature(userId, petId, petFeatureId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petFeatureMapper.map(petFeature));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createPetFeature(@PathVariable("pet-id") UUID petId,
                                                         @RequestBody @Valid CreatePetFeatureRequest createPetFeatureRequest,
                                                         JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetFeature newPetFeature = petFeatureService.createPetFeature(userId, petId, createPetFeatureRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petFeatureMapper.map(newPetFeature));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{feature-id}")
    public ResponseEntity<ResponseBody> updatePetFeature(@PathVariable("pet-id") UUID petId,
                                                         @PathVariable("feature-id") UUID featureId,
                                                         @RequestBody @Valid UpdatePetFeatureRequest updatePetFeatureRequest,
                                                         JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        PetFeature petFeature = petFeatureService.updatePetFeature(userId, petId, featureId, updatePetFeatureRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petFeatureMapper.map(petFeature));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{feature-id}")
    public ResponseEntity<ResponseBody> deletePetFeature(@PathVariable("pet-id") UUID petId,
                                                         @PathVariable("feature-id") UUID featureId,
                                                         JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        petFeatureService.deletePetFeature(userId, petId, featureId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
