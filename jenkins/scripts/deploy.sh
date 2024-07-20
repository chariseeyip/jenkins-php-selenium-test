#!/usr/bin/env sh

set -x
docker run -d -p 80:80 --name my-apache-php-app -v c:\\Users\\chari\\Desktop\\SIT\\YEAR 2\\TRI 3\\ICT2216 SECURE SOFTWARE DEVELOPMENT\\TEST\\jenkins-php-selenium-test\\src:/var/www/html php:7.2-apache
sleep 20
set +x

echo 'Now...'
echo 'Visit http://localhost to see your PHP application in action.'

