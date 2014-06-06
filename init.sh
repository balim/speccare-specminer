#!/bin/sh
mvn clean verify
mvn org.pitest:pitest-maven:mutationCoverage
