# Task 08: Pipeline as Code and Server Deployment

## Objective
To create a Jenkinsfile with checkout, build, package and deploy stages, deploy the build to Tomcat and parameterise an environment setting.

## 1. Tomcat installed as the deployment target

Apache Tomcat 10.1 was installed in /opt/tomcat, set to port 8082 (Jenkins uses 8080) and run as a systemd service. The jenkins user was added to the tomcat group so that it can copy the WAR into webapps.

> Screenshot: Figure: Tomcat 10.1 welcome page on port 8082 (see Reports/Task_08 PDF)

## 2. Jenkinsfile (pipeline as code)

```
pipeline {
    agent any
    parameters {
        choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging'],
               description: 'Environment label baked into the app')
    }
    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        WEBAPPS   = '/opt/tomcat/webapps'
        APP_URL   = 'http://localhost:8082/luggage-portal'
        APP_ENV   = "${(params.DEPLOY_ENV ?: 'dev').toUpperCase()}"
    }
    stages {
        stage('Checkout') { steps { checkout scm } }
        stage('Build')    { steps { sh 'mvn -B clean compile' } }
        stage('Package')  { steps { sh 'mvn -B package -DskipTests -Dapp.env=$APP_ENV -Dapp.build=$BUILD_NUMBER' } }
        stage('Deploy')   { steps { sh 'cp target/luggage-portal.war $WEBAPPS/luggage-portal.war' } }
    }
}
```

> Screenshot: Figure: Jenkinsfile on GitHub (develop branch) (see Reports/Task_08 PDF)

## 3. Pipeline run

> Screenshot: Figure: Stages view of builds #1 to #3, all stages green (see Reports/Task_08 PDF)

> Screenshot: Figure: Console output: deployed to http://localhost:8082/luggage-portal (environment: DEV) (see Reports/Task_08 PDF)

## 4. Parameterised environment setting

> Screenshot: Figure: Build with Parameters: DEPLOY_ENV = staging (see Reports/Task_08 PDF)

> Screenshot: Figure: Deployed application showing Environment: STAGING (see Reports/Task_08 PDF)

> Screenshot: Figure: Build with Parameters: DEPLOY_ENV = dev (see Reports/Task_08 PDF)

> Screenshot: Figure: Deployed application showing Environment: DEV (see Reports/Task_08 PDF)

## Conclusion
A Jenkinsfile with checkout, build, package and deploy stages deployed the application to Tomcat, and the DEPLOY_ENV parameter changed the environment shown in the application.
