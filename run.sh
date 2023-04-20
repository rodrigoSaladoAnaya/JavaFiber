#!/usr/bin/env bash
javac --enable-preview -source 20 Test.java && java --enable-preview Test; rm *.class