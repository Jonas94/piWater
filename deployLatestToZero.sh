#!/bin/bash

mvn clean install
scp target/piWater-0.0.1-SNAPSHOT.jar pi@192.168.1.172:~/piWater/piWater-LATEST.jar
