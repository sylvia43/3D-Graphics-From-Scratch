import me.shreyasr.graphics.Matrix
import utest._

object MatrixTest extends TestSuite {

  def tests = TestSuite {
    'MatrixInitialization {
      val matrix = Matrix((1, 2, 3), (4, 5, 6))
      assert(matrix.rows == 2)
      assert(matrix.cols == 3)
      assert(matrix.cols * matrix.rows == 6)
      assert(matrix.iterator.toArray sameElements Array(1, 2, 3, 4, 5, 6))
    }
    'MatrixTraversable {
      val matrix = Matrix[Int]((1, 2, 3), (4, 5, 6))
      assert(matrix.transpose == Matrix[Int]((1, 4), (2, 5), (3, 6)))
      assert(matrix.map[Int](i => i+3) == Matrix[Int]((4, 5, 6), (7, 8, 9)))
    }
    'MatrixMult {
      val identity = Matrix[Int]((1, 0, 0), (0, 1, 0), (0, 0, 1))
      val matrix = Matrix[Int]((1, 2, 3), (4, 5, 6), (7, 8, 9))
      assert(identity * matrix == matrix)
      assert(matrix * matrix == Matrix[Int]((30, 36, 42), (66, 81, 96), (102, 126, 150)))
      assert(Matrix[Int](Tuple1(4), Tuple1(3)) * Matrix[Int]((2, 1))
        == Matrix[Int]((8, 4), (6, 3)))
    }
  }
}