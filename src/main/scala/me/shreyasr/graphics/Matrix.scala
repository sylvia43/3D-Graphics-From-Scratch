package me.shreyasr.graphics

import scala.collection.generic.{CanBuildFrom, GenericTraversableTemplate, TraversableFactory}
import scala.collection.{TraversableLike, mutable}
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
    new Matrix(rows.flatMap(_.productIterator).map(_.asInstanceOf[T]).toList, width)
  }
}

class Matrix[T] private(private val values: List[T], private val _width: Int)
  extends Traversable[T]
    with GenericTraversableTemplate[T, Matrix]
    with TraversableLike[T, Matrix[T]] {

  override def companion = new TraversableFactory[Matrix] {
    implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, Matrix[A]] =
      new CanBuildFrom[Matrix[_], A, Matrix[A]] {
        def apply(): mutable.Builder[A, Matrix[A]] = {
          println("apply empty")
          newBuilder[A]
        }
        def apply(from: Matrix[_]): mutable.Builder[A, Matrix[A]] = {
          println("apply on matrix")
          newBuilder[A](from.width)
        }
      }

    override def newBuilder[A]: mutable.Builder[A, Matrix[A]] = newBuilder[A](1)

    def newBuilder[A](width: Int) = new mutable.LazyBuilder[A, Matrix[A]] {
      def result = {
        new Matrix(parts.flatten.toList, parts.size)
      }
    }
  }

  require(values != null, "Matrix values cannot be null!")
  require(values.nonEmpty, "Matrix values cannot be empty!")
  require(values.length % width == 0, s"Not rectangular: ${values.length} values with width: $width")

  def width = _width
  def height = values.length / _width

  def apply(row: Int, col: Int):T = values(row*width + col)

  override def foreach[U](f: (T) => U): Unit = values.foreach(f)

  override def toString(): String = values.sliding(width, width).map(_.mkString(",")).mkString("\n")
}
