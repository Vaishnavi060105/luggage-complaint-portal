# Task 1: Problem Definition and Scope

**Project:** Luggage Complaint Portal  **Student:** Vaishnavi Khopkar  **Roll No.:** ________

## 1. Problem statement
Passengers whose luggage is delayed, damaged or lost report it today through phone calls, emails or paper forms at the airport baggage desk. These channels give no unique reference number, no way to check progress, no standard validation of the data submitted, and no audit trail of who decided what. Passengers keep calling for updates, and staff handle scattered, incomplete information by hand.
The **Luggage Complaint Portal** is a web application where passengers submit a complaint with supporting details, reviewers approve or reject it, and passengers track the status online with a reference number.
The project is delivered through an automated DevOps pipeline (Git, Jenkins, Selenium, Docker, Ansible) so every change is built, tested and deployed repeatably.

## 2. Background (real-time need)
Airlines record mishandled baggage with a *Property Irregularity Report (PIR)*. Passengers usually fill it at the airport or call a helpline. *(Add 2-3 references here: an airline's baggage-claim page and a PIR description, with URLs and the date you read them.)*

## 3. Target users
| User | Role | Needs |
|---|---|---|
| Passenger | Primary | Submit a complaint, upload a document, track status |
| Reviewer (airline staff) | Primary | See the queue, approve or reject with a comment |
| Administrator | Secondary (out of MVP) | Manage reviewer accounts |

## 4. Existing pain points
| # | Pain point | Impact |
|---|---|---|
| 1 | No unique reference number | Passengers cannot quote their case |
| 2 | No status visibility | Repeated calls and emails |
| 3 | Paper forms lost or incomplete | Delay and rework |
| 4 | No input validation | Wrong PNR, missing bag tag |
| 5 | Slow manual review | Long resolution time |
| 6 | No audit trail | No accountability for decisions |

## 5. Stakeholders
| Stakeholder | Interest | Influence |
|---|---|---|
| Passengers | Fast, trackable handling | Medium |
| Airline baggage desk | Less manual work | High |
| Reviewers / supervisors | Clear queue, easy decisions | High |
| Airport management | Service quality | High |
| Development team | Working MVP and pipeline | High |
| Faculty / evaluator | Correct DevOps practice and evidence | High |
| IT operations | Stable deployment, easy rollback | Medium |

## 6. Objectives and measurable success criteria
| ID | Objective | Success criterion |
|---|---|---|
| O1 | Fast submission | Valid complaint submitted in under 2 minutes |
| O2 | Unique tracking | 100% of complaints get a unique reference number |
| O3 | Data quality | 100% of invalid submissions rejected with a clear error |
| O4 | Review workflow | Approve/reject in 3 clicks or fewer; status updates instantly |
| O5 | Transparency | Status shown by reference in under 3 seconds |
| O6 | Automated build | Every commit triggers a Jenkins build |
| O7 | Quality gate | 100% Selenium tests pass before deployment; a failure blocks release |
| O8 | Fast delivery | Commit-to-deployed under 10 minutes |
| O9 | Repeatable provisioning | Ansible re-run shows `changed=0` |
| O10 | Recoverability | Rollback to previous release in under 5 minutes |

## 7. Constraints and assumptions
**Constraints:** one academic semester; one developer; free/open-source tools only; local VM, no cloud or public domain; mandatory tools (Git/GitHub, Jenkins, Maven, Selenium, Docker, Ansible); dummy data only; basic authentication only; no payments, notifications or airline-system integration.
**Assumptions:** users have a modern browser; reviewers are pre-created; one Tomcat server is available.

## 8. Frozen MVP scope (15 tasks)
| # | MVP task |
|---|---|
| 1 | Project and repository setup |
| 2 | Complaint data model |
| 3 | Complaint submission form |
| 4 | Server-side validation (PNR, email, phone, required fields) |
| 5 | Document upload (JPG/PNG/PDF, max 2 MB) |
| 6 | Unique reference number |
| 7 | Status tracking page |
| 8 | Reviewer login |
| 9 | Pending complaints list with filter |
| 10 | Approve or reject with comment |
| 11 | Status history and timestamps |
| 12 | Friendly error pages |
| 13 | Health endpoint `/actuator/health` |
| 14 | Automated Selenium tests |
| 15 | Documentation and deployment scripts |

**Out of scope:** payments, email/SMS notifications, airline-system integration, mobile app, multi-language, analytics, admin screens.

## 9. Approval
Scope approved by: ____________________ (Faculty)   Date: ________
Prepared by: Vaishnavi Khopkar (Roll No. ________)

## References
1. ______________________ (URL, accessed date)
2. ______________________ (URL, accessed date)
