package com.college.luggageportal.repository;

import com.college.luggageportal.model.Reviewer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {
    Optional<Reviewer> findByUsername(String username);
}
