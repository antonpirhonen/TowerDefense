package tdk
import scala.math.{hypot, pow}

abstract class Monster(val x: Int, val y: Int, val location: Int, val hp: Int) extends Entity {
  
  val bounty: Int
  
  val p = Path.path
  val pLength = p.length
  
  def newx = if (location >= 0 && location < pLength) p(location).x else x
  def newy = if (location >= 0 && location < pLength) p(location).y else y
  
  def takeDamage(projDamage: Int): Option[Monster]
  def advance: Option[Monster] 
}


class NormalMonster(x: Int, y: Int, location: Int, hp: Int = NormalMonster.startHP) extends Monster(x,y,location,hp) {
  
  val bounty = 20
  
  def takeDamage(projDamage: Int): Option[Monster] = {
    if (projDamage < hp) Some(new NormalMonster(x,y, location,hp - projDamage)) else None
  }
  
  def advance: Option[Monster] = {
    if (location > pLength) { 
      World.decreaseHp(NormalMonster.damage)
      None
    } else Some(new NormalMonster(newx, newy, location + NormalMonster.speed, hp))
  }
  
}

object NormalMonster {
  val startHP = 2
  val speed = 2
  val bounty = 20
  val damage = 2
}



class FastMonster(x: Int, y: Int, location: Int, hp: Int = FastMonster.startHP) extends Monster(x,y,location,hp) {
  
  val bounty = 10
  
  def takeDamage(projDamage: Int): Option[Monster] = {
    if (projDamage < hp) Some(new FastMonster(x,y,location,hp - projDamage)) else None
  }
  
  def advance: Option[Monster] = {
    if (location > pLength) { 
      World.decreaseHp(FastMonster.damage)
      None
    } else Some(new FastMonster(newx, newy, location + FastMonster.speed, hp))
  }
}

object FastMonster {
  val startHP = 1
  val speed = 5
  val bounty = 10
  val damage = 1
}


class TankMonster(x: Int, y: Int, location: Int, hp: Int = TankMonster.startHP) extends Monster(x,y,location,hp) {
  
  val bounty = 35
  
  def takeDamage(projDamage: Int): Option[Monster] = {
    if (projDamage < hp) Some(new TankMonster(x,y,location,hp - projDamage)) else None
  }
  
  def advance: Option[Monster] = {
    if (location > pLength) { 
      World.decreaseHp(TankMonster.damage)
      None
    } else Some(new TankMonster(newx, newy, location + TankMonster.speed, hp))
  }
}

object TankMonster {
  val startHP = 10
  val speed = 1
  val bounty = 35
  val damage = 5
}
