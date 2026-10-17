# Ansible (Tasks 13 and 14)

## Server specification
| Item | Value |
|---|---|
| Packages | openjdk-17-jre-headless, curl |
| User | `portal` (system user, no login) |
| Folders | /opt/luggage-portal, /opt/luggage-portal/releases, /opt/luggage-portal/uploads, /var/log/luggage-portal |
| Files | releases/portal-<version>.war, current.war (symlink), /etc/systemd/system/luggage-portal.service |
| Port | 8080 (app), 22 (SSH) |
| Service | `luggage-portal` (systemd, enabled and started) |

## Run
```bash
sudo apt install -y ansible
mvn clean package -DskipTests                       # builds target/luggage-portal.war
ansible -i ansible/inventory.ini portal -m ping
ansible-playbook -i ansible/inventory.ini ansible/site.yml -K | tee docs/evidence/task13-first-run.log
```
(`-K` asks for the sudo password. Drop it if sudo is passwordless.)

## Idempotency (Task 14)
Run the same command again. The recap must show `changed=0`:
```bash
ansible-playbook -i ansible/inventory.ini ansible/site.yml -K | tee docs/evidence/task14-idempotency.log
```

## Release and rollback
```bash
# Deploy a new release: build it, then give it a NEW version number
mvn clean package -DskipTests
ansible-playbook -i ansible/inventory.ini ansible/site.yml -K -e app_version=1.1.0

# Roll back: the old WAR is still in releases/, so only the symlink switches
ansible-playbook -i ansible/inventory.ini ansible/site.yml -K -e app_version=1.0.0
curl http://localhost:8080/actuator/health
```
Note: an existing release file is never overwritten (`force: false`), so always use a new version number for new code.
