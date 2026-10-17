// Declarative pipeline for the Luggage Complaint Portal.
// Flow: Checkout -> Build -> Unit Test -> Package -> Deploy to Test -> Selenium Tests
//       -> Deploy Final -> Docker Build -> Docker Push -> Docker Deploy
// If Unit Test or Selenium Tests fail, Jenkins stops and NOTHING after that stage runs.
pipeline {
    agent any

    tools {
        maven 'Maven3'   // Manage Jenkins > Tools
        jdk 'JDK17'
    }

    parameters {
        choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging'],
               description: 'Environment label baked into the app (shown in banner and footer)')
    }

    environment {
        IMAGE   = 'vaishnavi060105/luggage-portal'   // Docker Hub repository
        TAG     = "${env.BUILD_NUMBER}"              // version tag = build number
        WEBAPPS = '/opt/tomcat/webapps'
        APP_ENV = "${(params.DEPLOY_ENV ?: 'dev').toUpperCase()}"
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }   // pull the latest code from GitHub
        }

        stage('Build') {
            steps { sh 'mvn -B clean compile' }
        }

        stage('Unit Test') {
            steps { sh 'mvn -B test' }   // Selenium tests are excluded here
            post { always { junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml' } }
        }

        stage('Package') {
            // APP_ENV and the build number are written into the WAR (visible in the banner and /actuator/info)
            steps { sh 'mvn -B package -DskipTests -Dapp.env=$APP_ENV -Dapp.build=$BUILD_NUMBER' }
        }

        stage('Deploy to Test') {
            // A separate test instance so Selenium never runs against the "real" deployment
            steps {
                sh '''
                    cp target/luggage-portal.war $WEBAPPS/luggage-test.war
                    bash scripts/wait-for-build.sh http://localhost:8080/luggage-test $BUILD_NUMBER
                '''
            }
        }

        stage('Selenium Tests') {
            steps { sh 'mvn -B test -Pselenium -Dbase.url=http://localhost:8080/luggage-test' }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: 'target/screenshots/*.png', allowEmptyArchive: true
                }
            }
        }

        stage('Deploy Final') {
            // Only reached when every test passed
            steps {
                sh '''
                    cp target/luggage-portal.war $WEBAPPS/luggage-portal.war
                    bash scripts/wait-for-build.sh http://localhost:8080/luggage-portal $BUILD_NUMBER
                '''
            }
        }

        stage('Docker Build') {
            steps { sh 'docker build -t $IMAGE:$TAG -t $IMAGE:latest .' }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DH_USER', passwordVariable: 'DH_PASS')]) {
                    sh '''
                        echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin
                        docker push $IMAGE:$TAG
                        docker push $IMAGE:latest
                    '''
                }
            }
        }

        stage('Docker Deploy') {
            steps {
                sh '''
                    docker rm -f portal || true
                    docker run -d --name portal -p 8082:8080 $IMAGE:$TAG
                '''
            }
        }
    }

    post {
        success { archiveArtifacts artifacts: 'target/*.war', fingerprint: true }
        failure { echo 'Pipeline failed - see the stage that turned red. Nothing after it was deployed.' }
    }
}
