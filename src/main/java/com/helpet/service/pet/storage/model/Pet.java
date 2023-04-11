package com.helpet.service.pet.storage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "avatar_url", length = Integer.MAX_VALUE)
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "is_spayed_or_neutered")
    private Boolean isSpayedOrNeutered;

    @Column(name = "chip_number", length = Integer.MAX_VALUE)
    private String chipNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_category_id")
    private PetCategory petCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id")
    private Species species;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private Family family;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private Account createdBy;

    @OneToMany(mappedBy = "pet")
    private Set<PetDisease> petDiseaseHistory = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pet")
    private Set<PetFeature> petFeatures = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pet")
    private Set<PetVaccination> petVaccinationHistory = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pet")
    private Set<PetAnthropometry> petAnthropometryHistory = new LinkedHashSet<>();
}