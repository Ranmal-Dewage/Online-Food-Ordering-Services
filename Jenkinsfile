pipeline{

  agent any

  stages {

    stage("build") {

      setps {
        sh "pwd"
        dir("product") {
          sh "pwd"
        }
        echo "building the application"      
      }

    }

    stage("test") {

       setps {
        sh "pwd"
        dir("product") {
          sh "pwd"
        }
         echo "testing the application"        
      }

    }

    stage("deploy") {

       setps {
        sh "pwd"
        dir("product") {
          sh "pwd"
        }
         echo "deploying the application"        
      }

    }

  }

}
