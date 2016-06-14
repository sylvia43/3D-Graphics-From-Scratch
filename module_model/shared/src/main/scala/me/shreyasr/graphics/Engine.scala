package me.shreyasr.graphics

class Engine {

  def execute(modelCoords: Array[(Vec, Vec)], translateVec: Vec, scaleVec: Vec, rotateVec: Vec,
              fovx: Float, fovy: Float, near: Int, far: Int,
              screenWidth: Int, screenHeight: Int): Array[(Vec, Vec)] = {
    val worldCoords = modelToWorld(modelCoords, translateVec, scaleVec, rotateVec)
    val projectionCoords = worldToProjection(worldCoords, fovx, fovy, near, far)
    val screenCoords = projectionToScreen(projectionCoords, screenWidth, screenHeight)
    screenCoords
  }

  def modelToWorld(modelCoords: Array[(Vec, Vec)],
                   translateVec: Vec, scaleVec: Vec, rotateVec: Vec): Array[(Vec, Vec)] = {
    val modelToWorldTransform = Mat.translation(translateVec) * Mat.rotate(rotateVec) * Mat.scale(scaleVec)
    modelCoords.map { case(v, n) => (modelToWorldTransform * v, n) }
  }

  def worldToProjection(worldCoords: Array[(Vec, Vec)], fovx: Float, fovy: Float,
                        near: Float, far: Float): Array[(Vec, Vec)] = {
//    println(worldCoords.head)
    val worldToProjectionTransform =
      Mat.perspective(math.toRadians(fovx).toFloat, math.toRadians(fovy).toFloat, near, far)
    worldCoords.map { case(v, n) => (worldToProjectionTransform * v, n) }
      .map { case(vec, n) => (if (vec.w != 1) vec / vec.w else vec, n) } // w normalization for frustum
  }

  def projectionToScreen(projectionCoords: Array[(Vec, Vec)],
                         screenWidth: Int, screenHeight: Int): Array[(Vec, Vec)] = {
//    println(projectionCoords.head)
    projectionCoords
      .map { case(v, n) => ((v + 1) / 2, n) } // map between 0 and 1
        .sliding(3, 3)
        .filter(_.exists { case(v, n) =>  v.x > 0 && v.x < 1 && v.z > 0 })
      .flatten
//      .filterNot(v => v.x < 0 || v.x > 1) // >= one component outside of unit cube
      .map { case(v, n) => (v scalar Vec(screenWidth, screenHeight), n) } // scale to screen size
      .toArray
  }
}
