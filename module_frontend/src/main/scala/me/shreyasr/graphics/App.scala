package me.shreyasr.graphics

import org.scalajs.dom.window
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.html.Canvas
import org.scalajs.jquery.jQuery

import scala.scalajs.js.{JSApp, timers}

object App extends JSApp {

  def main(): Unit = {
    jQuery(setupUi _)
  }

  def setupUi(): Unit = {
    val canvas = jQuery("canvas").get(0).asInstanceOf[Canvas]
    canvas.style.background = "#660000"
    val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    timers.setInterval(100)(() -> update(ctx))
  }

  val engine = new Engine
  val worldPoints: Array[Vec] = (1 to 60)
    .map(i => Vec(math.random.toFloat*200-100, math.random.toFloat*200-100, -math.random.toFloat*100, 1)).toArray

  var rotation = 0f

  val getDelta = {
    var lastTime = System.currentTimeMillis()
    () => { val diff = System.currentTimeMillis() - lastTime; lastTime += diff; diff/16f }
  }

  def update(g: CanvasRenderingContext2D): Unit = {
    val delta = getDelta()

    g.canvas.width = window.innerWidth
    g.canvas.height = window.innerHeight

    val screenPoints = engine.execute(worldPoints,
      Vec(0, 0, 0), // translate
      Vec(1, 1, 1), // scale
      Vec(rotation, rotation, rotation), // rotate
      400, 400, 0, 100,
      g.canvas.width, g.canvas.height) // screen coords)

    rotation += 0.006f * delta
    println(screenPoints.length, screenPoints.sliding(3).next().mkString(","))

//    g.lineWidth=50
//    g.fill()
    screenPoints.sliding(3, 3).foreach((vecs: Array[Vec]) => {
      g.fillStyle=s"rgb(0,0,${(vecs.foldLeft(0f)((a, vec) => a + vec.z)*255/3).toInt})"
      g.beginPath()
      g.moveTo(vecs.last.x, vecs.last.y)
      vecs.foreach(vec => g.lineTo(vec.x, vec.y))
      g.fill()
    })
  }
}
