package me.shreyasr.graphics

class Engine {

  def execute(modelCoords: Array[Vec], translateVec: Vec, scaleVec: Vec, rotateVec: Vec) = {
    val modelToWorld = Mat.scale(scaleVec) * Mat.rotate(rotateVec) * Mat.translation(translateVec)
    val worldCoords = modelCoords.map(v => {
      modelToWorld * v
    })
  }
}
