#!/bin/bash

mvn -f fetcher/pom.xml clean &&
mvn -f fetcher/pom.xml package -DskipTests &&

mvn -f query/pom.xml clean &&
mvn -f query/pom.xml package -DskipTests
