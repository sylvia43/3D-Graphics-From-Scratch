package me.shreyasr.graphics

import scala.reflect.ClassTag

object Vector {

  def apply[T: ClassTag](values: T*) = new Vector(values.toArray)
}

class Vector[T] private(private val values: Array[T]) {

  def apply(index:Int): T = values(index)
  def length: Int = values.length
}
