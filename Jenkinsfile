// Task 12 pipeline: Checkout -> Build -> Unit Test -> Package -> Deploy to Test -> Selenium
//   -> Deploy Final (Tomcat) -> Docker Build -> Docker Push (local registry) -> Docker Deploy
// If any test stage fails, nothing after it runs (no image, no push, no deployment).
pipeline {
    agent any

    parameters {
        choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging'],
               description: 'Environment label baked into the app')
    }

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH      = "${JAVA_HOME}/bin:${PATH}"
        WEBAPPS   = '/opt/tomcat/webapps'
        BASE      = 'http://localhost:8082'
        APP_ENV   = "${(params.DEPLOY_ENV ?: 'dev').toUpperCase()}"
        REGISTRY  = 'localhost:5000'
        IMAGE     = 'localhost:5000/luggage-portal'
        TAG       = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') { steps { checkout scm } }

        stage('Build') { steps { sh 'mvn -B clean compile' } }

        stage('Unit Test') {
            steps { sh 'mvn -B test' }
            post { always { junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml' } }
        }

        stage('Package') {
            steps { sh 'mvn -B package -DskipTests -Dapp.env=$APP_ENV -Dapp.build=$BUILD_NUMBER' }
        }

        stage('Deploy to Test') {
            steps {
                sh '''
                    cp target/luggage-portal.war $WEBAPPS/luggage-test.war
                    bash scripts/wait-for-build.sh $BASE/luggage-test $BUILD_NUMBER
                '''
            }
        }

        stage('Selenium Tests') {
            steps { sh 'mvn -B test -Pselenium -Dbase.url=$BASE/luggage-test' }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: 'target/screenshots/*.png', allowEmptyArchive: true
                }
            }
        }

        stage('Deploy Final') {
            steps {
                sh '''
                    cp target/luggage-portal.war $WEBAPPS/luggage-portal.war
                    bash scripts/wait-for-build.sh $BASE/luggage-portal $BUILD_NUMBER
                '''
            }
        }

        stage('Docker Build') {
            steps { sh 'docker build -t $IMAGE:$TAG -t $IMAGE:latest .' }
        }

        stage('Docker Push') {
            steps {
                sh '''
                    docker push $IMAGE:$TAG
                    docker push $IMAGE:latest
                    curl -s http://localhost:5000/v2/luggage-portal/tags/list
                '''
            }
        }

        stage('Docker Deploy') {
            steps {
                sh '''
                    docker rm -f portal || true
                    docker run -d --name portal --restart unless-stopped -p 8083:8080 $IMAGE:$TAG
                    for i in $(seq 1 30); do
                        if curl -sf http://localhost:8083/actuator/health > /dev/null; then
                            echo "Container is healthy"; exit 0
                        fi
                        sleep 5
                    done
                    echo "Container did not become healthy"; docker logs --tail 30 portal; exit 1
                '''
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            echo "Deployed ${env.IMAGE}:${env.TAG} to http://localhost:8083"
        }
        failure { echo 'Pipeline failed - see the red stage. Later stages did not run.' }
    }
}
