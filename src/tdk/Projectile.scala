package tdk

import scala.math.{sin, cos}
/* Direction is given as an angle from 0 to 2*Pi
 * 
 * */
class Projectile(val x: Int, val y: Int, val speed: Int, val direction: Double) extends Entity {
  
  def advance: Projectile = {
    val newX = x + (speed*cos(direction)).toInt
    val newY = y + (speed*sin(direction)).toInt
    new Projectile(newX, newY, speed, direction)
  }
  
}