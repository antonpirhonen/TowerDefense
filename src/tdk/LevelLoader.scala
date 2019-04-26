package tdk

import scala.collection.mutable.Buffer
import scala.io.Source

import scala.collection.mutable.ListBuffer
/**This Object initializes the World
 * */
object LevelLoader {
  
  def wakeup = Unit
  
//  val gameFile = Source.fromFile("levels.testLevel.level")
//  def readLine(str: String) = {
//    val mons = str.trim.split(',')
//    mons.foreach(mon => {
//      val monType = mon.trim.takeWhile(_ != '*')
//      val monAmount = mon.trim.reverse.takeWhile(_ != '*').reverse.toInt
//      val toAdd = monType match {
//        case "normal" => for (rep <- 0 until monAmount) yield new NormalMonster(-25, -25, -25*rep)
//        case "fast"   => for (rep <- 0 until monAmount) yield new FastMonster(-25, -25, -25*rep)
//        case "tank"   => for (rep <- 0 until monAmount) yield new TankMonster(-25, -25, -25*rep)
//        case _        => IndexedSeq[Monster]()
//      }
//    World.monsters.++=(toAdd)  
//    })
//  }
//  
//  try {
//    if (gameFile.nonEmpty) {
//      val lines = gameFile.getLines()
//      lines.foreach(readLine)
//    }
//  } finally {
//    gameFile.close()
//  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  val wave1 = {
    for (i <- 0 until 50) yield new NormalMonster(-25, -25, -25*i)
  }
    
  val wave2 = {
    for (i <- 0 until 50) yield new NormalMonster(-25, -25, -10*i)
  }
  
  val wave3 = {
    for (i <- 0 until 20) yield new FastMonster(-25*i, -25*i, -25*i)
  }
  
  val wave4 = {
    for (i <- 0 until 100) yield new NormalMonster(-25, -25, -10*i)
    for (i <- 0 until 10) yield new TankMonster(-25*i, -25*i, -10*i)
  }
  
  World.addWave(List(wave1,wave2,wave3,wave4).map(_.toBuffer))
}