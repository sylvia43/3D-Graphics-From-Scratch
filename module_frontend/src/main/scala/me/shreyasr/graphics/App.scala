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
    canvas.style.background = "#0000FF"
    val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    timers.setInterval(100)(() -> update(ctx))
  }

  def update(g: CanvasRenderingContext2D): Unit = {
    g.canvas.width = window.innerWidth
    g.canvas.height = window.innerHeight

    g.fillStyle="#FF0000"
    g.strokeStyle="#00FF00"
    g.lineWidth=50
    g.fill()
    g.beginPath()
    g.moveTo(0, 0)
    g.lineTo(0, 400)
    g.lineTo(400, 400)
    g.lineTo(400, 0)
    g.lineTo(0, 0)
    g.stroke()
  }
}
