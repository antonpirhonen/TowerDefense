package tdk

import scala.math.{hypot, max}
import scala.util.Random
import scala.collection.mutable.Queue
import scala.collection.mutable.Buffer
import scala.collection.mutable.ListBuffer


object World {
  
  def time = GUI.gameTime
  private var lastSpawn = 0L
  private var health = 100
  private var money  = 1000
  private var spawnFreq = 1.0
  
  //These represent the world and should all probably be private as well. Changed by a method
  var entities: Buffer[Entity] = Buffer()
  var unspawnedMonsters = Queue[Monster]()
  var monsters = Buffer[Monster]()
  var towers = Buffer[Tower]()
  var projectiles: ListBuffer[Projectile] = ListBuffer()
  var waves: Queue[Wave] = Queue()
    
  
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
    
    val last = lastSpawn
    if (unspawnedMonsters.nonEmpty && (time - last) > spawnFreq*10E8) { monsters += unspawnedMonsters.dequeue(); lastSpawn = time }
    
    monsters = monsters.map(_.advance).flatten
    projectiles = projectiles.map(_.advance).flatten
    projectiles = projectiles ++ towers.map(_.shoot).flatten.flatten
    val all: Buffer[Entity] = monsters ++ projectiles ++ towers
    all
  }
  
  
  
  def decreaseHp(damage: Int) = {
    health -= damage
  }
  def getHP = this.health
  def setHP(hp: Int) = health = hp 
  def updateSpawnFreq(freq: Double) = this.spawnFreq = freq
  def getMoney = this.money
  def setMoney(amount: Int) = money = amount 
  def addMoney(amount: Int) = money += max(amount,0)
  def buy(price: Int): Boolean = {
    if(price > money) false else {
      money -= price
      true
    }
  }
  def addWave(wave: Wave) = {
    waves += wave
  }
  def addWave(multWaves : Seq[Wave]) = {
    multWaves.foreach(wave => waves += wave)
  }
  def nextWave() = {
    if (waves.nonEmpty && unspawnedMonsters.isEmpty) {
      val next = waves.dequeue()
      spawnFreq = next.spawnFreq
      unspawnedMonsters = Queue[Monster]() ++ next.enemies
    }
  }
  
  def initializeWorld() = {
    lastSpawn = 0L
    health = 100
    money = 1000
    spawnFreq = 0.2
    entities = Buffer()
    unspawnedMonsters = Queue[Monster]()
    monsters = Buffer[Monster]()
    towers = Buffer[Tower]()
    projectiles = ListBuffer()
    waves = Queue()
    GUI.fileCor = false
    GUI.errorMessage = ""
    GUI.monsLoaded = false
    LevelLoader.loadGame(GUI.gameFile)
  }
  
  SoundManager.prep
  LevelLoader.loadGame(GUI.gameFile)
}
