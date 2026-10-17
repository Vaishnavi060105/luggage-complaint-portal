package com.college.luggageportal.repository;

import com.college.luggageportal.model.Complaint;
import com.college.luggageportal.model.ComplaintStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Optional<Complaint> findByReferenceNo(String referenceNo);
    List<Complaint> findAllByOrderByIdDesc();
    List<Complaint> findByStatusOrderByIdDesc(ComplaintStatus status);
}
