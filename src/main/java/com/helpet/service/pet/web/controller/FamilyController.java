package com.helpet.service.pet.web.controller;

import com.helpet.security.Role;
import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.service.FamilyService;
import com.helpet.service.pet.store.model.Family;
import com.helpet.service.pet.web.dto.request.AddFamilyMemberRequest;
import com.helpet.service.pet.web.dto.request.CreateFamilyRequest;
import com.helpet.service.pet.web.mapper.FamilyMapper;
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
@RequestMapping("/user/families")
@RestController
public class FamilyController {
    private final FamilyService familyService;

    private final FamilyMapper familyMapper;

    @Autowired
    public FamilyController(FamilyService familyService, FamilyMapper familyMapper) {
        this.familyService = familyService;
        this.familyMapper = familyMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getFamilies(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        List<Family> families = familyService.getUserFamilies(userId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(familyMapper.mapCollection(families));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{family-id}")
    public ResponseEntity<ResponseBody> getFamily(@PathVariable("family-id") UUID familyId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Family family = familyService.getUserFamily(userId, familyId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(familyMapper.map(family));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createFamily(@RequestBody CreateFamilyRequest request, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        Family family = familyService.createFamily(userId, request);
        ResponseBody responseBody = new SuccessfulResponseBody<>(familyMapper.map(family));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PatchMapping("/{family-id}/members")
    public ResponseEntity<ResponseBody> addFamilyMember(@PathVariable("family-id") UUID familyId,
                                                        @RequestBody AddFamilyMemberRequest request,
                                                        JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractId(jwtAuthenticationToken.getToken());
        familyService.addFamilyMember(userId, familyId, request);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
