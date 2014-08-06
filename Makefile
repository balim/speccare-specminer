clean:
	mvn clean

install:
	mvn clean package

test:
	mvn -Dspring.profiles.active="test" clean verify

test-coverage:
	mvn -Dspring.profiles.active="test" clean verify && \
	mvn org.pitest:pitest-maven:mutationCoverage

run-uat:
	mvn -Dspring.profiles.active="uat" -Djetty.port=9999 jetty:start

run:
	java -jar -DlivingDocumentation.featuresDir=`pwd`/src target/dependency/jetty-runner.jar --port 48001 target/speccare-specminer-1.0-SNAPSHOT.war

container-build:
	mvn clean
	docker build -t michaelszymczak/speccare-specminer .

container-run:
	docker run -P -d --name speccare-specminer michaelszymczak/speccare-specminer make run && \
	docker port speccare-specminer 48001 | sed 's/0.0.0.0/Specminer started. Go to http:\/\/localhost/'

debug-quickrun:
	docker run -P -v `pwd`:/features --rm michaelszymczak/speccare-specminer java -jar -DlivingDocumentation.featuresDir=/features /features/target/dependency/jetty-runner.jar --port 48001 /features/target/speccare-specminer-1.0-SNAPSHOT.war

debug-bash:
	docker run -t -i -P -v `pwd`:/features --rm michaelszymczak/speccare-specminer /bin/bash

.PHONY: clean install run test test-coverage test-start-server
