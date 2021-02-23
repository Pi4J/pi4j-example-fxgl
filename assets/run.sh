#!/usr/bin/env bash
java -Dmonocle.egl.lib=/opt/arm32fb-sdk/lib/libgluon_drm.so \
  -Djava.library.path=/opt/arm32fb-sdk/lib \
  -Dmonocle.platform.traceConfig=true \
  -Dmonocle.platform=EGL \
  -Dprism.verbose=true \
  -Djavafx.verbose=true \
  -p /opt/arm32fb-sdk/lib \
  --add-modules javafx.controls \
  --module-path . \
  --module com.pi4j.example/com.pi4j.example.FxglExample $@