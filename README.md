
# Luggage Complaint Portal - Version A


A small Spring Boot web app where passengers submit and track luggage complaints and reviewers approve or reject them. Delivered through a full DevOps pipeline: Git -> Jenkins -> Selenium -> Docker -> Ansible.

## Tech stack
Java 17, Spring Boot 3.3, Thymeleaf, Spring Data JPA, H2, Spring Security, Maven, Tomcat 10.1, JUnit 5, Selenium 4, Jenkins, Docker, Ansible.

## Features
Complaint form with validation and document upload; unique reference numbers; status tracking with history; reviewer login, list, filter and approve/reject; custom error pages; `/actuator/health`.

## Folder structure
```
src/main/java      application code (controller, service, repository, model, dto, config, exception)
src/main/resources templates, css, application*.properties
src/test/java      unit tests and Selenium journeys
Jenkinsfile        CI/CD pipeline
Dockerfile         container image
ansible/           inventory.ini, site.yml, README
docker/            container lifecycle commands
scripts/           helper scripts
docs/              task documents, diagrams, evidence
```

## Prerequisites
Java 17, Maven 3.6.3+, Chrome (for Selenium), Git. Optional: Tomcat 10.1, Jenkins, Docker, Ansible.

## Run locally
```bash
mvn spring-boot:run          # http://localhost:8081
```
Reviewer logins (dummy): `reviewer1` / `Review@123`, `reviewer2` / `Review@123`

## Tests
```bash
mvn test                                              # unit tests
mvn test -Pselenium -Dbase.url=http://localhost:8081  # Selenium (app must be running)
```
Failure screenshots go to `target/screenshots/`.

## Build the WAR
```bash
mvn clean package            # target/luggage-portal.war
java -jar target/luggage-portal.war
```
Environment label (shown in the banner): `mvn package -Dapp.env=STAGING`

## Docker
```bash
docker build -t luggage-portal:1.0 .
docker run -d --name portal -p 8082:8080 luggage-portal:1.0
```
See `docker/docker-commands.md`.

## Ansible
See `ansible/README.md`.

## Jenkins pipeline
Stages: Checkout, Build, Unit Test, Package, Deploy to Test, Selenium Tests, Deploy Final, Docker Build, Docker Push, Docker Deploy. Parameter `DEPLOY_ENV` (dev/staging). A failing test stops the pipeline before any deployment. Docker Hub credentials ID: `dockerhub`.

## Branch rules
`main` stable and tagged; `develop` integration; `feature/<name>`; `bugfix/<name>`; `release/<version>`. All work goes through pull requests into `develop`.

## Commit convention
`feat:` new feature, `fix:` bug fix, `docs:` documentation, `test:` tests, `ci:` pipeline, `chore:` maintenance.

## Default status label
Default complaint status label: Submitted


