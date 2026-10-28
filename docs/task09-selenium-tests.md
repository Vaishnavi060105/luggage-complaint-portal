# Task 09: Selenium Test Design and Local Execution

## Objective
To identify critical user journeys, write Selenium WebDriver tests with assertions and test data, save screenshots on failure and run the suite locally through Maven.

## 1. Test plan

| ID | Journey | Steps | Expected result |
| --- | --- | --- | --- |
| J1 | Valid complaint shows a reference number | Open /complaints/new, fill all fields, submit | Reference in the format LCP-YYYY-NNNNNN |
| J2 | Empty form shows validation errors | Open /complaints/new, submit empty | At least 5 validation errors |
| J3 | Tracking shows the status | Submit a complaint, open /track, enter the reference | Status is SUBMITTED |
| J4 | Reviewer approves a complaint | Submit, log in as reviewer, open complaint, choose APPROVE | Status becomes APPROVED |

Test data: Asha Verma, asha.verma@example.com, 9876543210, flight AI202, PNR AB12CD, bag tag 1234567890, type DELAYED. Tool: Selenium WebDriver 4 with JUnit 5, class PortalJourneyTest.

## 2. Local execution through Maven

```
mvn -B test -Pselenium -Dtest=PortalJourneyTest -Dbase.url=http://localhost:8082/luggage-portal
```

> Screenshot: Figure: Tests run: 4, Failures: 0, Errors: 0 and BUILD SUCCESS (see Reports/Task_09 PDF)

> Screenshot: Figure: Surefire reports and the PortalJourneyTest report (see Reports/Task_09 PDF)

## 3. Failure screenshot mechanism

ScreenshotOnFailure.java is a JUnit 5 extension that saves target/screenshots/<testName>.png when a test fails.

## 4. Deliberate defect to prove the mechanism

> Screenshot: Figure: Expected status changed from SUBMITTED to WRONG in journey J3 (see Reports/Task_09 PDF)

> Screenshot: Figure: Demonstration script: passing run, then the deliberate defect (see Reports/Task_09 PDF)

```
===== 1/3 PASSING RUN =====
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
===== 2/3 DELIBERATE DEFECT =====
Tests run: 4, Failures: 1, Errors: 0, Skipped: 0
BUILD FAILURE
--- failure screenshots ---
j3_trackingShowsStatus.png
===== 3/3 FIX AND RERUN =====
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

> Screenshot: Figure: Saved failure screenshot in target/screenshots (see Reports/Task_09 PDF)

> Screenshot: Figure: j3_trackingShowsStatus.png captured automatically at the failure (see Reports/Task_09 PDF)

## Conclusion
Four critical journeys were automated with Selenium and run through Maven. A screenshot is saved automatically when a test fails, and the suite passed again after the defect was removed.
