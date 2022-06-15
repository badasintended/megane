#!/usr/bin/bash

DML_PROJECT_ID="398614"
DML_FILE_ID="3758209"

rm -rf "dml_modules"
mkdir -p "dml_modules"

(
    cd "dml_modules"
    TEMP_DIR="$(mktemp -d /tmp/megane-dml.XXXXX)"

    echo
    echo "Downloading DML jar"
    curl -L "https://cursemaven.com/curse/maven/dml-${DML_PROJECT_ID}/${DML_FILE_ID}/dml-${DML_PROJECT_ID}-${DML_FILE_ID}.jar" -o "dml.jar"

    echo
    echo "Extracting jar-in-jar"
    mkdir "$TEMP_DIR/out"
    unzip "dml.jar" -d "$TEMP_DIR/out"
    cp -r "$TEMP_DIR/out/META-INF/jars/." .

    rm -rf "$TEMP_DIR"
    cd ..
)


