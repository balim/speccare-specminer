#!/bin/sh
mvn -Dspring.profiles.active="test" clean verify
mvn org.pitest:pitest-maven:mutationCoverage
