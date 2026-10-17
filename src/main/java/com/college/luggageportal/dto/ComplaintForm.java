package com.college.luggageportal.dto;

import com.college.luggageportal.model.ComplaintType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/** Form object for the submission page. Bean Validation rules live here. */
@Getter
@Setter
public class ComplaintForm {
    @NotBlank(message = "Passenger name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String passengerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\d{10}", message = "Phone must be exactly 10 digits")
    private String phone;

    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "[A-Z]{2}\\d{1,4}", message = "Flight number must look like AI202")
    private String flightNo;

    @NotBlank(message = "PNR is required")
    @Pattern(regexp = "[A-Z0-9]{6}", message = "PNR must be 6 uppercase letters or digits")
    private String pnr;

    @NotBlank(message = "Bag tag is required")
    @Pattern(regexp = "\\d{10}", message = "Bag tag must be exactly 10 digits")
    private String bagTag;

    @NotNull(message = "Select a complaint type")
    private ComplaintType type;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be 10 to 500 characters")
    private String description;

    /** Optional. Checked by FileStorageService (jpg/png/pdf, max 2 MB). */
    private MultipartFile attachment;
}
