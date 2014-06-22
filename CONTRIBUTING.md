Requirements:

- oraclejdk7 or openjdk7
- maven

To build and test:

    mvn clean verify

To run mutation tests

    mvn org.pitest:pitest-maven:mutationCoverage
