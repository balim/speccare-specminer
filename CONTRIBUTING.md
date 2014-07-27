Requirements:

- make
- oraclejdk7 or openjdk7
- maven

To build and test:

    make test-coverage
    
    
To develop and test in IDE you probably want to start server first:

    make test-start-server

And run some tests. When you run some individual tests outside the maven in IDE,
be sure that you provide the jetty port number, i.e. -Djetty.port=999 to the test run arguments list.
If the port that you used to start the jetty server is different, change it accordingly.