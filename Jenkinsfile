pipeline { 
    agent none 
    stages { 
        stage('Integration UI Test') { 
            parallel { 
                stage('Deploy') { 
                    agent any 
                    steps { 
                        sh ''' 
                        if [ $(docker ps -a -q -f name=my-apache-php-app) ]; then 
                            docker rm -f my-apache-php-app 
                        fi
                        docker run -d -p 80:80 --name my-apache-php-app -v /c/Users/chari/Desktop/SIT/YEAR_2/TRI_3/ICT2216_SECURE_SOFTWARE_DEVELOPMENT/TEST/jenkins-php-selenium-test/src:/var/www/html php:7.2-apache 
                        sleep 20 
                        echo 'Now...' 
                        echo 'Visit http://localhost to see your PHP application in action.' 
                        ''' 
                        input message: 'Finished using the website? (Click "Proceed" to continue)' 
                        sh ''' 
                        docker kill my-apache-php-app 
                        docker rm my-apache-php-app 
                        ''' 
                    } 
                } 
                stage('Headless Browser Test') { 
                    agent any 
                    steps { 
                        sh ''' 
                        docker pull maven:3-alpine
                        echo "Current directory: $(pwd)"
                        echo "Listing contents of $(pwd):"
                        ls -l $(pwd)
                        echo "Listing contents of /var/jenkins_home/workspace/Lab-07b@2:"
                        ls -l /var/jenkins_home/workspace/Lab-07b@2
                        docker run --rm -v /root/.m2:/root/.m2 -v /var/jenkins_home/workspace/Lab-07b@2:/workspace -w /workspace maven:3-alpine ls -l /workspace
                        docker run --rm -v /root/.m2:/root/.m2 -v /var/jenkins_home/workspace/Lab-07b@2:/workspace -w /workspace maven:3-alpine cat /workspace/pom.xml
                        docker run --rm -v /root/.m2:/root/.m2 -v /var/jenkins_home/workspace/Lab-07b@2:/workspace -w /workspace maven:3-alpine mvn -B -DskipTests clean package 
                        docker run --rm -v /root/.m2:/root/.m2 -v /var/jenkins_home/workspace/Lab-07b@2:/workspace -w /workspace maven:3-alpine mvn test 
                        ''' 
                    } 
                    post { 
                        always { 
                            junit 'target/surefire-reports/*.xml' 
                        } 
                    } 
                } 
            } 
        } 
    } 
}
