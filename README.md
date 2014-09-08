SpecMiner (part of SpecCare) - WIP
===================

Travis build status: [![Build Status](https://travis-ci.org/michaelszymczak/speccare-specminer.svg?branch=master)](https://travis-ci.org/michaelszymczak/speccare-specminer)

A tool that provides a RESTful API to retrieve projects' actual specification.

Supported spec types:

- Gherkin *.feature files [DONE]
- Cucumber-jvm reports [WIP]
- Behat reports [WIP]

Requirements:
-------------

- make
- docker


Usage
------------
Run the application by typing

    make container-run SPECMINER_FEATURESDIR=/path/to/your/project

Or, if you do not want to use make, open Makefile to see what the full command is.

