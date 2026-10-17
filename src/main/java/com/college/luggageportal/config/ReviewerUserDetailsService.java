package com.college.luggageportal.config;

import com.college.luggageportal.repository.ReviewerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** Loads reviewers from the database for Spring Security. */
@Service
public class ReviewerUserDetailsService implements UserDetailsService {
    private final ReviewerRepository reviewers;

    public ReviewerUserDetailsService(ReviewerRepository reviewers) { this.reviewers = reviewers; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return reviewers.findByUsername(username)
            .map(r -> User.withUsername(r.getUsername()).password(r.getPasswordHash()).roles("REVIEWER").build())
            .orElseThrow(() -> new UsernameNotFoundException("Unknown reviewer"));
    }
}
