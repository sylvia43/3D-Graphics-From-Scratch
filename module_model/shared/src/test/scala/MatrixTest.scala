import me.shreyasr.graphics.{Mat, Vec}
import utest._

object MatrixTest extends TestSuite {

  def tests = TestSuite {
    'MatrixInitialization {
      val matrix = Mat((1, 2, 3), (4, 5, 6))
      assert(matrix.rows == 2)
      assert(matrix.cols == 3)
      assert(matrix.cols * matrix.rows == 6)
      assert(matrix.iterator.toArray sameElements Array(1, 2, 3, 4, 5, 6))
      intercept[IllegalArgumentException](Mat((3,4,5), (2, 3)))
    }
    'MatrixEquality {
      val matrix = Mat((1,2,3), (4,5,6))
      assert(matrix == Mat((1,2,3), (4,5,6)))
      assert(matrix != Mat((0,2,3), (4,5,6)))
      assert(matrix != Mat((1,2,3,4), (5,6,7,8)))
      assert(matrix != Mat((1,2), (3,4)))
    }
    'MatrixTraversable {
      val matrix = Mat((1, 2, 3), (4, 5, 6))
      assert(matrix.transpose == Mat((1, 4), (2, 5), (3, 6)))
      assert(matrix.map((i: Float) => i+3) == Mat((4, 5, 6), (7, 8, 9)))
    }
    'MatrixMult {
      val identity = Mat((1, 0, 0), (0, 1, 0), (0, 0, 1))
      val matrix = Mat((1, 2, 3), (4, 5, 6), (7, 8, 9))
      assert(identity * matrix == matrix)
      assert(matrix * matrix == Mat((30, 36, 42), (66, 81, 96), (102, 126, 150)))
      assert(Mat(Tuple1(4), Tuple1(3)) * Mat((2, 1))
        == Mat((8, 4), (6, 3)))
    }
    'SpecialMatrixConstruction {
      assert(Mat.identity(3) == Mat((1, 0, 0), (0, 1, 0), (0, 0, 1)))
      assert(Mat.identity(4) == Mat((1, 0, 0, 0), (0, 1, 0, 0), (0, 0, 1, 0), (0, 0, 0, 1)))
      assert(Mat.translation(Vec(1,2,3)) == Mat((1, 0, 0, 1), (0, 1, 0, 2), (0, 0, 1, 3), (0, 0, 0, 1)))
      assert(Mat.scale(Vec(2,3,4)) == Mat((2, 0, 0, 0), (0, 3, 0, 0), (0, 0, 4, 0), (0, 0, 0, 1)))
      assert(Mat.rotate(0)(30) == Mat(
        (1, 0, 0, 0),
        (0, math.cos(30), -math.sin(30), 0),
        (0, math.sin(30), math.cos(30), 0),
        (0, 0, 0, 1)))
      assert(Mat.rotate(1)(60) == Mat(
        (math.cos(60), 0, -math.sin(60), 0),
        (0, 1, 0, 0),
        (math.sin(60), 0, math.cos(60), 0),
        (0, 0, 0, 1)))
      assert(Mat.rotate(2)(45) == Mat(
        (math.cos(45), -math.sin(45), 0, 0),
        (math.sin(45), math.cos(45), 0, 0),
        (0, 0, 1, 0),
        (0, 0, 0, 1)))
    }
  }
}