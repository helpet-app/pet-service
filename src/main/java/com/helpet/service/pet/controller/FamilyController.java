package com.helpet.service.pet.controller;

import com.helpet.security.jwt.JwtPayloadExtractor;
import com.helpet.service.pet.dto.request.AddFamilyMemberRequest;
import com.helpet.service.pet.dto.request.AddPetToFamilyRequest;
import com.helpet.service.pet.dto.request.CreateFamilyRequest;
import com.helpet.service.pet.dto.request.UpdateFamilyRequest;
import com.helpet.service.pet.mapper.FamilyMapper;
import com.helpet.service.pet.mapper.FamilyMemberMapper;
import com.helpet.service.pet.mapper.FamilyPetMapper;
import com.helpet.service.pet.service.FamilyService;
import com.helpet.service.pet.storage.model.Account;
import com.helpet.service.pet.storage.model.Family;
import com.helpet.service.pet.storage.model.Pet;
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

@RequestMapping("/user/families")
@RestController
public class FamilyController {
    private final FamilyService familyService;

    private final FamilyMapper familyMapper;

    private final FamilyMemberMapper familyMemberMapper;

    private final FamilyPetMapper familyPetMapper;

    @Autowired
    public FamilyController(FamilyService familyService,
                            FamilyMapper familyMapper,
                            FamilyMemberMapper familyMemberMapper,
                            FamilyPetMapper familyPetMapper) {
        this.familyService = familyService;
        this.familyMapper = familyMapper;
        this.familyMemberMapper = familyMemberMapper;
        this.familyPetMapper = familyPetMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getUserFamilies(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<Family> families = familyService.getUserFamilies(userId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(familyMapper.mapCollection(families));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{family-id}")
    public ResponseEntity<ResponseBody> getUserFamily(@PathVariable("family-id") UUID familyId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Family family = familyService.getUserFamily(userId, familyId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(familyMapper.map(family));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createFamily(@RequestBody @Valid CreateFamilyRequest createFamilyRequest,
                                                     JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Family family = familyService.createFamily(userId, createFamilyRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(familyMapper.map(family));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{family-id}")
    public ResponseEntity<ResponseBody> updateFamily(@PathVariable("family-id") UUID familyId,
                                                     @RequestBody @Valid UpdateFamilyRequest updateFamilyRequest,
                                                     JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        Family family = familyService.updateFamily(userId, familyId, updateFamilyRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(familyMapper.map(family));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{family-id}")
    public ResponseEntity<ResponseBody> deleteFamily(@PathVariable("family-id") UUID familyId,
                                                     JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        familyService.deleteFamily(userId, familyId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{family-id}/members")
    public ResponseEntity<ResponseBody> getFamilyMembers(@PathVariable("family-id") UUID familyId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<Account> members = familyService.getFamilyMembers(userId, familyId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(familyMemberMapper.mapCollection(members));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/{family-id}/members")
    public ResponseEntity<ResponseBody> addFamilyMember(@PathVariable("family-id") UUID familyId,
                                                        @RequestBody @Valid AddFamilyMemberRequest addFamilyMemberRequest,
                                                        JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        familyService.addFamilyMember(userId, familyId, addFamilyMemberRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @DeleteMapping("/{family-id}/members/{member-username}")
    public ResponseEntity<ResponseBody> removeFamilyMember(@PathVariable("family-id") UUID familyId,
                                                           @PathVariable("member-username") String memberUsername,
                                                           JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        familyService.removeFamilyMember(userId, familyId, memberUsername);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{family-id}/pets")
    public ResponseEntity<ResponseBody> getFamilyPets(@PathVariable("family-id") UUID familyId, JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<Pet> pets = familyService.getFamilyPets(userId, familyId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(familyPetMapper.mapCollection(pets));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/{family-id}/pets")
    public ResponseEntity<ResponseBody> addPetToFamily(@PathVariable("family-id") UUID familyId,
                                                       @RequestBody @Valid AddPetToFamilyRequest addPetToFamilyRequest,
                                                       JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        familyService.addPetToFamily(userId, familyId, addPetToFamilyRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @DeleteMapping("/{family-id}/pets/{pet-id}")
    public ResponseEntity<ResponseBody> removePetFromFamily(@PathVariable("family-id") UUID familyId,
                                                            @PathVariable("pet-id") UUID petId,
                                                            JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        familyService.removePetFromFamily(userId, familyId, petId);
        ResponseBody responseBody = new SuccessfulResponseBody<>();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/members")
    public ResponseEntity<ResponseBody> getAllFamiliesMembers(JwtAuthenticationToken jwtAuthenticationToken) {
        UUID userId = JwtPayloadExtractor.extractSubject(jwtAuthenticationToken.getToken());
        List<Account> familiesMembers = familyService.getAllFamiliesMembers(userId);
        ResponseBody responseBody = new SuccessfulResponseBody<>(familyMemberMapper.mapCollection(familiesMembers));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
