# Task 15: Final End-to-End Release, Documentation and Viva

## Objective
To run the complete DevOps workflow from Git commit to Jenkins build, Selenium quality gate, Docker deployment and Ansible provisioning, and to complete the documentation.

## 1. End-to-end workflow

| Stage | Tool | Result |
| --- | --- | --- |
| Commit and push | Git, GitHub | Code on the develop branch, release tagged v1.0.0 |
| Build and unit tests | Jenkins, Maven, JUnit | 22 unit tests passing |
| Quality gate | Selenium | 4 journeys passing; failed tests stop deployment |
| Deploy | Tomcat | Application on port 8082 |
| Package and publish | Docker, local registry | Versioned images (tags 7, 8, 10, latest) |
| Run | Docker container | Application on port 8083 |
| Provision | Ansible | Configured node with health check and rollback |

> Screenshot: Figure: Architecture of the portal and its deployment targets (see Reports/Task_15 PDF)

## 2. Final pipeline run

> Screenshot: Figure: Build #10: commit to container, Finished: SUCCESS (see Reports/Task_15 PDF)

> Screenshot: Figure: Versioned images in the registry and the running container (see Reports/Task_15 PDF)

> Screenshot: Figure: Final application on port 8083 (see Reports/Task_15 PDF)

## 3. Troubleshooting guide

| Problem | Cause | Fix |
| --- | --- | --- |
| release version 17 not supported | Default Java was a Java 21 runtime without compiler | Set JAVA_HOME to Java 17 |
| Could not resolve host | VM DNS dropped | Set DNS 8.8.8.8 and 1.1.1.1 |
| Jenkins archive found nothing | Project builds a WAR, not a JAR | Archive target/*.war |
| Permission denied in /opt/tomcat | Folder owned by the tomcat user | Add jenkins to the tomcat group, chmod 755 |
| Docker permission denied | User not in the docker group | usermod -aG docker, restart Jenkins |
| Port already in use | Several services on one VM | Fixed port plan: 8080, 8081, 8082, 8083, 8090, 5000 |
| apt NO_PUBKEY (Ansible) | Leftover GitHub CLI repository | Remove the repository file |

## 4. Limitations

- H2 embedded database: data is not suitable for production.
- Everything runs on a single VM.
- Local registry without TLS or authentication.
- Demo reviewer account and no real airline integration.
- Builds are started by polling or manually, not by webhooks.

## 5. Future enhancement plan

- PostgreSQL with persistent storage and backups.
- Email and SMS notifications to passengers.
- GitHub webhooks and Jenkins agents on separate nodes.
- Kubernetes deployment, HTTPS and secrets management.
- Monitoring and alerting (Prometheus and Grafana).

> Evidence to add: Ansible provisioning run, live demonstration video and viva presentation

## Conclusion
The complete workflow ran from a commit through build, test, a versioned Docker image and a fresh container. Troubleshooting notes, limitations and a future enhancement plan are documented.
