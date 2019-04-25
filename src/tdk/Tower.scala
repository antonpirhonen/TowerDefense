package tdk

import scala.math.{Pi, hypot, atan}

class Tower(val x: Int, val y: Int, val firePerSec: Double, val range: Int) extends Entity {
  
  var lastShot = 0L
  val cooldownSec = 1.0/firePerSec
  
  
  def shoot: Option[Projectile] = {
    
    val enemiesInRange = World.monsters.filter(mon => hypot(x - mon.x, y - mon.y) < range)
    val furthestEnemyInRange = if (enemiesInRange.nonEmpty) Some(enemiesInRange.maxBy(_.location)) else None
    
    if (World.time - lastShot > cooldownSec*10E8 && enemiesInRange.nonEmpty) {
      lastShot = World.time
      val m = furthestEnemyInRange.get
      
      val dy = m.y - this.y
      val dx = m.x - this.x
      var angle = atan(dy.toDouble/dx)
      
      if (angle >= 0 ) {
        if (dy < 0 ) angle = angle + Pi
      } else {
        if (dy > 0) {
          angle = angle + Pi
        }
      }
      
      Some(new Projectile(x,y,20,angle))
    } else None
  }

}

class BasicTower(x: Int, y: Int) extends Tower(x,y,1,100) 

object BasicTower extends TowerType {
  val size = 30
  val price = 500
}

class MachinegunTower(x: Int, y: Int) extends Tower(x,y,5,100)

object MachinegunTower extends TowerType {
  val size = 30
  val price = 1000
}

trait TowerType {
  val price: Int
}


