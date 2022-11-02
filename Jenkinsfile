pipeline{
agent any
stages{
             tools{
                    maven '3.6.3'
                }
                stage('Build Maven'){
                    steps{
                        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'ba4a0cbe-520f-465a-8d41-3e86112c0466', url: 'https://github.com/RJ976389/DemoMicroservice.git']]])
                    bat 'mvn clean install'
                    }
}
}
}
