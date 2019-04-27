package tdk

import scala.collection.mutable.Buffer
import scala.io.Source
import java.io.BufferedReader

import scala.collection.mutable.ListBuffer
/**This Object initializes the World
 * */
class CorruptedFileException(message: String) extends Exception(message)

class Wave(enemies: Buffer[Monster], spawnFreq: Double)

object LevelLoader {
  
  def wakeup = Unit
  
  def loadGame(str: String) = {
    val gameFile = Source.fromFile(str)
    def readLine(str: String): Unit = {
      
      
      
      
      val allowed = (48 until 58).map(_.toChar).:+('.')
      val monsAndFreq = str.trim.split(':').map(_.trim())
      val mons = monsAndFreq.head.split(',')
      if (mons.head.trim == "") return ()
      val freq = if (monsAndFreq.size == 1) 0.2 
                 else if (monsAndFreq.last.forall(char => allowed.contains(char))
                          && monsAndFreq.last.filter(char => char == '.').size <= 1) monsAndFreq.last.toDouble
                 else throw new CorruptedFileException("Monster spawn frequency entered wrong.")
      
      val toAdd = mons.map(mon => {
        val monType = mon.trim.takeWhile(_ != '*')
        val monAmount = mon.trim.reverse.takeWhile(_ != '*').reverse.toInt
        monType match {
          case "normal" => for (rep <- 0 until monAmount) yield new NormalMonster(-25, -25, -25*rep)
          case "fast"   => for (rep <- 0 until monAmount) yield new FastMonster(-25, -25, -25*rep)
          case "tank"   => for (rep <- 0 until monAmount) yield new TankMonster(-25, -25, -25*rep)
          case _        => throw new CorruptedFileException("Monsters are not formatted correctly in the file\n"
                                                          + "they should be written <type>*<amount> e.g. fast*10")
        }
      })
      World.waves.+=(toAdd.flatten.toBuffer)
      new Wave(toAdd.flatten.toBuffer,freq)
      println(freq)
      Unit
    }
    
    try {
      if (gameFile.nonEmpty) {
        val lines = gameFile.getLines()
        lines.foreach(readLine)
      }
    } finally {
      gameFile.close()
    }   
  }
  
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
  
  //World.addWave(List(wave1,wave2,wave3,wave4).map(_.toBuffer))
}
