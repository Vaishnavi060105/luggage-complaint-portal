#!/usr/bin/env bash
# Creates a clean Git history with meaningful commits (Task 4), then a develop branch.
# Run ONCE from the project root:  bash scripts/initial-commits.sh
set -e
P=src/main/java/com/college/luggageportal
git init -q
git checkout -q -B main

git add pom.xml .gitignore .gitattributes README.md $P/LuggagePortalApplication.java src/main/resources/application*.properties
git commit -q -m "chore: initial Spring Boot skeleton"

git add $P/model $P/repository $P/dto $P/exception $P/service $P/controller/HomeController.java $P/controller/ComplaintController.java \
        src/main/resources/templates/fragments.html src/main/resources/templates/index.html src/main/resources/templates/complaint-*.html \
        src/main/resources/templates/track.html src/main/resources/templates/error src/main/resources/static
git commit -q -m "feat: complaint submission, validation, upload and tracking"

git add $P/config $P/controller/ReviewerController.java src/main/resources/templates/login.html src/main/resources/templates/reviewer-*.html
git commit -q -m "feat: reviewer login, review list and approve/reject workflow"

git add src/test
git commit -q -m "test: add unit tests and Selenium journeys"

git add Jenkinsfile Dockerfile .dockerignore docker ansible scripts
git commit -q -m "ci: add Jenkinsfile, Dockerfile, Ansible playbook and helper scripts"

git add -A
git commit -q -m "docs: add project documentation and GitHub templates"

git update-index --chmod=+x scripts/*.sh
git commit -q -m "chore: mark helper scripts executable" || true
git branch -f develop main
git log --oneline
echo "Done. Next: git remote add origin git@github.com:<user>/luggage-complaint-portal.git && git push -u origin main develop"
