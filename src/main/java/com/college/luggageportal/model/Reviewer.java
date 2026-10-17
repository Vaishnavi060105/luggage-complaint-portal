package com.college.luggageportal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/** Airline staff member who reviews complaints. */
@Entity
@Table(name = "reviewers")
@Getter
@Setter
public class Reviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;
}
