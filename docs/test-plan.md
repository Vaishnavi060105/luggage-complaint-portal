# Test Plan (Task 9)

Environment: headless Chrome, base URL from `-Dbase.url`. Run: `mvn test -Pselenium -Dbase.url=http://localhost:8081`
Failure screenshots: `target/screenshots/<testName>.png`. Reports: `target/surefire-reports/`.

| ID | Journey | Test data | Steps | Expected result |
|---|---|---|---|---|
| J1 | Submit valid complaint | Asha Verma, asha.verma@example.com, 9876543210, AI202, AB12CD, 1234567890, DELAYED | Open form, fill, submit | Confirmation shows `LCP-YYYY-NNNNNN` |
| J2 | Empty form | none | Open form, click submit | At least 5 validation errors shown |
| J3 | Track status | Reference from J1 | Open /track, enter reference, Track | Status SUBMITTED shown |
| J4 | Reviewer approves | reviewer1 / Review@123 | Login, open complaint, choose Approve, comment, save | Status becomes APPROVED |

Unit tests (run in every build): form validation, file validation, service workflow.
