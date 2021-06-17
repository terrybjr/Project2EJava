#!/bin/sh
mvn clean package && docker build -t com.temp/project-2e-ws .
docker rm -f project-2e-ws || true && docker run -d -p 8080:8080 -p 4848:4848 --name project-2e-ws com.temp/project-2e-ws 
