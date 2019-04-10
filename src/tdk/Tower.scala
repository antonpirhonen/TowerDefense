package tdk

import scala.math.Pi

class Tower(val x: Int, val y: Int, val firePerSec: Double, val range: Int) extends Entity {
  
  var lastShot = 0L
  val cooldownSec = 1.0/firePerSec
  
  
  def shoot: Option[Projectile] = {
    if (World.time - lastShot > cooldownSec*10E8) {
      lastShot = World.time
      Some(new Projectile(x,y,20,1.5*Pi))
    } else None
  }
  
  
  
  
}