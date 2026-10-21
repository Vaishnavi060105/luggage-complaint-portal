// Task 8 pipeline: Checkout -> Build -> Package -> Deploy (Tomcat)
// The full pipeline (tests, Selenium, Docker) is kept in Jenkinsfile.full for Tasks 10-12.
pipeline {
    agent any

    parameters {
        choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging'],
               description: 'Environment label baked into the app (shown in banner and footer)')
    }

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        PATH      = "${JAVA_HOME}/bin:${PATH}"
        WEBAPPS   = '/opt/tomcat/webapps'
        APP_URL   = 'http://localhost:8082/luggage-portal'
        APP_ENV   = "${(params.DEPLOY_ENV ?: 'dev').toUpperCase()}"
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Build') {
            steps { sh 'mvn -B clean compile' }
        }

        stage('Package') {
            // APP_ENV and the build number are written into the WAR
            steps { sh 'mvn -B package -DskipTests -Dapp.env=$APP_ENV -Dapp.build=$BUILD_NUMBER' }
        }

        stage('Deploy') {
            steps {
                sh '''
                    cp target/luggage-portal.war $WEBAPPS/luggage-portal.war
                    bash scripts/wait-for-build.sh $APP_URL $BUILD_NUMBER
                '''
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            echo "Deployed to ${env.APP_URL} (environment: ${env.APP_ENV})"
        }
        failure { echo 'Pipeline failed - see the stage that turned red. Nothing after it ran.' }
    }
}
