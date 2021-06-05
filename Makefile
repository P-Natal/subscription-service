run: start-db
	mvn spring-boot:run;

start-db:
	docker-compose up -d;