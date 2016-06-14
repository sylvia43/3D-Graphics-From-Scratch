package me.shreyasr.graphics

import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, Event}
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw.{KeyboardEvent, XMLHttpRequest}
import org.scalajs.jquery.jQuery

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import scala.scalajs.js.{JSApp, timers}

object App extends JSApp {

  def main(): Unit = {
    jQuery(setupUi _)
  }

  lazy val canvas = {
    val c = jQuery("canvas").get(0).asInstanceOf[Canvas]
    c.onkeydown = onKeyDown _
    c.style.background = "#660000"
    c
  }

  def setupUi(): Unit = {
    canvas.focus()
  }

  val getDelta = {
    var lastTime = System.currentTimeMillis()
    () => { val diff = System.currentTimeMillis() - lastTime; lastTime += diff; diff/16f }
  }

  val engine = new Engine

  var fileData = ""
  val request = new XMLHttpRequest()
  request.open("GET", "http://localhost:8080/teapot.obj", async = true)
  request.send(null)
  request.onreadystatechange = (e: Event) => {
    if (request.readyState == 4) {
        timers.setInterval(16)(() -> update(canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]))
//      dom.window.alert(request.responseText)
    }
  }
  lazy val rawPoints: Array[(Vec, Int)] =
    request.responseText.split("\n")
      .filter(_.startsWith("v  "))
      .map(line => {
        Vec(line.split("  ").tail.map(_.toFloat) :+ 1f)
      })
      .zipWithIndex

  lazy val normals: Array[Vec] =
    request.responseText.split("\n")
      .filter(_.startsWith("vn  "))
      .map(line => {
        Vec(line.split("  ").tail.map(_.toFloat))
      })

  lazy val worldPoints: Array[(Vec, Vec)] =
    request.responseText.split("\n")
      .filter(_.startsWith("f  "))
      .flatMap(line => line.split("  ").tail.map(_.toInt))
      .map(index => {
        (rawPoints(index-1)._1, normals(rawPoints(index-1)._2))
      })

  val turnSpeed = 0.009f
  val moveSpeed = 0.3f

  var delta = 0f
  var rotationX = 0f
  var rotationY = 0f
  var rotationZ = 0f
  var translateX = 0f
  var translateY = 0f
  var translateZ = 100f

  var stroke = false
  var fill = true

  def onKeyDown(e: KeyboardEvent): Unit = {
    e.keyCode match {
      case KeyCode.Left => rotationY += turnSpeed*delta
      case KeyCode.Right => rotationY -= turnSpeed*delta
      case KeyCode.Up => rotationX += turnSpeed*delta
      case KeyCode.Down => rotationX -= turnSpeed*delta
      case KeyCode.A => translateX += moveSpeed*delta
      case KeyCode.D => translateX -= moveSpeed*delta
      case KeyCode.W => translateZ += moveSpeed*delta
      case KeyCode.S => translateZ -= moveSpeed*delta
      case KeyCode.Space => translateY += moveSpeed*delta
      case KeyCode.Shift => translateY -= moveSpeed*delta
      case KeyCode.F => fill = !fill
      case KeyCode.M => stroke = !stroke
      case _ =>
    }
  }

  def update(g: CanvasRenderingContext2D): Unit = {
    delta = getDelta()

    g.canvas.width = dom.window.innerWidth
    g.canvas.height = dom.window.innerHeight

//    var time = System.nanoTime()

    val screenPoints = engine.execute(worldPoints,
      Vec(translateX, translateY, translateZ), // translate
      Vec(1, 1, 1), // scale
      Vec(rotationX, rotationY, rotationZ), // rotate
      90, 90, 1, 1000,
      g.canvas.width, g.canvas.height) // screen coords

//    println(s"math   ${(System.nanoTime() - time)/1000f/1000f}")
//    time = System.nanoTime()

    screenPoints
      .sliding(3, 3).toSeq
        .sortWith((left, right) => {
          left.map(_._1).foldLeft(0f)((a, v) => if (v.z > a) v.z else a) <
          right.map(_._1).foldLeft(0f)((a, v) => if (v.z > a) v.z else a)
        })
      .foreach((vecs: Array[(Vec, Vec)]) => {
      g.fillStyle=s"rgb(0,0,${(vecs.foldLeft(0f)((a: Float, vn: (Vec, Vec)) => a + vn._2.y+vn._2.x+2)/6/2*255).toInt})"
      g.beginPath()
      g.moveTo(vecs.last._1.x, vecs.last._1.y)
      vecs.foreach(vec => g.lineTo(vec._1.x, vec._1.y))
      if (fill) g.fill()
      if (stroke) g.stroke()
    })

//    println(s"render ${(System.nanoTime() - time)/1000f/1000f}")
  }

  def log(obj: js.Any) = g.console.log(obj)
}
