package me.shreyasr.graphics

object Vec {

  def apply(values: Float*) = new Vec(values :_*)
}

class Vec private(private val values: Float*) {

  def /(f: Float) = this.map(_ / f)
  def +(n: Int) = this.map(_ + n)
  def scalar(v: Vec): Vec = {
    require(v.length < this.length)
    val nextOrOne = {
      val iter = v.iterator
      () => { if (iter.hasNext) iter.next() else 1 }
    }
    this.map(_ * nextOrOne())
  }

  def get(index: Int) = apply(index)
  def apply(index: Int): Float = values(index)
  def length: Int = values.length

  def foreach[U](f: Float => U): Unit = values.foreach(f)
  def map(f: Float => Float): Vec = new Vec(values.map(f) :_*)
  def iterator = values.iterator

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case that: Vec => this.length == that.length && (this.values, that.values).zipped.forall(_ == _)
      case _ => false
    }
  }

  override def toString: String = values.mkString("[", ",", "]")
}
