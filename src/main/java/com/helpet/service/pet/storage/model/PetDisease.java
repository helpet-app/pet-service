package com.helpet.service.pet.storage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pet_disease_history")
public class PetDisease {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "disease_name", nullable = false, length = Integer.MAX_VALUE)
    private String diseaseName;

    @Column(name = "comment", length = Integer.MAX_VALUE)
    private String comment;

    @NotNull
    @Column(name = "got_sick_on", nullable = false)
    private LocalDate gotSickOn;

    @Column(name = "recovered_on")
    private LocalDate recoveredOn;

    @Builder.Default
    @NotNull
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}