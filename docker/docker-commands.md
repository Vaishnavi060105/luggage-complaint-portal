# Docker container lifecycle (Task 11)

Build the WAR first: `mvn clean package -DskipTests`

| Step | Command |
|---|---|
| Build image | `docker build -t luggage-portal:1.0 .` |
| Tag for Docker Hub | `docker tag luggage-portal:1.0 vaishnavi060105/luggage-portal:1.0` |
| List images | `docker images` |
| Run (map port 8082 -> 8080) | `docker run -d --name portal -p 8082:8080 luggage-portal:1.0` |
| List running containers | `docker ps` |
| View logs | `docker logs portal` (add `-f` to follow) |
| Inspect details | `docker inspect portal` |
| Health status | `docker inspect --format '{{.State.Health.Status}}' portal` |
| Stop | `docker stop portal` |
| Start again | `docker start portal` |
| Restart | `docker restart portal` |
| Remove container | `docker stop portal && docker rm portal` |
| Remove image | `docker rmi luggage-portal:1.0` |
| Push to Docker Hub | `docker login` then `docker push vaishnavi060105/luggage-portal:1.0` |

Open http://localhost:8082 and http://localhost:8082/actuator/health.

Lifecycle: **image built -> container created -> running -> stopped -> removed**.
Record the whole session with `script docs/evidence/task11-docker.log` ... `exit`.
