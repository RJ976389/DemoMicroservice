pipeline{
agent any
stages{
               stage ('Build & Test') {
               				tools {
               					maven '3.6.3'
               				}
               				steps {
               				checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'ba4a0cbe-520f-465a-8d41-3e86112c0466', url: 'https://github.com/RJ976389/DemoMicroservice.git']]])
                                    sh 'mvn clean install'
                                    }
                            }

                            stage('Docker Build Image'){
                                    steps{
                                        script{
                                           bat 'docker build -t rs9763898989/om-jenkins:latest .'
                                            bat 'docker login --username rohitnarayanaboy@gmail.com  --password AKCp8nH4Xe7UzMouzKNn1odZBy39gUzoBLFKRJgREzXLZSQy2Vg62fLZFNdvGiewxPRo5Xuxb https://rohitlocalhost.jfrog.io'
                                            bat 'docker tag rs9763898989/om-jenkins:latest rohitlocalhost.jfrog.io/microservice-jenkinsfile/rs9763898989/om-jenkins:latest'
                                            bat 'docker push rohitlocalhost.jfrog.io/microservice-jenkinsfile/rs9763898989/om-jenkins:latest'
                                            }
                                    }
                            }        

}}

