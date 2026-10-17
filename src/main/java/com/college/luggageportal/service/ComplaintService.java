package com.college.luggageportal.service;

import com.college.luggageportal.dto.ComplaintForm;
import com.college.luggageportal.exception.ComplaintNotFoundException;
import com.college.luggageportal.model.*;
import com.college.luggageportal.repository.ComplaintRepository;
import com.college.luggageportal.repository.StatusHistoryRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Business rules: submission, reference numbers, workflow and audit history. */
@Service
public class ComplaintService {
    private final ComplaintRepository complaints;
    private final StatusHistoryRepository history;
    private final FileStorageService storage;

    public ComplaintService(ComplaintRepository complaints, StatusHistoryRepository history, FileStorageService storage) {
        this.complaints = complaints;
        this.history = history;
        this.storage = storage;
    }

    @Transactional
    public Complaint submit(ComplaintForm f) {
        Complaint c = new Complaint();
        c.setPassengerName(f.getPassengerName().trim());
        c.setEmail(f.getEmail().trim());
        c.setPhone(f.getPhone());
        c.setFlightNo(f.getFlightNo());
        c.setPnr(f.getPnr());
        c.setBagTag(f.getBagTag());
        c.setType(f.getType());
        c.setDescription(f.getDescription().trim());
        c.setStatus(ComplaintStatus.SUBMITTED);
        if (f.getAttachment() != null && !f.getAttachment().isEmpty()) {
            try {
                c.setAttachmentPath(storage.store(f.getAttachment()));
            } catch (IOException e) {
                throw new IllegalStateException("Could not store attachment", e);
            }
        }
        c = complaints.save(c);
        // Reference number is built from the unique database id, so it can never repeat.
        c.setReferenceNo(String.format("LCP-%d-%06d", Year.now().getValue(), c.getId()));
        c = complaints.save(c);
        record(c, null, ComplaintStatus.SUBMITTED, "passenger", "Complaint submitted");
        return c;
    }

    @Transactional(readOnly = true)
    public Optional<Complaint> findByReference(String ref) {
        return complaints.findByReferenceNo(ref);
    }

    @Transactional(readOnly = true)
    public List<Complaint> list(ComplaintStatus status) {
        return status == null ? complaints.findAllByOrderByIdDesc() : complaints.findByStatusOrderByIdDesc(status);
    }

    @Transactional(readOnly = true)
    public List<StatusHistory> historyOf(Long complaintId) {
        return history.findByComplaintIdOrderByIdAsc(complaintId);
    }

    /** Opening a SUBMITTED complaint moves it to UNDER_REVIEW automatically. */
    @Transactional
    public Complaint openForReview(Long id, String reviewer) {
        Complaint c = get(id);
        if (c.getStatus() == ComplaintStatus.SUBMITTED) {
            c.setStatus(ComplaintStatus.UNDER_REVIEW);
            c = complaints.save(c);
            record(c, ComplaintStatus.SUBMITTED, ComplaintStatus.UNDER_REVIEW, reviewer, "Opened for review");
        }
        return c;
    }

    @Transactional
    public Complaint decide(Long id, String decision, String comment, String reviewer) {
        if (comment == null || comment.isBlank()) throw new IllegalArgumentException("A comment is required.");
        ComplaintStatus target;
        if ("APPROVE".equalsIgnoreCase(decision)) target = ComplaintStatus.APPROVED;
        else if ("REJECT".equalsIgnoreCase(decision)) target = ComplaintStatus.REJECTED;
        else throw new IllegalArgumentException("Decision must be APPROVE or REJECT.");

        Complaint c = get(id);
        if (c.getStatus() == ComplaintStatus.APPROVED || c.getStatus() == ComplaintStatus.REJECTED)
            throw new IllegalStateException("This complaint has already been decided.");

        ComplaintStatus old = c.getStatus();
        c.setStatus(target);
        c.setReviewerComment(comment.trim());
        c = complaints.save(c);
        record(c, old, target, reviewer, comment.trim());
        return c;
    }

    public Complaint get(Long id) {
        return complaints.findById(id).orElseThrow(() -> new ComplaintNotFoundException("Complaint " + id + " not found"));
    }

    private void record(Complaint c, ComplaintStatus oldS, ComplaintStatus newS, String by, String note) {
        StatusHistory h = new StatusHistory();
        h.setComplaint(c);
        h.setOldStatus(oldS);
        h.setNewStatus(newS);
        h.setChangedBy(by);
        h.setChangedAt(LocalDateTime.now());
        h.setNote(note);
        history.save(h);
    }
}
