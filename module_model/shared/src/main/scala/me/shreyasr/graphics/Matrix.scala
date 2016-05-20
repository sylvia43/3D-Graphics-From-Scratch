package me.shreyasr.graphics

import me.shreyasr.graphics.Matrix.ElementMath

import scala.reflect.ClassTag

object Matrix {

  def translation[T: ClassTag](vec: Vec[T]): Matrix[T] = {
    val matrix = identity[T](vec.size+1)
    (0 to vec.size).foreach(i => {
      matrix(matrix.rows, i) == vec(i)
    })
    matrix
  }

  def identity[T: ClassTag](size: Int)(implicit math: ElementMath[T]): Matrix[T] = {
    new Matrix((0 until size).flatMap(row => {
      (0 until size).map(col => {
        if (row == col) math.one else math.zero
      })
    }).toArray, size)
  }

  def apply[T: ClassTag](rows: Product*): Matrix[T] = {
    require(rows.nonEmpty, "Matrix must have rows!")
    val width = rows.head.productArity
    require(rows.forall(_.productArity == width), "Row must have same length")
    new Matrix(rows.flatMap(_.productIterator).map(_.asInstanceOf[T]).toArray, width)
  }

  trait ElementMath[T] {
    def add(a: T, b: T): T
    def times(a: T, b: T): T
    def zero: T
    def one: T
  }

  implicit object ElementMathInt extends ElementMath[Int] {
    override def times(a: Int, b: Int): Int = a*b
    override def add(a: Int, b: Int): Int = a+b
    override def zero: Int = 0
    override def one: Int = 1
  }

  implicit object ElementMathFloat extends ElementMath[Float] {
    override def times(a: Float, b: Float): Float = a*b
    override def add(a: Float, b: Float): Float = a+b
    override def zero: Float = 0f
    override def one: Float = 1f
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

  def *(that: Matrix[T])(implicit math: ElementMath[T]): Matrix[T] = {
    if (this.cols != that.rows) throw new IllegalArgumentException("Invalid dimensions!")
    new Matrix(
      (0 until this.rows).flatMap(row => {
        (0 until that.cols).map(col => {
          (0 until this.cols).foldLeft[T](math.zero)((accum, next) => {
            math.add(accum, math.times(this(row, next), that(next, col)))
          })
        })
      }).toArray, this.rows)
  }

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case that: Matrix[T] =>
        this.rows == that.rows && this.cols == that.cols &&
          (this, that.toIterable).zipped.forall(_ == _)
      case _ => false
    }
  }

  override def toString(): String = values.sliding(cols, cols).map(_.mkString(",")).mkString("\n")
}
