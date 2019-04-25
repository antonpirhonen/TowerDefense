package tdk

import scala.math.{hypot, max}
import scala.util.Random
import scala.collection.mutable.Queue
import scala.collection.mutable.Buffer

object World {
  
  def time = GUI.gameTime
  private var health = 100
  private var money  = 1000
  
  //These represent the world and should all probably be private as well. Changed by a method
  var entities: Buffer[Entity] = Buffer()
  var monsters = Buffer[Monster]()
  var towers = Buffer(new Tower(300,350,1,100), new MachinegunTower(300,250))
  var projectiles: Buffer[Projectile] = Buffer()
  var waves: Queue[Buffer[Monster]] = Queue()
  
  def spawn() = {
    for (i <- 0 until 50) {
      monsters = monsters :+ new Monster(-25*i, -25*i, 2, 2, 20, -25*i)
    }
  }
  
  
  //A method for updating the world
  def update: Buffer[Entity] = {
    
    def activeMonsters = monsters.filter(_.location > 0)
    projectiles.foreach(proj => {
      val monstersClose  = activeMonsters.filter(mon => hypot(mon.x - proj.x, mon.y - proj.y) < Projectile.size)
      val monsterToShoot = if (monstersClose.nonEmpty) Some(monstersClose.minBy(mon => hypot(mon.x - proj.x, mon.y - proj.y)) ) 
                           else None
      if (monsterToShoot.nonEmpty) {
        monsters -= monsterToShoot.get
        projectiles -= proj
        val damaged = monsterToShoot.get.takeDamage(proj.damage)
        if (damaged.nonEmpty) monsters += damaged.get else money += monsterToShoot.get.bounty
      }
    })
    
    
    monsters = monsters.map(_.advance).flatten
    projectiles = projectiles.map(_.advance).flatten
    projectiles = projectiles ++ towers.map(_.shoot).flatten.flatten
    val all: Buffer[Entity] = monsters ++ projectiles ++ towers
    all
  }
  
  
  
  def decreaseHp(damage: Int) = {
    health -= scala.math.max(damage, 0)
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
  def addWave(monsters: Buffer[Monster]) = {
    waves += monsters
  }
  def addWave(multWaves : Seq[Buffer[Monster]]) = {
    multWaves.foreach(wave => waves += wave)
  }
  def nextWave() = {
    if (waves.nonEmpty) {
      monsters = monsters ++ waves.dequeue()
    } else spawn()
  }
  
  LevelLoader.wakeup
  
    
  
  
  
  
  
  
  
  
  
  
    
  
}