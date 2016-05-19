import me.shreyasr.graphics.Matrix
import utest._

object MatrixTest extends TestSuite {

  def tests = TestSuite {
    'MatrixInitialization {
      val matrix = Matrix((1, 2, 3), (4, 5, 6))
      assert(matrix.width == 3)
      assert(matrix.width * matrix.height == 6)
    }
    'MatrixTraversable {
      val matrix = Matrix[Int]((1, 2, 3), (4, 5, 6))
      println(matrix)
      println(matrix.transpose)
      println(matrix.map[String](i => s"Val<$i>"))
      println(matrix.map[Int](i => i + 3))
      println(matrix.map((i: Int) => i + 3))
    }
  }
}