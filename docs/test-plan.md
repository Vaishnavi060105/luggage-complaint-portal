# Selenium Test Plan - Luggage Complaint Portal

## 1. Scope
Four critical user journeys covering the MVP: complaint submission, validation, status tracking and reviewer approval.

## 2. Test environment
| Item | Value |
|---|---|
| Tool | Selenium WebDriver 4 with JUnit 5 (Maven profile `selenium`) |
| Browser | Google Chrome (installed in the Ubuntu VM) |
| Application URL | http://localhost:8082/luggage-portal (passed as `-Dbase.url`) |
| Test class | `src/test/java/com/college/luggageportal/selenium/PortalJourneyTest.java` |
| Run command | `mvn -B test -Pselenium -Dtest=PortalJourneyTest -Dbase.url=http://localhost:8082/luggage-portal` |
| Reports | `target/surefire-reports` |
| Failure evidence | Screenshot saved in `target/screenshots` |

## 3. Test data
| Field | Value |
|---|---|
| Passenger name | Asha Verma |
| Email | asha.verma@example.com |
| Phone | 9876543210 |
| Flight number | AI202 |
| PNR | AB12CD |
| Bag tag | 1234567890 |
| Complaint type | DELAYED |
| Description | My suitcase did not arrive on flight AI202. |
| Reviewer login | reviewer1 / Review@123 (demo account) |

## 4. Test cases
| ID | Journey | Steps | Expected result | Assertion in code |
|---|---|---|---|---|
| J1 | Valid complaint shows a reference number | 1. Open /complaints/new. 2. Fill all fields with the test data. 3. Click Submit. | A confirmation page shows a reference number in the format LCP-YYYY-NNNNNN | Reference matches `LCP-\d{4}-\d{6}` |
| J2 | Empty form shows validation errors | 1. Open /complaints/new. 2. Click Submit without entering anything. | Several validation errors appear and nothing is saved | At least 5 `.error` elements are visible |
| J3 | Tracking shows the status | 1. Submit a valid complaint and note the reference. 2. Open /track. 3. Enter the reference and click Track. | The status of the new complaint is displayed | Status text equals `SUBMITTED` |
| J4 | Reviewer approves a complaint | 1. Submit a valid complaint. 2. Log in at /login as reviewer1. 3. Open the complaint from the list. 4. Choose APPROVE, add a comment, submit. | The complaint status changes to APPROVED | Status text equals `APPROVED` |

## 5. Pass and fail criteria
- Pass: all four tests report `Failures: 0, Errors: 0` and the build ends with `BUILD SUCCESS`.
- Fail: any assertion fails or an element is not found. A screenshot of the page is saved in `target/screenshots`.

## 6. Execution record
| Run | Date | Result |
|---|---|---|
| Local run 1 | 2026-10-21 | Passed |
