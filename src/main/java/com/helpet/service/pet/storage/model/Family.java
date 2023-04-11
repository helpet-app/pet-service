package com.helpet.service.pet.storage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "families")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private Account createdBy;

    @OneToMany(mappedBy = "family")
    private Set<Pet> pets = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "user_families",
               joinColumns = @JoinColumn(name = "family_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Account> members = new LinkedHashSet<>();
}