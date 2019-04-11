package tdk
import scala.math.{hypot, pow}

class Monster(val x: Int, val y: Int, val speed: Int, val location: Int) extends Entity {
  
  val p = Path.path
  val pLength = p.length
  
  def newx = if (p.indices contains location) p(location).x else x
  def newy = if (p.indices contains location) p(location).y else y
  
  def advance: Option[Monster] = {
    def damage: Boolean = { 
      World.projectiles.exists(proj => hypot(this.x - proj.x, this.y - proj.y) < Projectile.size)
    }
    
    if (location > pLength) { 
      World.decreaseHp(1)
      None
    } else if(!damage) Some(new Monster(newx, newy, speed, location + speed)) else None
  }
  
}