package tdk

import scala.math.{hypot, pow, sqrt}

object Tools {
  
  def dist(x1: Int, y1: Int, x2: Int, y2: Int): Double = {
    sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2))
  }
  
  
  
}