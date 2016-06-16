# Graphics

3D Graphics implemented from scratch, for fun. I wrote a linear algebra library from scratch then implemented it in a Scala.js project to create an [interactive online demo](https://anubiann00b.github.io/Graphics).

This was my final project for Linear Algebra at Bellevue College. Check out my [presentation slides](https://docs.google.com/presentation/d/1g4EpVgR5TG7khKvMejvNLPjuj-Lxkps9VCQrgyL75dc/edit?usp=sharing).

![Screenshot of demo with teapot model](screenshot.png)

## Controls

 * WASD and Shift/Space to translate the model in space
 * Arrow keys to rotate the model about the X and Y axis
 * F to toggle to display of the faces
 * M to toggle the display of the mesh

## Building

```
sbt frontend/fullOptJs
```

Then host a local webserver:

```
python -m SimpleHTTPServer 8080
```

And navigate to `localhost:8080`

## Description

The `module_model` module contains the linear algebra library, and the `module_frontend` module contains the website code.

Here are a handful of important files:
 * [Vec.scala](module_model/shared/src/main/scala/me/shreyasr/graphics/Vec.scala)

All the code for the Vector class. Vectors are represented as a simple array of Floats.

 * [Mat.scala](module_model/shared/src/main/scala/me/shreyasr/graphics/Mat.scala)

All the code for the Matrix class. Matrices are represented as an array of floats with a number representing the number of columns. The companion object in this file contains a bunch of methods to create special matrices (such as the [identity matrix](module_model/shared/src/main/scala/me/shreyasr/graphics/Mat.scala#L169), a [translation matrix](module_model/shared/src/main/scala/me/shreyasr/graphics/Mat.scala#L121), and a [perspective projection matrix](module_model/shared/src/main/scala/me/shreyasr/graphics/Mat.scala#L90), among others).

 * [Engine.scala](module_model/shared/src/main/scala/me/shreyasr/graphics/Engine.scala)

This object contains all the methods to apply transformations onto an array of vectors.

These classes have tests: [VectorTest.scala](module_model/shared/src/test/scala/VectorTest.scala) and [MatrixTest.scala](module_model/shared/src/test/scala/MatrixTest.scala).

 * [App.scala](module_frontend/src/main/scala/me/shreyasr/graphics/App.scala)

This is the code for the actual website.
