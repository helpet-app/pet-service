package com.helpet.service.pet.web.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.service.DiseaseService;
import com.helpet.service.pet.store.model.Disease;
import com.helpet.service.pet.web.dto.request.CreateDiseaseRequest;
import com.helpet.service.pet.web.mapper.DiseaseMapper;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RolesAllowed(Role.USER)
@RequestMapping("/user/pets/{pet-id}/medical-card/disease-history")
@RestController
public class DiseaseController {
    private final DiseaseService diseaseService;

    private final DiseaseMapper diseaseMapper;

    @Autowired
    public DiseaseController(DiseaseService diseaseService, DiseaseMapper diseaseMapper) {
        this.diseaseService = diseaseService;
        this.diseaseMapper = diseaseMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getDiseases(@PathVariable("pet-id") UUID petId,
                                                    Pageable pageable,
                                                    JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Page<Disease> diseasePage = diseaseService.getPetDiseases(userId, petId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(diseaseMapper.mapPage(diseasePage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{disease-id}")
    public ResponseEntity<ResponseBody> getDisease(@PathVariable("pet-id") UUID petId,
                                                   @PathVariable("disease-id") UUID diseaseId,
                                                   JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Disease disease = diseaseService.getPetDisease(userId, petId, diseaseId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(diseaseMapper.map(disease));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createDisease(@PathVariable("pet-id") UUID petId,
                                                      @RequestBody CreateDiseaseRequest request,
                                                      JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Disease newDisease = diseaseService.createPetDisease(userId, petId, request);
        ResponseBody responseBody = new SuccessfulResponseBody<>(diseaseMapper.map(newDisease));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}
