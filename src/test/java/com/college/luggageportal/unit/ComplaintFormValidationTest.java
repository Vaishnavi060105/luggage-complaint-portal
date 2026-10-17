package com.college.luggageportal.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.college.luggageportal.dto.ComplaintForm;
import com.college.luggageportal.model.ComplaintType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ComplaintFormValidationTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static ComplaintForm validForm() {
        ComplaintForm f = new ComplaintForm();
        f.setPassengerName("Asha Verma");
        f.setEmail("asha@example.com");
        f.setPhone("9876543210");
        f.setFlightNo("AI202");
        f.setPnr("AB12CD");
        f.setBagTag("1234567890");
        f.setType(ComplaintType.LOST);
        f.setDescription("Black suitcase missing after landing.");
        return f;
    }

    @Test
    void validFormHasNoViolations() {
        assertTrue(validator.validate(validForm()).isEmpty());
    }

    @Test
    void invalidPnrIsRejected() {
        ComplaintForm f = validForm();
        f.setPnr("abc");
        Set<ConstraintViolation<ComplaintForm>> v = validator.validate(f);
        assertEquals(1, v.size());
    }

    @Test
    void invalidPhoneAndEmailAreRejected() {
        ComplaintForm f = validForm();
        f.setPhone("12345");
        f.setEmail("not-an-email");
        assertEquals(2, validator.validate(f).size());
    }

    @Test
    void shortDescriptionIsRejected() {
        ComplaintForm f = validForm();
        f.setDescription("short");
        assertFalse(validator.validate(f).isEmpty());
    }
}
