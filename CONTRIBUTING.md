Requirements:

- Virtualbox
- Vagrant

OR

- oraclejdk7 or openjdk7
- maven
- nodejs (with node binary as a symlink to node) 
- npm

To build and test:

    git clone https://github.com/michaelszymczak/independentfrontend.git -b spring provisioning
    vagrant up # can take several minutes
    vagrant ssh
    
    npm install
    ./node_modules/bower/bin/bower install
    ./node_modules/grunt-cli/bin/grunt
    
    
To develop and test in IDE you probably want to start server first:

    mvn -Dspring.profiles.active="uat" -Djetty.port=9999 jetty:start

And run some tests. When you run some individual tests outside the maven in IDE,
be sure that you provide the jetty port number, i.e. -Djetty.port=999 to the test run arguments list.
If the port that you used to start the jetty server is different, change it accordingly.