# Task 12: Jenkins-Docker Continuous Deployment

## Objective
To extend the Jenkins pipeline to build a versioned Docker image, publish it to a registry and deploy a fresh container automatically after the tests pass.

## 1. Local registry and Docker access for Jenkins

```
docker run -d --name registry --restart unless-stopped -p 5000:5000 registry:2
sudo usermod -aG docker jenkins
sudo systemctl restart jenkins
```

## 2. Docker stages added to the Jenkinsfile

```
environment {
    IMAGE = 'localhost:5000/luggage-portal'
    TAG   = "${env.BUILD_NUMBER}"      // version tag = build number
}
stage('Docker Build')  { steps { sh 'docker build -t $IMAGE:$TAG -t $IMAGE:latest .' } }
stage('Docker Push')   { steps { sh 'docker push $IMAGE:$TAG && docker push $IMAGE:latest' } }
stage('Docker Deploy') { steps { sh 'docker rm -f portal || true
                                      docker run -d --name portal --restart unless-stopped -p 8083:8080 $IMAGE:$TAG' } }
```

## 3. Versioned images in the registry

> Screenshot: Figure: Registry tags ["8","7","latest"], docker images and the running portal container (see Reports/Task_12 PDF)

## 4. End-to-end pipeline run

> Screenshot: Figure: Build #10: Deployed localhost:5000/luggage-portal:10 to http://localhost:8083, Finished: SUCCESS (see Reports/Task_12 PDF)

## 5. Fresh container serving the new build

> Screenshot: Figure: Application on port 8083 showing Environment: STAGING (see Reports/Task_12 PDF)

## Conclusion
The pipeline builds a versioned image, pushes it to a local registry and deploys a fresh container automatically after the tests pass, from commit to container.
