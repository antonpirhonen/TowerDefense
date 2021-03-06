package tdk

import scala.math.{Pi, hypot, atan}

class Tower(val x: Int, val y: Int, val firePerSec: Double, val range: Int) extends Entity {
  
  var lastShot = 0L
  val cooldownSec = 1.0/firePerSec
  
  //Tower shoots a projectile at a Monster
  def shoot: Vector[Option[Projectile]] = {
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
      
      SoundManager.playClick()
      Vector(Some(new Projectile(x,y,20,angle)))
    } else Vector(None)
  }

}

class BasicTower(x: Int, y: Int) extends Tower(x,y,1,150) 

object BasicTower extends TowerType {
  val size = 30
  val price = 500
}

class MachinegunTower(x: Int, y: Int) extends Tower(x,y,4,150)

object MachinegunTower extends TowerType {
  val size = 30
  val price = 1500
}

class ShotgunTower(x: Int, y: Int) extends Tower(x,y,0.8,100) {
  override def shoot: Vector[Option[Projectile]] = {
    
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
      
      SoundManager.playClick()
      Vector(Some(new Projectile(x,y,20,angle)), Some(new Projectile(x,y,20,angle + 0.2)), Some(new Projectile(x,y,20,angle - 0.2)))
    } else Vector(None)
  }
}

object ShotgunTower extends TowerType {
  val size = 30
  val price = 1000
}


trait TowerType {
  val price: Int
}


