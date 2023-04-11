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
@Table(name = "pet_vaccination_history")
public class PetVaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "vaccination_name", nullable = false, length = Integer.MAX_VALUE)
    private String vaccinationName;

    @Column(name = "comment", length = Integer.MAX_VALUE)
    private String comment;

    @NotNull
    @Column(name = "vaccinated_on", nullable = false)
    private LocalDate vaccinatedOn;

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