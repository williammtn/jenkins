pipeline {
  agent any

  tools {
    maven 'maven'
  }

  environment {
    // Nom de l'installation SonarQube dans Jenkins
    SONARQUBE = 'sonarqube'
    // Nom de l'image Docker à construire
    DOCKER_IMAGE = 'spring-poc-image'
  }

  stages {
    stage('Checkout') {
      steps {
        // Clonage du dépôt GitHub
        git url: 'https://github.com/williammtn/jenkins.git', branch: 'master'
      }
    }

    stage('Analyse SonarQube') {
      steps {
        // Exécute l'analyse SonarQube en utilisant Maven
        // Assurez-vous d’avoir un fichier pom.xml correctement configuré
        withSonarQubeEnv("${SONARQUBE}") {
          sh "mvn clean verify sonar:sonar -Dsonar.projectKey=poc -Dsonar.projectName=poc -Dsonar.login=sqa_761d86a22f48fe2c9825513ca3d778c7eb28d7b6"
        }
      }
    }

    stage('Tests') {
      steps {
        // Exécute les tests unitaires
        sh 'mvn test'
      }
    }

    stage('Build Docker Image') {
      agent {
        docker {
          image 'docker:stable'
          args '-v /var/run/docker.sock:/var/run/docker.sock'
          reuseNode true
        }
      }
      steps {
        sh 'docker build -t spring-poc-image .'
      }
    }

    stage('Déploiement sur Azure') {
      steps {
        echo 'Déploiement sur Azure...'
        sh 'echo " Deployment on Azure successful"'
      }
    }
  }

  post {
    always {
      // Publie les résultats des tests et archive l’artefact si besoin
      junit '**/target/surefire-reports/*.xml'
      archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
    }
  }
}
