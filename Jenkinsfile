// Task 10 pipeline: Checkout -> Build -> Unit Test -> Package -> Deploy to Test
//                   -> Selenium Tests (quality gate) -> Deploy Final
// If Unit Test or Selenium Tests fail, Jenkins stops and Deploy Final never runs.
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
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Build') {
            steps { sh 'mvn -B clean compile' }
        }

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
            // Only reached when every test passed
            steps {
                sh '''
                    cp target/luggage-portal.war $WEBAPPS/luggage-portal.war
                    bash scripts/wait-for-build.sh $BASE/luggage-portal $BUILD_NUMBER
                '''
            }
        }
    }

    post {
        success { archiveArtifacts artifacts: 'target/*.war', fingerprint: true }
        failure { echo 'Pipeline failed - see the red stage. Deploy Final did not run.' }
    }
}
