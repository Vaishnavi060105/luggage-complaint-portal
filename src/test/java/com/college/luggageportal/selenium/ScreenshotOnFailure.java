package com.college.luggageportal.selenium;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/** JUnit 5 extension: saves target/screenshots/<testName>.png when a test fails. */
public class ScreenshotOnFailure implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (context.getExecutionException().isEmpty()) return;
        Object test = context.getRequiredTestInstance();
        if (test instanceof BaseTest base && base.driver != null) {
            File shot = ((TakesScreenshot) base.driver).getScreenshotAs(OutputType.FILE);
            Path dir = Path.of("target", "screenshots");
            Files.createDirectories(dir);
            Files.copy(shot.toPath(), dir.resolve(context.getRequiredTestMethod().getName() + ".png"),
                       StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
