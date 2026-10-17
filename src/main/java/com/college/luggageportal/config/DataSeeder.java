package com.college.luggageportal.config;

import com.college.luggageportal.model.Reviewer;
import com.college.luggageportal.repository.ReviewerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Creates two dummy reviewers on startup (demo data only). */
@Component
public class DataSeeder implements CommandLineRunner {
    private final ReviewerRepository reviewers;
    private final PasswordEncoder encoder;

    public DataSeeder(ReviewerRepository reviewers, PasswordEncoder encoder) {
        this.reviewers = reviewers;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (reviewers.count() > 0) return;
        for (String name : new String[] {"reviewer1", "reviewer2"}) {
            Reviewer r = new Reviewer();
            r.setUsername(name);
            r.setPasswordHash(encoder.encode("Review@123"));
            reviewers.save(r);
        }
    }
}
