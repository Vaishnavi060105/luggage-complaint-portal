# Task 04: Git and GitHub Repository Initialization

## Objective
To create a GitHub repository with README, .gitignore, folder structure, issue templates and branch rules, and to commit the initial application skeleton with meaningful messages.

## 1. GitHub repository

Repository URL: https://github.com/Vaishnavi060105/luggage-complaint-portal

> Screenshot: Figure: GitHub CLI login, remote configuration and commit log (see Reports/Task_04 PDF)

## 2. Folder structure on GitHub

> Screenshot: Figure: Repository on GitHub (develop branch) with .github, ansible, docker, docs, scripts and src folders (see Reports/Task_04 PDF)

## 3. Initial commits

```
164416d docs: add project documentation and GitHub templates
a4cfd35 ci: add Jenkinsfile, Dockerfile, Ansible playbook and helper scripts
adc0cd8 test: add unit tests and Selenium journeys
9400a92 feat: reviewer login, review list and approve/reject workflow
8f01209 feat: complaint submission, validation, upload and tracking
1479a10 chore: initial Spring Boot skeleton
```

## 4. Branch policy

- main: stable, released code (tagged).
- develop: integration branch for finished features.
- feature/<name>: one branch per feature, merged into develop through a pull request.
- Commit messages use a prefix: feat, fix, docs, test, ci, chore.

> Evidence to add: README.md and the issue templates (.github folder) on GitHub

## Conclusion
The GitHub repository was created and the application skeleton was committed with meaningful messages, a clear folder structure, templates and a branch policy.
