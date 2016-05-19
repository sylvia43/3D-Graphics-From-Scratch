import utest._

object VectorTest extends TestSuite {

  override def tests = TestSuite {
    'VectorInitialization {
      val vector = Vector(1, 2, 3)
      assert(vector(0) == 1)
    }
    'VectorEquality {
      val vector = Vector(1, 2, 3)
      assert(vector == Vector(1, 2, 3))
      assert(vector != Vector(0, 2, 3))
      assert(vector != Vector(1, 2, 3, 4))
    }
    'VectorTraversable {
      val vector = Vector(1, 2, 3)
      assert(vector.map(_ + 3) == Vector(4, 5, 6))
    }
  }
}
