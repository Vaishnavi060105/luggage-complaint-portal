# Troubleshooting

| Problem | Cause | Fix |
|---|---|---|
| `Port 8080 already in use` | Tomcat and Jenkins clash | Move Jenkins to 9090 (`sudo systemctl edit jenkins`, set `JENKINS_PORT=9090`) |
| `Permission denied` copying WAR to Tomcat | Jenkins cannot write webapps | `sudo chgrp -R jenkins /opt/tomcat/webapps && sudo chmod -R 775 /opt/tomcat/webapps && sudo chmod g+s /opt/tomcat/webapps` |
| `permission denied ... docker.sock` in Jenkins | jenkins not in docker group | `sudo usermod -aG docker jenkins && sudo systemctl restart jenkins` |
| ChromeDriver / Chrome version mismatch | Old cached driver | Delete `~/.cache/selenium`, rerun; keep Chrome updated |
| `invalid target release: 17` | Wrong JDK | `java -version` must say 17; set `JDK17` in Jenkins Tools |
| Selenium: connection refused | App not running or wrong `-Dbase.url` | Start the app, check the URL in a browser |
| Jenkins waits forever in "Deploy to Test" | Tomcat not running or old WAR | `/opt/tomcat/bin/startup.sh`; check `/opt/tomcat/logs/catalina.out` |
| `bad interpreter: /bin/bash^M` | Windows line endings | `dos2unix scripts/*.sh` |
| Ansible `changed` on every run | A task is not idempotent | Check `copy` uses `force: false` and links use `state: link` |
| Health check fails after deploy | App still starting | Wait 30 s; `journalctl -u luggage-portal -n 50` |
