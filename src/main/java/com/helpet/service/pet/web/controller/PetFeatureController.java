package com.helpet.service.pet.web.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.service.PetFeatureService;
import com.helpet.service.pet.store.model.PetFeature;
import com.helpet.service.pet.web.dto.request.CreatePetFeatureRequest;
import com.helpet.service.pet.web.mapper.PetFeatureMapper;
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
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        List<PetFeature> petFeatures = petFeatureService.getPetFeatures(userId, petId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petFeatureMapper.mapCollection(petFeatures));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{pet-feature-id}")
    public ResponseEntity<ResponseBody> getPetFeature(@PathVariable("pet-id") UUID petId,
                                                      @PathVariable("pet-feature-id") UUID petFeatureId,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        PetFeature petFeature = petFeatureService.getPetFeature(userId, petId, petFeatureId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petFeatureMapper.map(petFeature));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createPetFeature(@PathVariable("pet-id") UUID petId,
                                                         @RequestBody CreatePetFeatureRequest request,
                                                         JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        PetFeature petFeature = petFeatureService.createPetFeature(userId, petId, request);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petFeatureMapper.map(petFeature));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}
