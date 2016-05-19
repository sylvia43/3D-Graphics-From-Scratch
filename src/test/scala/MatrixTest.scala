import me.shreyasr.graphics.Matrix
import utest._

import scala.reflect.ClassTag

object MatrixTest extends TestSuite {

  def tests = TestSuite {
    'MatrixInitialization {
      val matrix = Matrix((1, 2, 3), (4, 5, 6))
      assert(matrix.width == 3)
      assert(matrix.width * matrix.height == 6)
    }
    'MatrixTraversable {
      val matrix: Matrix[Int] = Matrix[Int]((1, 2, 3), (4, 5, 6))
      var cnt = 0
      matrix.map(_ + 1)
      println(matrix)
      println(ClassTag(matrix.map(_ + 1).getClass))
    }
  }
}