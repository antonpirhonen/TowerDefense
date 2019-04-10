package tdk

import scala.math.{hypot, pow}

object Tools {
  def dist(x1: Int, y1: Int, x2: Int, y2: Int): Double = {
    hypot(pow(2,x1 - x2), pow(2, y1 - y2))
  }
}