package com.college.luggageportal.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** Validates and stores uploaded documents under a random file name. */
@Service
public class FileStorageService {
    static final long MAX_BYTES = 2L * 1024 * 1024;
    private static final Set<String> ALLOWED = Set.of("jpg", "jpeg", "png", "pdf");
    private final Path uploadDir;

    public FileStorageService(@Value("${app.upload-dir:./uploads}") String dir) {
        this.uploadDir = Paths.get(dir).toAbsolutePath().normalize();
    }

    /** Returns an error message, or null when the file is fine (or absent). */
    public String validate(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        if (file.getSize() > MAX_BYTES) return "Attachment must be 2 MB or smaller.";
        if (!ALLOWED.contains(extension(file.getOriginalFilename()))) return "Only JPG, PNG or PDF files are allowed.";
        return null;
    }

    /** Saves the file with a random name and returns that name. */
    public String store(MultipartFile file) throws IOException {
        Files.createDirectories(uploadDir);
        String name = UUID.randomUUID() + "." + extension(file.getOriginalFilename());
        Files.copy(file.getInputStream(), uploadDir.resolve(name), StandardCopyOption.REPLACE_EXISTING);
        return name;
    }

    private String extension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase(Locale.ROOT);
    }
}
