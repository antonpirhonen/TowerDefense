package tdk

import scalafx.scene.paint.Color
import scala.util.Random

object World {
  
  def time = GUI.gameTime
  
  var entities: List[Entity] = List()
  var monsters = List[Monster]()
  var towers = List(new Tower(300,500,2,200), new Tower(500,500,1,100))
  var projectiles: List[Projectile] = List()
  
  
  for (i <- 0 until 50) {
    monsters = monsters :+ new Monster(-25*i, -25*i, 3, -25*i)
  }
  
  def update: List[Entity] = {
    
    
    monsters = monsters.map(_.advance).flatten
    projectiles = projectiles.map(_.advance)
    projectiles = projectiles ++ towers.map(_.shoot).flatten
    val all: List[Entity] = monsters ++ projectiles ++ towers
    all
  }
  
  
  
  

  
    
  
  
  
  
  
  
  
  
  
  
    
  
}