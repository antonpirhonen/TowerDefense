package tdk

import scala.math.Pi

class Tower(val x: Int, val y: Int ,val firerate: Double) extends Entity {
  
  val shootFreq: Int = (60/firerate).toInt        //In ticks 
  
  def shoot = new Projectile(x,y,20,Pi)
  
}