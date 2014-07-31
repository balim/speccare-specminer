FROM java:7
MAINTAINER Michael Szymczak <contact@michaelszymczak.com>

RUN apt-get update
RUN apt-get install -y maven make

ADD ./pom.xml /tmp/maven/pom.xml
WORKDIR /tmp/maven
RUN ["mvn", "dependency:go-offline"]

EXPOSE 48001

ADD . /app
WORKDIR /app

RUN ["make", "test"]
RUN ["make", "install"]





