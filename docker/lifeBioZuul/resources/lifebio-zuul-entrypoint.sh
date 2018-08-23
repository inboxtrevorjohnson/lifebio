#!/bin/sh
while ! nc -z lifebio-feign 4001 ; do
    echo "Waiting for upcoming Feign Server"
    sleep 2
done
java -jar /opt/spring-cloud/lib/LifeBioZuul-1.0-exec.jar