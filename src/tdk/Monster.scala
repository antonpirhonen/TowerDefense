package tdk
import scala.math.{hypot, pow}

class Monster(val x: Int, val y: Int, val speed: Int, val location: Int) extends Entity {
  
  val p = Path.path
  def newx = if (p.indices contains location) p(location).x else x
  def newy = if (p.indices contains location) p(location).y else y
  
  def advance = {
    val damage = World.projectiles.exists(proj => Tools.dist(this.x, this.y, proj.x, proj.y) < 1)
    
    new Monster(newx, newy, speed, location + speed)
  }
  
}