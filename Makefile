setup:

build:
	./gradlew build

dev:
	./script/kill-backend-process.sh 8080
	./gradlew bootRun

start:

test: