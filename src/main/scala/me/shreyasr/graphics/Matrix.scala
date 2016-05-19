package me.shreyasr.graphics

import scala.reflect.ClassTag

object Matrix {

  /*
//  implicit def canBuildFrom[A]: CanBuildFrom[Matrix[_], A, Matrix[A]] = new GenericCanBuildFrom[A]
  implicit def canBuildFrom[A]: CanBuildFrom[Matrix[_], A, Matrix[A]] =
    new CanBuildFrom[Matrix[_], A, Matrix[A]] {
      def apply(): mutable.Builder[A, Matrix[A]] = newBuilder
      def apply(from: Matrix[_]): mutable.Builder[A, Matrix[A]] = newBuilder(from.width)
    }



  def newBuilder[A](width: Int) = new mutable.LazyBuilder[A, Matrix[A]] {
    def result = {
      new Matrix(parts.flatten.toArray, width)
    }
  }

  def newBuilder[A] = newBuilder[A](1)
  */

  def apply[T: ClassTag](rows: Product*): Matrix[T] = {
    require(rows.nonEmpty, "Matrix must have rows!")
    val width = rows.head.productArity
    require(rows.forall(_.productArity == width), "Row must have same length")
    new Matrix(rows.flatMap(_.productIterator).map(_.asInstanceOf[T]).toArray, width)
  }
}

class Matrix[T: ClassTag] private(private val values: Array[T], private val _width: Int) extends Traversable[T] {

  require(values != null, "Matrix values cannot be null!")
  require(values.nonEmpty, "Matrix values cannot be empty!")
  require(values.length % width == 0, s"Not rectangular: ${values.length} values with width: $width")

  def width: Int = _width
  def height: Int = values.length / _width

  def apply(row: Int, col: Int): T = values(row*width + col)

  override def foreach[U](f: T => U): Unit = values.foreach(f)
  def iterator: Iterator[T] = values.iterator
  def map[A: ClassTag](f: T => A): Matrix[A] = new Matrix(values.map(f).array, width)

  def transpose: Matrix[T] =
    new Matrix(Array.tabulate(height, width)((r, c) => apply(r, c)).transpose.flatten, height)

  override def toString(): String = values.sliding(width, width).map(_.mkString(",")).mkString("\n")
}
