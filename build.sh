#!/usr/bin/env bash

rm -rf "out/modules"
mkdir -p "out/modules"

./gradlew clean build publish
rm -rf "out/base"
mv "build/libs" "out/base"
notify-send "Published base"

cd "modules"

for MODULE in $(find * -maxdepth 0 -type d | grep -v "megane-all" | tr '\r\n' ' '); do
  cd "$MODULE"
  ../../gradlew clean build
  mv "build/libs" "../../out/modules/$MODULE"
  notify-send "built module $MODULE"
  cd ..
done

cd "megane-all"
../../gradlew clean build
mv "build/libs" "../../out/modules/all"

notify-send "Built module all"
