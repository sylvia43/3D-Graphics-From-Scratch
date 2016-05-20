import me.shreyasr.graphics.{Matrix, Vec}
import utest._

object MatrixTest extends TestSuite {

  def tests = TestSuite {
    'MatrixInitialization {
      val matrix = Matrix((1, 2, 3), (4, 5, 6))
      assert(matrix.rows == 2)
      assert(matrix.cols == 3)
      assert(matrix.cols * matrix.rows == 6)
      assert(matrix.iterator.toArray sameElements Array(1, 2, 3, 4, 5, 6))
      intercept[IllegalArgumentException](Matrix((3,4,5), (2, 3)))
    }
    'MatrixEquality {
      val matrix = Matrix((1,2,3), (4,5,6))
      assert(matrix == Matrix((1,2,3), (4,5,6)))
      assert(matrix != Matrix((0,2,3), (4,5,6)))
      assert(matrix != Matrix((1,2,3,4), (5,6,7,8)))
      assert(matrix != Matrix((1,2), (3,4)))
    }
    'MatrixTraversable {
      val matrix = Matrix((1, 2, 3), (4, 5, 6))
      assert(matrix.transpose == Matrix((1, 4), (2, 5), (3, 6)))
      assert(matrix.map((i: Float) => i+3) == Matrix((4, 5, 6), (7, 8, 9)))
    }
    'MatrixMult {
      val identity = Matrix((1, 0, 0), (0, 1, 0), (0, 0, 1))
      val matrix = Matrix((1, 2, 3), (4, 5, 6), (7, 8, 9))
      assert(identity * matrix == matrix)
      assert(matrix * matrix == Matrix((30, 36, 42), (66, 81, 96), (102, 126, 150)))
      assert(Matrix(Tuple1(4), Tuple1(3)) * Matrix((2, 1))
        == Matrix((8, 4), (6, 3)))
    }
    'SpecialMatrixConstruction {
      assert(Matrix.identity(3) == Matrix((1, 0, 0), (0, 1, 0), (0, 0, 1)))
      assert(Matrix.identity(4) == Matrix((1, 0, 0, 0), (0, 1, 0, 0), (0, 0, 1, 0), (0, 0, 0, 1)))
      assert(Matrix.translation(Vec(1,2,3)) == Matrix((1, 0, 0, 1), (0, 1, 0, 2), (0, 0, 1, 3), (0, 0, 0, 1)))
      assert(Matrix.scale(Vec(2,3,4)) == Matrix((2, 0, 0, 0), (0, 3, 0, 0), (0, 0, 4, 0), (0, 0, 0, 1)))
    }
  }
}