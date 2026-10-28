# Task 14: Automated Provisioning and Reliability Validation

## Objective
To provision a clean node with Ansible, deploy the application, rerun the automation to show idempotency, run a health check and test rollback and recovery to the previous stable release.

## 1. Provisioning design

- Clean node: service, unit file, folders and the portal user removed before the run.
- Provisioning: ansible-playbook installs Java, the user, folders, the release, the systemd service and runs the health check.
- Idempotency: a second run of the same playbook should report changed=0.
- Rollback: running the playbook with the previous app_version repoints current.war and restarts the service.
- Recovery: the unit uses Restart=always, so systemd restarts the service if it is killed.

## 2. Commands used

```
AP="ansible-playbook -i ansible/inventory.ini ansible/site.yml -e app_port=8090"
$AP -e app_version=1.0.0     # provision clean node
$AP -e app_version=1.0.0     # idempotency: expect changed=0
$AP -e app_version=1.1.0     # new release
$AP -e app_version=1.0.0     # rollback to previous stable release
sudo systemctl kill -s KILL luggage-portal   # recovery test
curl http://localhost:8090/actuator/health
```

## 3. Evidence

> Evidence to add: Provisioning run on the clean node (PLAY RECAP)

> Evidence to add: Second run showing changed=0 (idempotency)

> Evidence to add: Health check output {"status":"UP"}

> Evidence to add: Rollback: actuator/info before and after, and the releases folder listing

> Evidence to add: Recovery: service active again after being killed

## Conclusion
The provisioning playbook installs the application as a versioned, health-checked systemd service. Rerunning it changes nothing, and a rollback is done by running it with the previous version.
