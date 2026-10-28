# Task 11: Docker Image and Container Lifecycle

## Objective
To create a Dockerfile, build and tag the image, run the container with port mapping, inspect logs, stop, restart and remove it, and document the full container lifecycle.

## 1. Dockerfile

```
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/luggage-portal.war app.war
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.war", "--server.port=8080"]
```

## 2. Build and tag the image

```
$ docker build -t luggage-portal:1.0 -t luggage-portal:latest .
Successfully built cdec67bfc9fe
Successfully tagged luggage-portal:1.0
Successfully tagged luggage-portal:latest

$ docker images luggage-portal
luggage-portal:1.0      cdec67bfc9fe   534MB (content 161MB)
luggage-portal:latest   cdec67bfc9fe   534MB (content 161MB)

$ docker image inspect luggage-portal:1.0 --format "ID: {{.Id}} Size: {{.Size}}"
ID: sha256:cdec67bfc9fe...  Size: 160530746
```

## 3. Run the container with port mapping

```
$ docker run -d --name portal -p 8083:8080 luggage-portal:1.0
$ docker ps
d9e370e54043  luggage-portal:1.0  Up (health: starting)  0.0.0.0:8083->8080/tcp  portal
```

## 4. Logs, health and resource use

```
$ docker logs --tail 15 portal
... Tomcat started on port 8080 (http) with context path '/'
... Started LuggagePortalApplication in 4.714 seconds

$ curl -I http://localhost:8083/
HTTP/1.1 200

$ docker stats --no-stream portal
CPU 0.50%   MEM 303.8MiB / 3.824GiB   PIDS 39
```

## 5. Stop, start, restart and remove

```
$ docker stop portal
$ docker ps -a --filter name=portal
portal   Exited (143)
$ docker start portal
$ docker restart portal
$ docker ps
portal   Up 20 seconds (healthy)   0.0.0.0:8083->8080/tcp
$ docker stop portal
$ docker rm portal
$ docker ps -a --filter name=portal
(no containers)
```

## 6. Final running container

```
$ docker run -d --name portal -p 8083:8080 --restart unless-stopped luggage-portal:1.0
$ docker ps
4e198e99d97b  luggage-portal:1.0  Up 30 seconds (healthy)  0.0.0.0:8083->8080/tcp  portal
```

> Screenshot: Figure: Application served from the container on port 8083 (see Reports/Task_11 PDF)

## Conclusion
The application was packaged as a Docker image and the complete container lifecycle (build, run, port mapping, logs, stop, restart and remove) was carried out and logged.
