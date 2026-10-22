# Server Configuration Specification - Luggage Complaint Portal

Target node: Ubuntu 22.04 server (managed with Ansible, `ansible/site.yml`).

| Category | Requirement | Ansible task |
|---|---|---|
| Packages | openjdk-17-jre-headless, curl | Install Java 17 and curl |
| User | `portal` (system user, shell /usr/sbin/nologin, no home) | Create service user |
| Folders | /opt/luggage-portal, /opt/luggage-portal/releases, /opt/luggage-portal/uploads (owner portal, 0755) | Create application folders |
| Log folder | /var/log/luggage-portal (owner portal) | Create log folder |
| Files | /opt/luggage-portal/releases/portal-<version>.war, symlink current.war, /etc/systemd/system/luggage-portal.service | Copy release, point current.war, install systemd unit |
| Port | 8090 (HTTP, passed as `app_port`; 8080 is used by Jenkins) | systemd ExecStart argument |
| Service | `luggage-portal` enabled at boot, running, restarted when the release or unit changes | Enable and start the service |
| Health check | GET /actuator/health returns 200 and UP | Health check task |
| Rollback | Run again with the previous `app_version`; current.war is repointed | Point current.war |
