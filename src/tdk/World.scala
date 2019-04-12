package tdk

import scalafx.scene.paint.Color
import scala.math.{hypot, max}
import scala.util.Random
import scala.collection.mutable.ListBuffer

object World {
  
  def time = GUI.gameTime
  private var health = 100
  private var money  = 1000
  
  var entities: ListBuffer[Entity] = ListBuffer()
  var monsters = ListBuffer[Monster]()
  var towers = ListBuffer(new Tower(300,350,1,100), new Tower(300,250,2,100))
  var projectiles: ListBuffer[Projectile] = ListBuffer()
  
  def spawn() = {
    for (i <- 0 until 50) {
      monsters = monsters :+ new Monster(-25*i, -25*i, 2, -25*i)
    }
  }
  
  spawn()
  
  def update: ListBuffer[Entity] = {
    
    def activeMonsters = monsters.filter(_.location > 0)
    projectiles.foreach(proj => {
      val monstersClose  = activeMonsters.filter(mon => hypot(mon.x - proj.x, mon.y - proj.y) < Projectile.size)
      val monsterToShoot = if (monstersClose.nonEmpty) Some(monstersClose.minBy(mon => hypot(mon.x - proj.x, mon.y - proj.y)) ) 
                           else None
      if (monsterToShoot.nonEmpty) {
        monsters -= monsterToShoot.get
        projectiles -= proj
        money += monsterToShoot.get.bounty
      }
    })
    
    
    monsters = monsters.map(_.advance).flatten
    projectiles = projectiles.map(_.advance).flatten
    projectiles = projectiles ++ towers.map(_.shoot).flatten
    val all: ListBuffer[Entity] = monsters ++ projectiles ++ towers
    all
  }
  
  
  
  def decreaseHp(damage: Int) = {
    health -= damage
  }
  
  def getHP = this.health
  def getMoney = this.money
  def addMoney(amount: Int) = money += max(amount,0)
  def buy(price: Int): Boolean = {
    if(price > money) false else {
      money -= price
      true
    }
  }
  
  val towerPrices = Map[Tower, Int](new BasicTower(1,2) -> 500)

  
    
  
  
  
  
  
  
  
  
  
  
    
  
}