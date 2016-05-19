import me.shreyasr.graphics.Matrix
import utest._

object MatrixTest extends TestSuite {

  def tests = TestSuite {
    'MatrixInitialization {
      val matrix = Matrix((1, 2, 3), (4, 5, 6))
      assert(matrix.cols == 3)
      assert(matrix.cols * matrix.rows == 6)
    }
    'MatrixTraversable {
      val matrix = Matrix[Int]((1, 2, 3), (4, 5, 6))
      println(matrix)
      println(matrix.transpose)
      println(matrix.map[String](i => s"Val<$i>"))
      println(matrix.map[Int](i => i + 3))
      println(matrix.map((i: Int) => i + 3))
      println()
    }
    'MatrixMult {
      val matrix1 = Matrix[Int]((1, 0, 0), (0, 1, 0), (0, 0, 1))
      val matrix2 = Matrix[Int]((1, 2, 3), (4, 5, 6), (7, 8, 9))
      println(matrix1 * matrix2)
    }
  }
}