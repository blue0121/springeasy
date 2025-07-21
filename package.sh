#!/bin/bash

BUILD_NUMBER=$1

CURRENT_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

if [[ $CURRENT_VERSION == *"SNAPSHOT" ]]; then
    VERSION_PREFIX=$(echo $CURRENT_VERSION | sed 's/SNAPSHOT//')
else
    VERSION_PREFIX=$CURRENT_VERSION
fi

NEW_VERSION=""

if [ -n "$BUILD_NUMBER" ]; then
    NEW_VERSION="${VERSION_PREFIX}${BUILD_NUMBER}"
    echo "Setting version to ${NEW_VERSION}"
else
    NEW_VERSION="${CURRENT_VERSION}"
    echo "Keeping SNAPSHOT version: ${CURRENT_VERSION}"
fi

mvn clean deploy -DskipTests -Drevision=${NEW_VERSION}
EXIT_CODE=$?
if [ $EXIT_CODE -ne 0 ]; then
    echo "maven build failed"
    exit $EXIT_CODE
fi

echo "maven deploy successfully, final version: ${NEW_VERSION}"