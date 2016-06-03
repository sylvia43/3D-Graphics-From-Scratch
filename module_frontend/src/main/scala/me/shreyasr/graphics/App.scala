package me.shreyasr.graphics

import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw.KeyboardEvent
import org.scalajs.jquery.jQuery

import scala.scalajs.js
import js.Dynamic.{ global => g }
import scala.scalajs.js.{JSApp, timers}

object App extends JSApp {

  def main(): Unit = {
    jQuery(setupUi _)
  }

  def setupUi(): Unit = {
    val canvas = jQuery("canvas").get(0).asInstanceOf[Canvas]
    canvas.onkeydown = onKeyDown _
    canvas.style.background = "#660000"
    val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    timers.setInterval(16)(() -> update(ctx))
  }

  val getDelta = {
    var lastTime = System.currentTimeMillis()
    () => { val diff = System.currentTimeMillis() - lastTime; lastTime += diff; diff/16f }
  }

  val engine = new Engine
  val worldPoints: Array[Vec] = (0 until 1)
    .map(i => Vec(math.random.toFloat*20-10, math.random.toFloat*20-10, -math.random.toFloat*5, 1)).toArray

  val turnSpeed = 0.018f
  val moveSpeed = 0.05f

  var delta = 0f
  var rotationX = 0f
  var rotationY = 0f
  var rotationZ = 0f
  var translateX = 0f
  var translateY = 0f
  var translateZ = 0f

  def onKeyDown(e: KeyboardEvent): Unit = {
//    println("KEY DOWN! " + e.keyCode)

    e.keyCode match {
      case KeyCode.Left => rotationY -= turnSpeed*delta
      case KeyCode.Right => rotationY += turnSpeed*delta
      case KeyCode.Up => rotationX -= turnSpeed*delta
      case KeyCode.Down => rotationX += turnSpeed*delta
      case KeyCode.A => translateX -= moveSpeed*delta
      case KeyCode.D => translateX += moveSpeed*delta
      case KeyCode.W => translateZ += moveSpeed*delta
      case KeyCode.S => translateZ -= moveSpeed*delta
      case KeyCode.Space => translateY += moveSpeed*delta
      case KeyCode.Shift => translateY -= moveSpeed*delta
      case _ =>
    }
  }

  def update(g: CanvasRenderingContext2D): Unit = {
    delta = getDelta()

    g.canvas.width = dom.window.innerWidth
    g.canvas.height = dom.window.innerHeight

    val screenPoints = engine.execute(worldPoints,
      Vec(translateX, translateY, translateZ), // translate
      Vec(1, 1, 1), // scale
      Vec(rotationX, rotationY, rotationZ), // rotate
      90, 90, 1, 5,
      g.canvas.width, g.canvas.height) // screen coords)

//    println(screenPoints.sliding(3).next().map(v => v.z).mkString(","))
    println((screenPoints.head.z * 1000).toInt)

    screenPoints.foreach(vec => {
      g.fillStyle=s"rgb(0,0,${(vec.z*255).toInt})"
      g.beginPath()
      g.arc(vec.x, vec.y, 0f max 3/vec.z, 0, 2*math.Pi)
      g.fill()
    })

    /*
    screenPoints.sliding(3, 3).foreach((vecs: Array[Vec]) => {
//      println(s"${(vecs.foldLeft(0f)((a, vec) => a + vec.z)/3f*255).toInt}")
      g.fillStyle=s"rgb(0,0,${(vecs.foldLeft(0f)((a, vec) => a + (0f max vec.z min 1f))/3f*255).toInt})"
      g.beginPath()
      g.moveTo(vecs.last.x, vecs.last.y)
      vecs.foreach(vec => g.lineTo(vec.x, vec.y))
      g.fill()
    })
    */
  }

  def log(obj: js.Any) = g.console.log(obj)
}
