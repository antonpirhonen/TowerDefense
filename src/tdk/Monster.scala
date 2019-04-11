package tdk
import scala.math.{hypot, pow}

class Monster(val x: Int, val y: Int, val speed: Int, val location: Int) extends Entity {
  
  val p = Path.path
  val pLength = p.length
  
  def newx = if (p.indices contains location) p(location).x else x
  def newy = if (p.indices contains location) p(location).y else y
  
  def advance: Option[Monster] = {
    
    if (location > pLength) { 
      World.decreaseHp(1)
      None
    } else Some(new Monster(newx, newy, speed, location + speed))
  }
  
}