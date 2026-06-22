#!/usr/bin/env bash
declare SCRIPT_DIR="$(cd "${0%/*}" ; pwd)"
java \
  -Dglass.platform=gtk \
  -Djava.library.path="${SCRIPT_DIR}/openjfx/lib" \
  -Dmonocle.platform.traceConfig=false \
  -Dprism.verbose=false \
  -Djavafx.verbose=false \
  --module-path ".:${SCRIPT_DIR}/openjfx/lib" \
  --add-modules javafx.controls \
  --module com.pi4j.example/com.pi4j.example.FxglExample $@
