# Task 10: Continuous Testing in Jenkins

## Objective
To integrate the Selenium suite into Jenkins, publish test reports, stop deployment when tests fail, and correct a deliberately introduced defect and rerun the pipeline.

## 1. Pipeline with a Selenium quality gate

Stages: Checkout, Build, Unit Test, Package, Deploy to Test, Selenium Tests, Deploy Final. Selenium runs against a separate test deployment (/luggage-test); Deploy Final runs only when every test passed.

## 2. Successful run (build #4)

> Screenshot: Figure: Build #4 stages: all stages green including Selenium Tests and Deploy Final (see Reports/Task_10 PDF)

> Screenshot: Figure: Jenkins test report: 26 tests passing (22 unit and 4 Selenium) (see Reports/Task_10 PDF)

## 3. Defect introduced: pipeline fails (build #5)

The id of the submit button in the complaint form was changed from submit to submitX, so the Selenium journeys could no longer find the button.

> Screenshot: Figure: Build #5: Selenium Tests failed and Deploy Final was skipped (see Reports/Task_10 PDF)

> Screenshot: Figure: Console output: Deploy Final skipped due to earlier failures, Finished: FAILURE (see Reports/Task_10 PDF)

> Screenshot: Figure: Job status after the failure with the test result trend (see Reports/Task_10 PDF)

## 4. Defect correction commit

> Screenshot: Figure: Commits: defect introduced (a28f58b) and fix: restore submit button id (34c91d3) (see Reports/Task_10 PDF)

## 5. Successful rerun (build #6)

> Screenshot: Figure: Build #6 stages: all green after the fix (see Reports/Task_10 PDF)

> Screenshot: Figure: Job page showing the recovery: #4 passed, #5 failed, #6 passed (see Reports/Task_10 PDF)

> Screenshot: Figure: Stages overview of all builds (see Reports/Task_10 PDF)

## Conclusion
Selenium tests were added to the Jenkins pipeline as a quality gate. The deliberate defect stopped deployment, and after the correction commit the pipeline passed again.
