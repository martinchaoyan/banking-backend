pipeline {
    agent any

    tools {
        jdk 'JDK_21'          // 你需要在 Jenkins 中配置这个名字的 JDK 21
        gradle 'Gradle_8'     // 同上，根据你 Jenkins 中配置的名称来写
    }

    environment {
        JAVA_HOME = "${tool 'JDK_21'}"
        GRADLE_HOME = "${tool 'Gradle_8'}"
        PATH = "${env.GRADLE_HOME}/bin:${env.JAVA_HOME}/bin:${env.PATH}"

        // GitHub 
        GHCR_USERNAME = 'martinchaoyan'
        GHCR_TOKEN = credentials('banking')
        GHCR_IMAGE = "ghcr.io/martinchaoyan/banking-backend:latest"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                sh './gradlew clean build -x test'
            }
        }

        stage('Login to GHCR') {
            steps {
                sh 'echo $GHCR_TOKEN | docker login ghcr.io -u $GHCR_USERNAME --password-stdin'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $GHCR_IMAGE .'
            }
        }

        stage('Push Docker Image') {
            steps {
                sh 'docker push $GHCR_IMAGE'
            }
        }
    }

    post {
        success {
            echo 'Build and Push Successful!'
        }
        failure {
            echo 'Build Failed'
        }
    }
}
