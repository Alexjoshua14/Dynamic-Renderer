3D Renderer - Java
=================================

This repository's foundation is from an implementation of the projects outlined in [the Build your own 3D renderer workshop](https://avik-das.github.io/build-your-own-raytracer/). The implementation is in Java, outputting to a .png file. The foundation can be found in the v1.0 tag in which a complete implementation of the projects from avikdas's tutorial is located.

After completing the tutorial, I idependently expanded the functionality of the 3D renderer with the first goal being a to have a constantly refreshing scene in which the scene objects gain the ability of movement and collision.

Quick Start for most recent version
-----------
```sh
git clone https://github.com/alexjoshua14/Dynamic-Renderer.git
cd Dynamic-Renderer

# Build and run the raytracer
gradle run
```


Quick Start for v1.0 tag
-----------

```sh
git clone https://github.com/alexjoshua14/Dynamic-Renderer.git
cd Dynamic-Renderer
git checkout v1.0

# Build the raytracer
gradle build

# Run it, outputting to a file called "test.png" in the current directory
java -jar build/libs/raytracer-java-1.0-SNAPSHOT.jar test.png
```
