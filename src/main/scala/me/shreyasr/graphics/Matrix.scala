package me.shreyasr.graphics

object Matrix {

  def apply(rows: Product*) = {
    require(rows.nonEmpty, "Matrix must have rows!")
    val width = rows.head.productArity
    require(rows.forall(_.productArity == width), "Row must have same length")
    println(rows.flatMap(_.productIterator))
    new Matrix(rows.flatMap(_.productIterator).toArray, width)
  }
}

class Matrix[T] private(private val values: Array[T], private val _width: Int) {

  require(values != null, "Matrix values cannot be null!")
  require(values.length > 0, "Matrix values cannot be empty!")
  require(values.length % width == 0, s"Not rectangular: ${values.length} values with width: $width")

  def width = _width
  def height = values.length / _width

  def apply(row: Int, col: Int):T = values(row*width + col)
}
