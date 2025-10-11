#!/bin/bash

cd /Users/neil/AndroidStudioProjects/BlueLabsCMP

./gradlew clean

./gradlew :buildSrc:build

./gradlew :composeApp:generateSitemap

./gradlew :composeApp:wasmJsBrowserDistribution

firebase deploy