package com.helpet.service.pet.web.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.service.VaccinationService;
import com.helpet.service.pet.store.model.Vaccination;
import com.helpet.service.pet.web.dto.request.CreateVaccinationRequest;
import com.helpet.service.pet.web.mapper.VaccinationMapper;
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
@RequestMapping("/user/pets/{pet-id}/medical-card/vaccination-history")
@RestController
public class VaccinationController {
    private final VaccinationService vaccinationService;

    private final VaccinationMapper vaccinationMapper;

    @Autowired
    public VaccinationController(VaccinationService vaccinationService, VaccinationMapper vaccinationMapper) {
        this.vaccinationService = vaccinationService;
        this.vaccinationMapper = vaccinationMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getVaccinations(@PathVariable("pet-id") UUID petId,
                                                        Pageable pageable,
                                                        JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Page<Vaccination> vaccinationPage = vaccinationService.getPetVaccinations(userId, petId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vaccinationMapper.mapPage(vaccinationPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{vaccination-id}")
    public ResponseEntity<ResponseBody> getVaccination(@PathVariable("pet-id") UUID petId,
                                                       @PathVariable("vaccination-id") UUID vaccinationId,
                                                       JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Vaccination vaccination = vaccinationService.getPetVaccination(userId, petId, vaccinationId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vaccinationMapper.map(vaccination));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createVaccination(@PathVariable("pet-id") UUID petId,
                                                          @RequestBody CreateVaccinationRequest request,
                                                          JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Vaccination newVaccination = vaccinationService.createPetVaccination(userId, petId, request);
        ResponseBody responseBody = new SuccessfulResponseBody<>(vaccinationMapper.map(newVaccination));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}
