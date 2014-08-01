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
	docker build -t michaelszymczak/speccare-specminer .

container-run:
	docker run -P -d michaelszymczak/speccare-specminer make run

.PHONY: clean install run test test-coverage test-start-server
