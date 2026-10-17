package com.college.luggageportal.repository;

import com.college.luggageportal.model.StatusHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {
    List<StatusHistory> findByComplaintIdOrderByIdAsc(Long complaintId);
}
