package me.shreyasr.graphics

object Matrix {

  def translation(vec: Vec): Matrix = {
    var matrix = identity(vec.size+1)
    (0 until vec.size).foreach(i => {
      matrix = matrix.set(i, matrix.rows-1)(vec(i))
    })
    matrix
  }

  def identity(size: Int): Matrix = {
    new Matrix((0 until size).flatMap(row => {
      (0 until size).map(col => {
        if (row == col) 1f else 0f
      })
    }).toArray, size)
  }

  def apply(rows: Product*): Matrix = {
    require(rows.nonEmpty, "Matrix must have rows!")
    val width = rows.head.productArity
    require(rows.forall(_.productArity == width), "Row must have same length")
    new Matrix(rows.flatMap(_.productIterator).map {
      case i: Int => i.toFloat
      case f: Float => f
      case _ => 0f
    }.toArray, width)
  }
}

class Matrix private(private val values: Array[Float], private val _cols: Int) {

  require(values != null, "Matrix values cannot be null!")
  require(values.nonEmpty, "Matrix values cannot be empty!")
  require(values.length % cols == 0, s"Not rectangular: ${values.length} values with width: $cols")

  def cols: Int = _cols
  def rows: Int = values.length / _cols

  def apply(row: Int, col: Int): Float = values(index(row, col))
  def index(row: Int, col: Int): Int = row*cols + col
  def set(row: Int, col: Int)(float: Float) = new Matrix(values.updated(index(row, col), float), cols)

  def length: Int = values.length
  def foreach[U](f: Float => U): Unit = values.foreach(f)
  def iterator: Iterator[Float] = values.iterator
  def map(f: Float => Float): Matrix = new Matrix(values.map(f).array, cols)

  def transpose: Matrix =
    new Matrix(Array.tabulate(rows, cols)((r, c) => apply(r, c)).transpose.flatten, rows)

  def *(that: Matrix): Matrix = {
    if (this.cols != that.rows) throw new IllegalArgumentException("Invalid dimensions!")
    new Matrix(
      (0 until this.rows).flatMap(row => {
        (0 until that.cols).map(col => {
          (0 until this.cols).foldLeft(0f)((accum, next) => {
            accum + this(row, next) * that(next, col)
          })
        })
      }).toArray, this.rows)
  }

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case that: Matrix =>
        this.rows == that.rows && this.cols == that.cols &&
          (this.values, that.values).zipped.forall(_ == _)
      case _ => false
    }
  }

  override def toString: String = values.sliding(cols, cols).map(_.mkString(",")).mkString("\n")
}
