package com.college.luggageportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/** A passenger's luggage complaint. */
@Entity
@Table(name = "complaints")
@Getter
@Setter
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String referenceNo;

    private String passengerName;
    private String email;
    private String phone;
    private String flightNo;
    private String pnr;
    private String bagTag;

    @Enumerated(EnumType.STRING)
    @Column(name = "complaint_type")
    private ComplaintType type;

    @Column(length = 500)
    private String description;

    private String attachmentPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "complaint_status")
    private ComplaintStatus status;

    @Column(length = 500)
    private String reviewerComment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() { createdAt = LocalDateTime.now(); updatedAt = createdAt; }

    @PreUpdate
    void onUpdate() { updatedAt = LocalDateTime.now(); }
}
