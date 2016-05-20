package me.shreyasr.graphics

import scala.collection.TraversableLike
import scala.collection.generic.{CanBuildFrom, GenericTraversableTemplate, TraversableFactory}
import scala.collection.mutable.ListBuffer

object Vec extends TraversableFactory[Vec] {

  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, Vec[A]] = new GenericCanBuildFrom[A]
  def newBuilder[A] = new ListBuffer[A].mapResult(x => new Vec[A](x:_*))
}

class Vec[T] private(private val values: T*)
  extends Traversable[T]
    with GenericTraversableTemplate[T, Vec]
    with TraversableLike[T, Vec[T]] {

  override def companion = Vec

  def apply(index: Int): T = values(index)
  def length: Int = values.length

  override def foreach[U](f: (T) => U): Unit = values.foreach(f)

  override def equals(obj: scala.Any): Boolean = {
    obj match {
      case that: Vec[T] => this.length == that.length &&
        (this, that.toIterable).zipped.forall(== _)
      case _ => false
    }
  }
}
