package com.college.luggageportal.controller;

import com.college.luggageportal.dto.ComplaintForm;
import com.college.luggageportal.model.Complaint;
import com.college.luggageportal.model.ComplaintType;
import com.college.luggageportal.service.ComplaintService;
import com.college.luggageportal.service.FileStorageService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/** Passenger-facing pages: submit and track. */
@Controller
public class ComplaintController {
    private final ComplaintService service;
    private final FileStorageService storage;

    public ComplaintController(ComplaintService service, FileStorageService storage) {
        this.service = service;
        this.storage = storage;
    }

    @GetMapping("/complaints/new")
    public String form(Model model) {
        model.addAttribute("complaintForm", new ComplaintForm());
        model.addAttribute("types", ComplaintType.values());
        return "complaint-form";
    }

    @PostMapping("/complaints")
    public String submit(@Valid @ModelAttribute("complaintForm") ComplaintForm form, BindingResult result, Model model) {
        String fileError = storage.validate(form.getAttachment());
        if (fileError != null) result.rejectValue("attachment", "attachment.invalid", fileError);
        if (result.hasErrors()) {
            model.addAttribute("types", ComplaintType.values());
            return "complaint-form";
        }
        Complaint c = service.submit(form);
        // Redirect after POST so a browser refresh does not submit twice.
        return "redirect:/complaints/success?ref=" + c.getReferenceNo();
    }

    @GetMapping("/complaints/success")
    public String success(@RequestParam String ref, Model model) {
        model.addAttribute("ref", ref);
        return "complaint-success";
    }

    @GetMapping("/track")
    public String track(@RequestParam(required = false) String ref, Model model) {
        if (ref != null && !ref.isBlank()) {
            Optional<Complaint> found = service.findByReference(ref.trim());
            if (found.isPresent()) {
                model.addAttribute("complaint", found.get());
                model.addAttribute("history", service.historyOf(found.get().getId()));
            } else {
                model.addAttribute("notFound", true);
            }
        }
        model.addAttribute("ref", ref);
        return "track";
    }
}
