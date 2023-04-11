package com.helpet.service.pet.storage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "username", nullable = false, length = Integer.MAX_VALUE)
    private String username;

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "avatar_url", length = Integer.MAX_VALUE)
    private String avatarUrl;
}