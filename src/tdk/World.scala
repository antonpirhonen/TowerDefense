package tdk

import scalafx.scene.paint.Color
import scala.util.Random

object World {
  
  var monsters = List[Monster]()
  
  for (i <- 0 until 50) {
    monsters = monsters :+ new Monster(-25*i, -25*i, 3, -25*i)
  }
  
  
//  val colors = List(Color.DimGrey, Color.Yellow, Color.SIENNA, Color.FORESTGREEN)
//  var monsters: List[Monster] = List()
//  val monsterspeed: Int = 3
//  
//  class Monster(val x: Int,val y: Int,val size: Int,val color: Color)
//  
//  for (rep <- 0 until 50) {
//    monsters = monsters :+ new Monster(-30*rep, 300,
//                                       Random.nextInt(40), colors.apply(Random.nextInt(3)) )
//  }
//  
//  def moveMonsters(): Unit = {
//    monsters = monsters.map(o => new Monster(o.x + monsterspeed, o.y, o.size, o.color))
//  }
  
    
  
}