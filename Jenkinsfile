pipeline {

  agent any
  tools {
    maven "maven 3.8.5"
  }

  stages {

    stage("test") {

      steps {
        echo "testing the application"
        dir("product") {
          sh "mvn clean test"
        }
        dir("user") {
          sh "mvn clean test"
        }
        dir("shopping-cart") {
          sh "mvn clean test"
        }   
      }

    }

    stage("build") {

      steps {
        echo "building the application"
        dir("product") {
          sh "mvn clean package"
        }
        dir("user") {
          sh "mvn clean package"
        }
        dir("shopping-cart") {
          sh "mvn clean package"
        }       
      }

    }
    
    stage("sonarqube analysis") {
        
      steps {
        echo "executing sonarqube analysis"
        dir("product") {
          withSonarQubeEnv("sonarqube-8.9.8") {
            sh "mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.coverage.jacoco.xmlReportPaths=./target/site/jacoco/jacoco.xml -Dsonar.coverage.exclusions=**/controller/*.java,**/dto/*.java,**/exception/*.java,**/model/*.java,**/repository/*.java,**/product/*.java"
          }
        }
        dir("user") {
          withSonarQubeEnv("sonarqube-8.9.8") {
            sh "mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.coverage.jacoco.xmlReportPaths=./target/site/jacoco/jacoco.xml -Dsonar.coverage.exclusions=**/controller/*.java,**/dto/*.java,**/exception/*.java,**/model/*.java,**/repository/*.java,**/user/*.java"
          }
        }
        dir("shopping-cart") {
          withSonarQubeEnv("sonarqube-8.9.8") {
            sh "mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.coverage.jacoco.xmlReportPaths=./target/site/jacoco/jacoco.xml -Dsonar.coverage.exclusions=**/controller/*.java,**/dto/*.java,**/exception/*.java,**/model/*.java,**/repository/*.java,**/shoppingcart/*.java"
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
