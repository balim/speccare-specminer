clean:
	mvn clean

install:
	mvn -Dspring.profiles.active="test" clean install

run:
	mvn clean package && \
	java -jar target/dependency/jetty-runner.jar --port 48001 target/LivingDocumentation-1.0-SNAPSHOT.war

run-uat:
	mvn clean package && \
	java -jar target/dependency/jetty-runner.jar --port 9999 target/LivingDocumentation-1.0-SNAPSHOT.war

test:
	mvn -Dspring.profiles.active="test" clean verify

test-coverage:
	mvn -Dspring.profiles.active="test" clean verify && \
	mvn org.pitest:pitest-maven:mutationCoverage

test-start-server:
	mvn -Dspring.profiles.active="uat" -Djetty.port=9999 jetty:start

.PHONY: clean install run test test-coverage test-start-server
