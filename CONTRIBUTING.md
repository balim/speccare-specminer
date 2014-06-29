Requirements:

- oraclejdk7 or openjdk7
- maven

To build and test:

    ./init.sh
    
To develop and test in IDE you probably want to start server first:

    mvn -Dspring.profiles.active="uat" jetty:start
