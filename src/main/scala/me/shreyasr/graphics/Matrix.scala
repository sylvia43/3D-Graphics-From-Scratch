package me.shreyasr.graphics

import me.shreyasr.graphics.Matrix.MatrixMult

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

  trait MatrixMult[T] {
    def add(a: T, b: T): T
    def times(a: T, b: T): T
    def zero: T
  }

  implicit object MatrixMultInt extends MatrixMult[Int] {
    override def times(a: Int, b: Int): Int = a*b
    override def add(a: Int, b: Int): Int = a+b
    override def zero: Int = 0
  }
}

class Matrix[T: ClassTag] private(private val values: Array[T], private val _width: Int) extends Traversable[T] {

  require(values != null, "Matrix values cannot be null!")
  require(values.nonEmpty, "Matrix values cannot be empty!")
  require(values.length % cols == 0, s"Not rectangular: ${values.length} values with width: $cols")

  def cols: Int = _width
  def rows: Int = values.length / _width

  def apply(row: Int, col: Int): T = values(row*cols + col)

  override def foreach[U](f: T => U): Unit = values.foreach(f)
  def iterator: Iterator[T] = values.iterator
  def map[A: ClassTag](f: T => A): Matrix[A] = new Matrix(values.map(f).array, cols)

  def transpose: Matrix[T] =
    new Matrix(Array.tabulate(rows, cols)((r, c) => apply(r, c)).transpose.flatten, rows)

  def *(that: Matrix[T])(implicit mult: MatrixMult[T]): Matrix[T] = {
    if (this.cols != that.rows) throw new IllegalArgumentException("Invalid dimensions!")
    new Matrix(
      (0 until this.rows).flatMap(row => {
        (0 until that.cols).map(col => {
          println(s" $row, $col")
          var accum = mult.zero
          (0 until this.cols).foreach(k => {
            accum = mult.add(accum, mult.times(this(row, k), that(k, col)))
            println(this(row, k), that(k, col), accum)
          })
          accum
        })
      }).toArray, this.rows)
  }

  override def toString(): String = values.sliding(cols, cols).map(_.mkString(",")).mkString("\n")
}
