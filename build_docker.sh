#!/bin/sh
docker network create springboot-mysql-net
docker run --name mysqldb --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=alibatman -e MYSQL_DATABASE=balootdb -d mysql
docker build -t backend-baloot .
docker run --network springboot-mysql-net --name springboot-mysql-container -p 8080:8080 backend-baloot