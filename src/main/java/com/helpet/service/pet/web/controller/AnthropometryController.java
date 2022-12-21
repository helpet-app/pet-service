package com.helpet.service.pet.web.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.service.AnthropometryService;
import com.helpet.service.pet.store.model.Anthropometry;
import com.helpet.service.pet.web.dto.request.CreateAnthropometryRequest;
import com.helpet.service.pet.web.mapper.AnthropometryMapper;
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
@RequestMapping("/user/pets/{pet-id}/medical-card/anthropometry-history")
@RestController
public class AnthropometryController {
    private final AnthropometryService anthropometryService;

    private final AnthropometryMapper anthropometryMapper;

    @Autowired
    public AnthropometryController(AnthropometryService anthropometryService, AnthropometryMapper anthropometryMapper) {
        this.anthropometryService = anthropometryService;
        this.anthropometryMapper = anthropometryMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getAnthropometries(@PathVariable("pet-id") UUID petId,
                                                           Pageable pageable,
                                                           JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Page<Anthropometry> anthropometryPage = anthropometryService.getPetAnthropometries(userId, petId, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(anthropometryMapper.mapPage(anthropometryPage));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{anthropometry-id}")
    public ResponseEntity<ResponseBody> getAnthropometry(@PathVariable("pet-id") UUID petId,
                                                         @PathVariable("anthropometry-id") UUID anthropometryId,
                                                         JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Anthropometry anthropometry = anthropometryService.getPetAnthropometry(userId, petId, anthropometryId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(anthropometryMapper.map(anthropometry));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createAnthropometry(@PathVariable("pet-id") UUID petId,
                                                            @RequestBody CreateAnthropometryRequest request,
                                                            JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Anthropometry newAnthropometry = anthropometryService.createPetAnthropometry(userId, petId, request);
        ResponseBody responseBody = new SuccessfulResponseBody<>(anthropometryMapper.map(newAnthropometry));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}
