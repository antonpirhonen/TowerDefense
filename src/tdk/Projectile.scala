package tdk

import scala.math.{sin, cos}
/* Direction is given as an angle from 0 to 2*Pi
 * 
 * */
class Projectile(val x: Int, val y: Int, val speed: Int, val direction: Double) extends Entity {
  
  def advance: Option[Projectile] = {
    val newX = x + (speed*cos(direction)).toInt
    val newY = y + (speed*sin(direction)).toInt
    if (newX > GUI.width || newX < 0 || newY > GUI.height || newY < 0) {
      None
    } else Some(new Projectile(newX, newY, speed, direction))
  }
  
}

object Projectile {
  val size = 15
}