package tdk

import scala.collection.mutable.Buffer
import scala.collection.mutable.ListBuffer
/**This Object initializes the World
 * */
object LevelLoader {
  
  def wakeup = Unit
  
  val wave1 = {
    for (i <- 0 until 50) yield new NormalMonster(-25*i, -25*i, -25*i)
  }
    
  val wave2 = {
    for (i <- 0 until 50) yield new NormalMonster(-25*i, -25*i, -10*i)
  }
  
  val wave3 = {
    for (i <- 0 until 20) yield new FastMonster(-25*i, -25*i, -25*i)
  }
  
  val wave4 = {
    for (i <- 0 until 100) yield new NormalMonster(-25*i, -25*i, -10*i)
    for (i <- 0 until 10) yield new TankMonster(-25*i, -25*i, -10*i)
  }
  
  World.addWave(List(wave1,wave2,wave3,wave4).map(_.toBuffer))
}