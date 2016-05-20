import me.shreyasr.graphics.{Engine, Vec}
import utest._

object EngineTest extends TestSuite {

  val engine = new Engine()

  override def tests = TestSuite {
    val screenVec = engine.execute(
      Array(Vec(0, 0, -50, 1)), // starting vector
      Vec(0, 0, 0), // translate
      Vec(1, 1, 1), // scale
      Vec(0, 0, 0), // rotate
      100, 100) // screen coords
      .head
    println(screenVec)
  }
}
