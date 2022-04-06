pipeline {

  agent any
  tools {
    maven "maven 3.8.5"
  }

  stages {

    stage("build") {

      steps {
        echo "building the application"
        dir("product") {
          sh "mvn clean package"
        }      
      }

    }

    stage("test") {

      steps {
        echo "testing the application"
      }

    }
    
    stage("sonarqube analysis") {
        
      steps {
        echo "executing sonarqube analysis"
        dir("product") {
          withSonarQubeEnv("sonarqube-8.9.8") {
            sh "mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000"
          }
        }
      }

    }

    stage("deploy") {

      steps {
        echo "deploying the application"        
      }

    }

  }

}
