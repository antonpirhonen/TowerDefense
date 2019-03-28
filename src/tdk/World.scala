package tdk

import scalafx.scene.paint.Color
import scala.util.Random

object World {
  
  var entities: List[Entity] = List()
  
  var monsters = List[Monster]()
  
  for (i <- 0 until 50) {
    monsters = monsters :+ new Monster(-25*i, -25*i, 3, -25*i)
  }
  
  def updateMonsters = {
    monsters = monsters.map(_.advance)
    monsters
  }
  
  def update: List[Entity] = {
    monsters = monsters.map(_.advance)
    projectiles = projectiles.map(_.advance)
    val all: List[Entity] = monsters ++ projectiles ++ towers
    all
  }
  
  
  var towers = List(new Tower(300,300,1), new Tower(600,600,2))
  
  var projectiles: List[Projectile] = List()
  
    
  
  
  
  
  
  
  
  
  
  
    
  
}