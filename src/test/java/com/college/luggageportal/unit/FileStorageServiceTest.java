package com.college.luggageportal.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.college.luggageportal.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class FileStorageServiceTest {
    private final FileStorageService service = new FileStorageService("target/test-uploads");

    @Test
    void acceptsPdf() {
        assertNull(service.validate(new MockMultipartFile("attachment", "bag.pdf", "application/pdf", new byte[100])));
    }

    @Test
    void rejectsExeFiles() {
        assertNotNull(service.validate(new MockMultipartFile("attachment", "virus.exe", "application/octet-stream", new byte[100])));
    }

    @Test
    void rejectsFilesOverTwoMegabytes() {
        byte[] big = new byte[2 * 1024 * 1024 + 1];
        assertNotNull(service.validate(new MockMultipartFile("attachment", "big.png", "image/png", big)));
    }

    @Test
    void missingFileIsAllowed() {
        assertNull(service.validate(null));
    }
}
