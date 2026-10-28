# Task 13: Configuration Management Script

## Objective
To identify the server prerequisites (packages, users, folders, files, ports, services) and write an Ansible inventory and playbook that configures the target node.

## 1. Configuration specification

| Category | Requirement |
| --- | --- |
| Packages | openjdk-17-jre-headless, curl |
| User | portal (system user, shell /usr/sbin/nologin, no home) |
| Folders | /opt/luggage-portal, /opt/luggage-portal/releases, /opt/luggage-portal/uploads, /var/log/luggage-portal |
| Files | releases/portal-<version>.war, symlink current.war, /etc/systemd/system/luggage-portal.service |
| Port | 8090 (8080 is used by Jenkins) |
| Service | luggage-portal enabled at boot and running; restarted when the release or unit changes |
| Health check | GET /actuator/health returns 200 and UP |

## 2. Inventory

```
[portal]
localhost ansible_connection=local
```

## 3. Playbook tasks (ansible/site.yml)

- Install Java 17 and curl (apt).
- Create the service user portal.
- Create the application and log folders with the right owner and mode.
- Copy the WAR to releases/ (force=false keeps older releases, which makes rollback safe).
- Point current.war at the selected app_version.
- Install the systemd unit and notify the handler to restart the service.
- Enable and start the service, flush handlers, then run the health check with retries.

## 4. First execution

```
ansible-playbook -i inventory.ini site.yml -K -e app_port=8090
```

The first run stopped at the first task because of an old GitHub CLI apt repository without a signing key:

```
TASK [Install Java 17 and curl]
fatal: [localhost]: FAILED! ... GPG error: https://cli.github.com/packages stable InRelease:
NO_PUBKEY 5612B36462313325 ... The repository is not signed.
```

Fix: the unused repository was removed (sudo rm /etc/apt/sources.list.d/github-cli.list), apt update was repeated and the playbook was run again.

> Evidence to add: PLAY RECAP of the successful run (failed=0), the service status and the health check output

## Conclusion
The server prerequisites were specified and an Ansible playbook was written to configure the portal node. The first run exposed an apt repository key problem, which was identified and fixed.
