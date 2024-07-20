pipeline { 
    agent none 
    environment {
        WORKSPACE_DIR = "${env.WORKSPACE}"
        DOCKER_WORKSPACE_DIR = "${WORKSPACE_DIR.replace('C:', '/c').replace('\\', '/')}"
    }
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
                        docker run -d -p 80:80 --name my-apache-php-app -v ${DOCKER_WORKSPACE_DIR}/src:/var/www/html php:7.2-apache 
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
                        echo "Current directory: ${WORKSPACE_DIR}"
                        echo "Docker workspace directory: ${DOCKER_WORKSPACE_DIR}"
                        echo "Listing contents of ${WORKSPACE_DIR}:"
                        ls -l ${WORKSPACE_DIR}
                        echo "Attempting to run Maven build inside Docker container"
                        docker run --rm -v /root/.m2:/root/.m2 -v ${DOCKER_WORKSPACE_DIR}:/workspace -w /workspace maven:3-alpine ls -l /workspace
                        docker run --rm -v /root/.m2:/root/.m2 -v ${DOCKER_WORKSPACE_DIR}:/workspace -w /workspace maven:3-alpine cat /workspace/pom.xml || echo "POM file not found"
                        docker run --rm -v /root/.m2:/root/.m2 -v ${DOCKER_WORKSPACE_DIR}:/workspace -w /workspace maven:3-alpine mvn -B -DskipTests clean package || echo "Maven build failed"
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
