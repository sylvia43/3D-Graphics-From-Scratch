import me.shreyasr.graphics.Matrix
import utest._

object MatrixTest extends TestSuite {

  def tests = TestSuite {
    'MatrixInitialization {
      val matrix = Matrix((1, 2, 3), (4, 5, 6))
      assert(matrix.width == 3)
      assert(matrix.width * matrix.height == 6)
    }
  }
}