package me.shreyasr.graphics

object Vec {

  def apply(values: Float*) = new Vec(values :_*)
}

class Vec private(private val values: Float*) {

  def get(index: Int) = apply(index)
  def apply(index: Int): Float = values(index)
  def size: Int = values.size

  def foreach[U](f: Float => U): Unit = values.foreach(f)
  def map(f: Float => Float): Vec = new Vec(values.map(f) :_*)

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case that: Vec => this.size == that.size && (this.values, that.values).zipped.forall(_ == _)
      case _ => false
    }
  }

  override def toString: String = values.mkString("[", ",", "]")
}
