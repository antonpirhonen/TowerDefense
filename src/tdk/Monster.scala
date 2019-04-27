package tdk
import scala.math.{hypot, pow, atan, Pi, sin, cos}
import scala.collection.mutable.Buffer

abstract class Monster(val x: Int, val y: Int, var path: Buffer[Tile], val hp: Int) extends Entity {
  
  val bounty: Int
//  
//  val p = Path.path
//  val pLength = p.length
//  
//  def newx = if (location >= 0 && location < pLength) p(location).x else x
//  def newy = if (location >= 0 && location < pLength) p(location).y else y
  
  val newP = Path.newPath
  
  def takeDamage(projDamage: Int): Option[Monster]
  def advance: Option[Monster]
  
}


case class TestMonster(val x: Int = Path.startLoc.x, val y: Int = Path.startLoc.y, var path: Buffer[Tile] = Path.newPath, val hp: Int = 5) extends Monster(x,y,path,hp) {
  val bounty = 10
  val speed = 2
  var goal = path.head
  val dx = goal.x - this.x
  val dy = goal.y - this.y
  if (hypot(dx, dy) < speed) updateGoal
  def updateGoal = if (path.length > 1 ) path = path.tail else if(path.length == 1) World.decreaseHp(1)
  def takeDamage(projDamage: Int) = if (projDamage < hp) Some(new TestMonster(x,y, path, hp - projDamage)) else None
  def advance = {
    var angle = atan(dy.toDouble/dx)
    if (angle > 0) {
      if (dy < 0) angle = angle + Pi
    } else {
      if (dy > 0) angle = angle + Pi
    }
    Some(new TestMonster(x + speed*cos(angle).round.toInt, y + speed*sin(angle).round.toInt, path, hp))
  }
  
}

//
//class NormalMonster(x: Int, y: Int, path: Buffer[Tile], hp: Int = NormalMonster.startHP) extends Monster(x,y,path,hp) {
//  
//  val bounty = 20
//  
//  def takeDamage(projDamage: Int): Option[Monster] = {
//    if (projDamage < hp) Some(new NormalMonster(x,y, path ,hp - projDamage)) else None
//  }
//  
//  def advance: Option[Monster] = {
//    if (location > pLength) { 
//      World.decreaseHp(NormalMonster.damage)
//      None
//    } else Some(new NormalMonster(newx, newy, location + NormalMonster.speed, hp))
//  }
//  
//}
//
//object NormalMonster {
//  val startHP = 2
//  val speed = 2
//  val bounty = 20
//  val damage = 2
//  def apply(x: Int, y: Int, location: Int, hp: Int) = new NormalMonster(x,y,location,hp)
//  //TehdasmetodiFTW
//}
//
//
//
//class FastMonster(x: Int, y: Int, location: Int, hp: Int = FastMonster.startHP) extends Monster(x,y,location,hp) {
//  
//  val bounty = 10
//  
//  def takeDamage(projDamage: Int): Option[Monster] = {
//    if (projDamage < hp) Some(new FastMonster(x,y,location,hp - projDamage)) else None
//  }
//  
//  def advance: Option[Monster] = {
//    if (location > pLength) { 
//      World.decreaseHp(FastMonster.damage)
//      None
//    } else Some(new FastMonster(newx, newy, location + FastMonster.speed, hp))
//  }
//}
//
//object FastMonster {
//  val startHP = 1
//  val speed = 5
//  val bounty = 10
//  val damage = 1
//}
//
//
//class TankMonster(x: Int, y: Int, location: Int, hp: Int = TankMonster.startHP) extends Monster(x,y,location,hp) {
//  
//  val bounty = 35
//  
//  def takeDamage(projDamage: Int): Option[Monster] = {
//    if (projDamage < hp) Some(new TankMonster(x,y,location,hp - projDamage)) else None
//  }
//  
//  def advance: Option[Monster] = {
//    if (location > pLength) { 
//      World.decreaseHp(TankMonster.damage)
//      None
//    } else Some(new TankMonster(newx, newy, location + TankMonster.speed, hp))
//  }
//}
//
//object TankMonster {
//  val startHP = 10
//  val speed = 1
//  val bounty = 35
//  val damage = 5
//}