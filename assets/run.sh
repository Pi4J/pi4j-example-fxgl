#!/usr/bin/env bash
java  \
  -Dmonocle.egl.lib=/opt/arm32fb-sdk/lib/libgluon_drm.so \
  -Djava.library.path=/opt/arm32fb-sdk/lib \
  -Dmonocle.platform.traceConfig=false \
  -Dprism.verbose=false \
  -Djavafx.verbose=false \
  -Dmonocle.platform=EGL \
  --module-path /opt/arm32fb-sdk/lib, . \
  --add-modules javafx.controls \
  --module com.pi4j.example/com.pi4j.example.FxglExample $@