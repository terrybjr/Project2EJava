#!/bin/sh
mvn clean package && docker build -t com.temp/project-2e-ejb .
docker rm -f project-2e-ejb || true && docker run -d -p 8080:8080 -p 4848:4848 --name project-2e-ejb com.temp/project-2e-ejb 
