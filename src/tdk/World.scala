package tdk

import scalafx.scene.paint.Color
import scala.util.Random

object World {
  
  val colors = List(Color.DimGrey, Color.Yellow, Color.SIENNA, Color.FORESTGREEN)
  var monsters: List[Monster] = List()
  val monsterspeed: Int = 3
  
  class Monster(val x: Int,var y: Int,val size: Int,val color: Color)
  
  for (rep <- 0 until 50) {
    monsters = monsters :+ new Monster(Random.nextInt(980), Random.nextInt(580),
                                       Random.nextInt(20), colors.apply(Random.nextInt(3)) )
  }
  
  def moveMonsters(): Unit = {
    monsters.foreach(monster => monster.y = (monster.y + 3)%600)
  }
  
    
  
}