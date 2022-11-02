pipeline{
agent any
stages{
               stage ('Build & Test') {
               				tools {
               					maven "Maven 3.6.3"
               				}
               				steps {
               				checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'ba4a0cbe-520f-465a-8d41-3e86112c0466', url: 'https://github.com/RJ976389/DemoMicroservice.git']]])
                                    sh 'mvn clean install'
                                    }
                               }     

}
}
