package tdk
import scala.math.{hypot, pow}

class Monster(val x: Int, val y: Int, val speed: Int, val hp: Int, val location: Int) extends Entity {
  
  val bounty = hp*10
  val p = Path.path
  val pLength = p.length
  
  def newx = if (p.indices contains location) p(location).x else x
  def newy = if (p.indices contains location) p(location).y else y
  def takeDamage(projDamage: Int): Option[Monster] = {
    if (projDamage < hp) Some(new Monster(x,y,speed,hp - projDamage, location)) else None
  }
  
  def advance: Option[Monster] = {
    
    if (location > pLength) { 
      World.decreaseHp(1)
      None
    } else Some(new Monster(newx, newy, speed, hp, location + speed))
  }
  
}

class NormalMonster(x: Int, y: Int, location: Int) extends Monster(x,y,2,2,location) 

class FastMonster(x: Int, y: Int, location: Int) extends Monster(x,y,5,1,location)

class TankMonster(x: Int, y: Int, location: Int) extends Monster(x,y,1,10,location)