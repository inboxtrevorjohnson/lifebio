#!/bin/sh
while ! nc -z lifebio-eureka-server 8761 ; do
    echo "Waiting for upcoming Eureka Server"
    sleep 2
done
java -jar /opt/spring-cloud/lib/LifeBioConfig-1.0-exec.jar