package tdk

import scalafx.scene.paint.Color
import scala.util.Random

object World {
  
  def time = System.nanoTime/10E8
  
  var entities: List[Entity] = List()
  var monsters = List[Monster]()
  var towers = List(new Tower(300,500,1), new Tower(500,500,2))
  var projectiles: List[Projectile] = List()
  
  
  for (i <- 0 until 50) {
    monsters = monsters :+ new Monster(-25*i, -25*i, 3, -25*i)
  }
  
  def update: List[Entity] = {
    monsters = monsters.map(_.advance)
    projectiles = projectiles.map(_.advance)
    projectiles = projectiles ++ towers.map(_.shoot)
    val all: List[Entity] = monsters ++ projectiles ++ towers
    all
  }
  
  

  
    
  
  
  
  
  
  
  
  
  
  
    
  
}