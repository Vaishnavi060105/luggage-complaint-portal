package com.college.luggageportal.controller;

import com.college.luggageportal.model.Complaint;
import com.college.luggageportal.model.ComplaintStatus;
import com.college.luggageportal.service.ComplaintService;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Reviewer pages (protected by Spring Security, role REVIEWER). */
@Controller
@RequestMapping("/reviewer/complaints")
public class ReviewerController {
    private final ComplaintService service;

    public ReviewerController(ComplaintService service) { this.service = service; }

    @GetMapping
    public String list(@RequestParam(required = false) String status, Model model) {
        ComplaintStatus filter = null;
        if (status != null && !status.isBlank()) {
            try { filter = ComplaintStatus.valueOf(status); } catch (IllegalArgumentException ignored) { }
        }
        model.addAttribute("complaints", service.list(filter));
        model.addAttribute("statuses", ComplaintStatus.values());
        model.addAttribute("selected", filter == null ? "" : filter.name());
        return "reviewer-list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Principal principal, Model model) {
        Complaint c = service.openForReview(id, principal.getName());
        model.addAttribute("complaint", c);
        model.addAttribute("history", service.historyOf(id));
        model.addAttribute("decided", c.getStatus() == ComplaintStatus.APPROVED || c.getStatus() == ComplaintStatus.REJECTED);
        return "reviewer-detail";
    }

    @PostMapping("/{id}/decision")
    public String decide(@PathVariable Long id, @RequestParam String decision, @RequestParam(required = false) String comment,
                         Principal principal, RedirectAttributes redirect) {
        try {
            service.decide(id, decision, comment, principal.getName());
            redirect.addFlashAttribute("message", "Decision saved.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/reviewer/complaints/" + id;
    }
}
