package me.shreyasr.graphics

object Vec {

  // Construct a new vector from vararg parameters
  def apply(values: Float*) = new Vec(values :_*)

  // Construct a new vector from a float array
  def apply(values: Array[Float]) = new Vec(values :_*)
}

/**
  * A vector of arbitrary length represented by an array of values
  *
  * @param values The values of the vector
  */
class Vec private(private val values: Float*) {

  def apply(index: Int) = get(index)
  def get(index: Int) = values(index)
  def length: Int = values.length

  def x = apply(0)
  def y = apply(1)
  def z = apply(2)
  def w = apply(3)

  def *(f: Float) = this.map(_ / f) // multiply this vector by a scalar
  def /(f: Float) = this.map(_ / f) // divide this vector by a scalar
  def +(n: Int) = this.map(_ + n) // add a scalar to all elements of this vector
  def scalar(v: Vec): Vec = { // add a vector to this vector
    require(v.length < this.length)
    val nextOrOne = {
      val iter = v.iterator
      () => { if (iter.hasNext) iter.next() else 1 }
    }
    this.map(_ * nextOrOne())
  }

  def foreach[U](f: Float => U): Unit = values.foreach(f)
  def map(f: Float => Float): Vec = new Vec(values.map(f) :_*)
  def iterator = values.iterator

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case that: Vec => this.length == that.length && (this.values, that.values).zipped.forall(_ == _)
      case _ => false
    }
  }

  override def toString: String = values.map("%.3f".format(_)).mkString("[", ",", "]")
}
