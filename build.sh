#!/usr/bin/env bash

rm -rf "out/modules"
mkdir -p "out/modules"

./gradlew clean build publish
mv "build/libs" "out/modules/base"
notify-send "Published base"

cd "modules"

for MODULE in $(find * -maxdepth 0 -type d | grep -v "megane-all" | tr '\r\n' ' '); do
  cd "$MODULE"
  ../../gradlew clean build publish
  mv "build/libs" "../../out/modules/$MODULE"
  notify-send "Published module $MODULE"
  cd ..
done

cd "megane-all"
../../gradlew clean build
mv "build/libs" "../../out/modules/all"

notify-send "Built module all"
