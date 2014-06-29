#!/bin/sh
mvn -Dspring.profiles.active="uat" -Djetty.port=9999 jetty:start
