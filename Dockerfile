FROM java:7
MAINTAINER Michael Szymczak <contact@michaelszymczak.com>

RUN apt-get update && apt-get install -y maven make locales

RUN echo "LANG=en_US.UTF-8" > /etc/default/locale && \
    sed -i 's/^[ ]*#[ ]*en_US.UTF-8/en_US.UTF-8/' /etc/locale.gen && \
    locale-gen en_US.UTF-8

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

ADD ./pom.xml /tmp/maven/pom.xml
WORKDIR /tmp/maven
RUN ["mvn", "dependency:go-offline"]

ADD . /app
WORKDIR /app

RUN ["make", "test"]
RUN ["make", "install"]

EXPOSE 48001
