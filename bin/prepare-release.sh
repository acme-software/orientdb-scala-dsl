#!/usr/bin/env bash

# pre release tasks
sbt +test +doc

# sync documentation
rm -rf docs/api
mkdir -p docs/api/2.11
mkdir -p docs/api/2.12
cp -R target/scala-2.11/api/* docs/api/2.11
cp -R target/scala-2.12/api/* docs/api/2.12
