package me.shreyasr.graphics

import scala.collection.TraversableLike
import scala.collection.generic.{CanBuildFrom, GenericTraversableTemplate, TraversableFactory}
import scala.collection.mutable.ListBuffer

object Vector extends TraversableFactory[Vector] {

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, Vector[A]] = new GenericCanBuildFrom[A]
  def newBuilder[A] = new ListBuffer[A].mapResult(x => new Vector[A](x:_*))
}

class Vector[T] private(private val values: T*)
  extends Traversable[T]
    with GenericTraversableTemplate[T, Vector]
    with TraversableLike[T, Vector[T]] {

  override def companion = Vector


  def apply(index: Int): T = values(index)
  def length: Int = values.length

  override def foreach[U](f: (T) => U): Unit = values.foreach(f)

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case that: Vector[T] => this.length == that.length &&
        (this, that.toIterable).zipped.forall(== _)
      case _ => false
    }
  }
}
