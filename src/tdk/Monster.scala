package tdk
import scala.math.{hypot, pow}

abstract class Monster(val x: Int, val y: Int, val hp: Int, val location: Int) extends Entity {
  
  val bounty: Int
  
  val p = Path.path
  val pLength = p.length
  
  def newx = if (p.indices contains location) p(location).x else x
  def newy = if (p.indices contains location) p(location).y else y
  def takeDamage(projDamage: Int): Option[Monster]
//  def takeDamage(projDamage: Int): Option[Monster] = {
//    if (projDamage < hp) Some(new Monster(x,y,speed,hp - projDamage, bounty, location)) else None
//  }
  def advance: Option[Monster]
  
//  def advance: Option[Monster] = {
//    
//    if (location > pLength) { 
//      World.decreaseHp(1)
//      None
//    } else Some(new Monster(newx, newy, speed, hp, bounty, location + speed))
//  }
  
}


class NormalMonster(x: Int, y: Int, hp: Int, location: Int) extends Monster(x,y,hp,location) {
  
  val bounty = 20
  
  def takeDamage(projDamage: Int): Option[Monster] = {
    if (projDamage < hp) Some(new NormalMonster(x,y,hp - projDamage, location)) else None
  }
  
  def advance: Option[Monster] = {
    if (location > pLength) { 
      World.decreaseHp(NormalMonster.damage)
      None
    } else Some(new NormalMonster(newx, newy, hp, location + NormalMonster.speed))
  }
  
}

object NormalMonster {
  val startHP = 2
  val speed = 2
  val bounty = 20
  val damage = 2
}



class FastMonster(x: Int, y: Int, hp: Int, location: Int) extends Monster(x,y,hp,location) {
  
  val bounty = 10
  
  def takeDamage(projDamage: Int): Option[Monster] = {
    if (projDamage < hp) Some(new FastMonster(x,y,hp - projDamage, location)) else None
  }
  
  def advance: Option[Monster] = {
    if (location > pLength) { 
      World.decreaseHp(FastMonster.damage)
      None
    } else Some(new FastMonster(newx, newy, hp, location + FastMonster.speed))
  }
}

object FastMonster {
  val startHP = 1
  val speed = 5
  val bounty = 10
  val damage = 1
}


class TankMonster(x: Int, y: Int, hp: Int, location: Int) extends Monster(x,y,hp,location) {
  
  val bounty = 35
  
  def takeDamage(projDamage: Int): Option[Monster] = {
    if (projDamage < hp) Some(new TankMonster(x,y,hp - projDamage, location)) else None
  }
  
  def advance: Option[Monster] = {
    if (location > pLength) { 
      World.decreaseHp(TankMonster.damage)
      None
    } else Some(new TankMonster(newx, newy, hp, location + TankMonster.speed))
  }
}

object TankMonster {
  val startHP = 10
  val speed = 1
  val bounty = 35
  val damage = 5
}