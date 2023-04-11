package com.helpet.service.pet.controller;

import com.helpet.security.Role;
import com.helpet.service.pet.dto.request.CreatePetCategoryRequest;
import com.helpet.service.pet.dto.request.CreateSpeciesRequest;
import com.helpet.service.pet.service.PetCategoryService;
import com.helpet.service.pet.service.SpeciesService;
import com.helpet.service.pet.storage.model.PetCategory;
import com.helpet.service.pet.storage.model.Species;
import com.helpet.service.pet.mapper.PetCategoryMapper;
import com.helpet.service.pet.mapper.SpeciesMapper;
import com.helpet.web.response.ResponseBody;
import com.helpet.web.response.SuccessfulResponseBody;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/pet-categories")
@RestController
public class PetCategoryController {
    private final PetCategoryService petCategoryService;

    private final SpeciesService speciesService;

    private final PetCategoryMapper petCategoryMapper;

    private final SpeciesMapper speciesMapper;

    @Autowired
    public PetCategoryController(PetCategoryService petCategoryService,
                                 SpeciesService speciesService,
                                 PetCategoryMapper petCategoryMapper,
                                 SpeciesMapper speciesMapper) {
        this.petCategoryService = petCategoryService;
        this.speciesService = speciesService;
        this.petCategoryMapper = petCategoryMapper;
        this.speciesMapper = speciesMapper;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> getPetCategories() {
        List<PetCategory> petCategories = petCategoryService.getPetCategories();
        ResponseBody responseBody = new SuccessfulResponseBody<>(petCategoryMapper.mapCollection(petCategories));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @RolesAllowed(Role.ADMIN)
    @PostMapping
    public ResponseEntity<ResponseBody> createPetCategory(@RequestBody @Valid CreatePetCategoryRequest createPetCategoryRequest) {
        PetCategory newPetCategory = petCategoryService.createPetCategory(createPetCategoryRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(petCategoryMapper.map(newPetCategory));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/{pet-category-id}/species")
    public ResponseEntity<ResponseBody> getCategorySpecies(@PathVariable("pet-category-id") Integer petCategoryId,
                                                           @RequestParam(value = "name", defaultValue = "") String name,
                                                           Pageable pageable) {
        Page<Species> species = speciesService.getCategorySpecies(petCategoryId, name, pageable);
        ResponseBody responseBody = new SuccessfulResponseBody<>(speciesMapper.mapPage(species));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @RolesAllowed(Role.ADMIN)
    @PostMapping("/{pet-category-id}/species")
    public ResponseEntity<ResponseBody> createCategorySpecies(@PathVariable("pet-category-id") Integer petCategoryId,
                                                              @RequestBody @Valid CreateSpeciesRequest createSpeciesRequest) {
        Species newSpecies = speciesService.createSpecies(petCategoryId, createSpeciesRequest);
        ResponseBody responseBody = new SuccessfulResponseBody<>(speciesMapper.map(newSpecies));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}
