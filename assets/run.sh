#!/usr/bin/env bash
java \
  -Dglass.platform=gtk \
  -Djava.library.path=/opt/arm32fb-sdk/lib \
  -Dmonocle.platform.traceConfig=false \
  -Dprism.verbose=false \
  -Djavafx.verbose=false \
  --module-path .:/opt/arm32fb-sdk/lib \
  --add-modules javafx.controls \
  --module com.pi4j.example/com.pi4j.example.FxglExample $@
