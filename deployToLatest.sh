#!/bin/bash

gradle clean build
scp build/libs/piWater-0.0.1-SNAPSHOT.jar pi@192.168.1.114:~/piWater/piWater-LATEST.jar
