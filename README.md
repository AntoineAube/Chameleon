# Chameleon

[![Build Status](https://travis-ci.org/AntoineAube/Chameleon.svg?branch=master)](https://travis-ci.org/AntoineAube/Chameleon)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=fr.antoineaube.chameleon&metric=alert_status)](https://sonarcloud.io/api/project_badges/measure?project=fr.antoineaube.chameleon&metric=alert_status)

**Chameleon** is both a framework and an application for steganography using the least significant bits.

## Description

> Steganography is the practice of concealing a file, message, image, or video within another file, message, image, or video.
*(Wikipedia, [Steganography](https://en.wikipedia.org/wiki/Steganography))*

**Chameleon** implements the LSB (least significant bits) technique to conceal a file in an image and also retrieve it.

This article explains how it works: [Least Significant Bit algorithm for image steganography](http://ijact.org/volume3issue4/IJ0340004.pdf).

**Chameleon** works with configurable concealment configurations which allow users to play with channels, numbers of bits per colour and followed patterns. 
More details on configurations on [this wiki page](https://github.com/AntoineAube/Chameleon/wiki/Concealment-configurations).

It allows developers to define their own patterns to create new ways of concealing files into images. The home of the developers guide is [this wiki page](https://github.com/AntoineAube/Chameleon/wiki/Developers-guide).

There is also a graphical interface to use built-in features of **Chameleon**. The user guide is [here](https://github.com/AntoineAube/Chameleon/wiki/User-guide).

## Modules & Artifacts

**Chameleon** is split in five parts:
* `chameleon-core`: the core APIs of **Chameleon** and the implementation of the core algorithms.
* `chameleon-patterns`: standard implementations of concealment patterns.
* `chameleon-pictures`: standard implementations of pictures.
* `chameleon-gui`: the graphical application.
* `chameleon-integration`: stress tests and other tests which use all the other modules.

`chameleon-patterns` and `chameleon-pictures` are not mandatory for a developer use: 
he may want to implement his own pictures and patterns.

## Building

### Requirements

* Java 8+
* JavaFX (`openjfx` or the Oracle JDK)

Check out and build:
```bash
git clone git@github.com:AntoineAube/Chameleon.git
cd Chameleon
./gradlew build
```

The artifacts may be found at `chameleon-${artifact.name}/build/libs/chameleon-${artifact.name}-${chameleon.version}.jar`

## Running the GUI application

Build and run:
```bash
java -jar chameleon-gui/build/libs/chameleon-gui-1.0-SNAPSHOT.jar
```

## Continuous integration

* [Travis CI](https://travis-ci.org/AntoineAube/Chameleon)
* [SonarCloud](https://sonarcloud.io/dashboard?id=fr.antoineaube.chameleon)