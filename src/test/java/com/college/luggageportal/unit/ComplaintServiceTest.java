package com.college.luggageportal.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.college.luggageportal.model.Complaint;
import com.college.luggageportal.model.ComplaintStatus;
import com.college.luggageportal.service.ComplaintService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/** Full workflow against the in-memory database. */
@SpringBootTest
class ComplaintServiceTest {
    @Autowired
    private ComplaintService service;

    @Test
    void complaintMovesThroughWorkflow() {
        Complaint c = service.submit(ComplaintFormValidationTest.validForm());
        assertTrue(c.getReferenceNo().startsWith("LCP-"));
        assertEquals(ComplaintStatus.SUBMITTED, c.getStatus());

        c = service.openForReview(c.getId(), "reviewer1");
        assertEquals(ComplaintStatus.UNDER_REVIEW, c.getStatus());

        c = service.decide(c.getId(), "APPROVE", "Looks fine", "reviewer1");
        assertEquals(ComplaintStatus.APPROVED, c.getStatus());
        assertEquals(3, service.historyOf(c.getId()).size());
    }

    @Test
    void decisionNeedsAComment() {
        Complaint c = service.submit(ComplaintFormValidationTest.validForm());
        Long id = c.getId();
        assertThrows(IllegalArgumentException.class, () -> service.decide(id, "REJECT", " ", "reviewer1"));
    }

    @Test
    void referenceNumbersAreUnique() {
        Complaint a = service.submit(ComplaintFormValidationTest.validForm());
        Complaint b = service.submit(ComplaintFormValidationTest.validForm());
        assertNotEquals(a.getReferenceNo(), b.getReferenceNo());
    }
}
