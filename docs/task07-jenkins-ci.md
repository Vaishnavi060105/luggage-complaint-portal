# Task 07: Jenkins Installation and Continuous Integration Job

## Objective
To install and configure Jenkins, connect the GitHub repository, create a Maven build job triggered by commit polling, and archive the build artefact.

## 1. Jenkins installed

> Screenshot: Figure: Jenkins dashboard after installation (Jenkins 2.580.1) (see Reports/Task_07 PDF)

## 2. Job configuration

- Job type: Freestyle project (luggage-complaint-portal).
- Source: https://github.com/Vaishnavi060105/luggage-complaint-portal.git, branch */develop.
- Trigger: Poll SCM with schedule H/2 * * * *.
- Build step: Execute shell using Java 17, then mvn clean package -DskipTests.
- Post-build: Archive the artifacts target/*.war.

```
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
mvn clean package -DskipTests
```

## 3. Failed builds and their fixes (troubleshooting)

> Screenshot: Build #1: release version 17 not supported (default Java had no compiler); fixed by setting JAVA_HOME to Java 17 (see Reports/Task_07 PDF)

> Screenshot: Build #2: Could not resolve host github.com (VM DNS); fixed by setting a public DNS (see Reports/Task_07 PDF)

> Screenshot: Build #3: BUILD SUCCESS but target/*.jar not found; the project builds a WAR, so the pattern was changed to target/*.war (see Reports/Task_07 PDF)

## 4. Successful build and archived artefact

> Screenshot: Figure: Console output of build #4 ending in BUILD SUCCESS (see Reports/Task_07 PDF)

> Screenshot: Figure: Job page with builds #1 to #4 and the archived luggage-portal.war (see Reports/Task_07 PDF)

## 5. Trigger evidence

> Screenshot: Figure: Build #5 started by an SCM change after a commit to develop (see Reports/Task_07 PDF)

## Conclusion
Jenkins was installed and a Maven CI job was connected to GitHub, started automatically by commits through SCM polling, and archived the WAR artefact. The failed builds were diagnosed and fixed.
