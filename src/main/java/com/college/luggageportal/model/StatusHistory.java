package com.college.luggageportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/** Audit trail: every status change is recorded here. */
@Entity
@Table(name = "status_history")
@Getter
@Setter
public class StatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Complaint complaint;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus newStatus;

    private String changedBy;
    private LocalDateTime changedAt;

    @Column(name = "note", length = 500)
    private String note;
}
