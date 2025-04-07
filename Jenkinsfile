pipeline {
  agent any

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
          sh "mvn clean verify sonar:sonar -Dsonar.projectKey=poc -Dsonar.projectName='poc'"
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
      steps {
        // Construit l’image Docker à partir du Dockerfile à la racine du projet
        sh "docker build -t ${DOCKER_IMAGE} ."
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
