# spring-mulitple-datasources

Demonstrating how to use multiple datasources in a spring project

Use the following docker commands to startup docker containers for mysql and postgres 
~~~~
docker container run --name mysql -d -e MYSQL_USER=raman -e MYSQL_PASSWORD=jdbc -e MYSQL_DATABASE=persondb -e MYSQL_ROOT_PASSWORD=jdbc -p 3306:3306 mysql:5.7 
~~~~

~~~~
docker container run --name postgres -p 5432:5432 -e POSTGRES_USER=raman -e POSTGRES_DB=userdb -e POSTGRES_PASSWORD=jdbc postgres:10 
~~~~
