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
  val worldPoints: Array[Vec] = (1 to 50)
    .map(i => Vec(math.random.toFloat*200-100, math.random.toFloat*200-100, -math.random.toFloat*100, 1)).toArray

  var rotation = 0f

  def update(g: CanvasRenderingContext2D): Unit = {
    g.canvas.width = window.innerWidth
    g.canvas.height = window.innerHeight

    val screenPoints = engine.execute(worldPoints,
      Vec(0, 0, 0), // translate
      Vec(1, 1, 1), // scale
      Vec(0, 0, rotation), // rotate
      g.canvas.width, g.canvas.height) // screen coords)

    rotation += 0.1f
    println(screenPoints.length, screenPoints.head)

//    g.lineWidth=50
//    g.fill()
    screenPoints.foreach(vec => {
      g.fillStyle=s"rgb(0,0,${(vec(2)*255).toInt})"
      g.beginPath()
      g.arc(vec(0), vec(1), 10, 0, 2*math.Pi)
      g.fill()
    })
  }
}
