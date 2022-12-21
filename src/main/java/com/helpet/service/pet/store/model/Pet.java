package com.helpet.service.pet.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "pets", indexes = {
        @Index(name = "pets_family_fkey", columnList = "family_id"),
        @Index(name = "pets_owner_fkey", columnList = "owner_id")
})
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "avatar_url", length = Integer.MAX_VALUE)
    private String avatarUrl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id")
    private Family family;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "is_sterilized")
    private Boolean isSterilized;

    @Column(name = "chip_number", length = Integer.MAX_VALUE)
    private String chipNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_category_id", nullable = false)
    private PetCategory petCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id")
    private Species species;

    @OneToMany(mappedBy = "pet")
    private Set<Disease> diseaseHistory = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pet")
    private Set<PetFeature> petFeatures = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pet")
    private Set<Vaccination> vaccinationHistory = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pet")
    private Set<Anthropometry> anthropometryHistory = new LinkedHashSet<>();
}