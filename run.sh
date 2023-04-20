#!/usr/bin/env bash
javac --enable-preview -source 20 Main.java && java --enable-preview Main; rm *.class