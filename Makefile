clean:
	mvn clean

install:
	mvn clean install

run:
	mvn clean jetty:run

test:
	mvn clean verify

test-coverage:
	mvn -Dspring.profiles.active="test" clean verify && \
	mvn org.pitest:pitest-maven:mutationCoverage

test-start-server:
	mvn -Dspring.profiles.active="uat" -Djetty.port=9999 jetty:start

.PHONY: clean install run test test-coverage test-start-server
