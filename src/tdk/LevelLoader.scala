package tdk

import scala.collection.mutable.Buffer
import scala.io.Source
import java.io.BufferedReader

import scala.collection.mutable.ListBuffer
/**This Object initializes the World
 * */
class CorruptedFileException(val message: String) extends Exception(message)

class Wave(var enemies: Buffer[Monster],val spawnFreq: Double)

object LevelLoader {
  
  var readingMonData  = false
  var readingStatData = false
  
  
  def loadGame(str: String) = {
    val gameFile = Source.fromFile(str)
    
    def readLine(str: String): Unit = {
      if (str.trim.toLowerCase == "#waves") {readingMonData = true; readingStatData = false; return}
      else if (str.trim.toLowerCase == "#stats") {readingMonData = false; readingStatData = true; return}
      if (readingMonData) return readMonsterData(str)
      if (readingStatData) return readStatData(str)
      else throw new CorruptedFileException("Headers of the .level file are wrong.")
      
      
      
      def readMonsterData(str: String): Unit = {
        val allowed = (48 until 58).map(_.toChar).:+('.')
        val monsAndFreq = str.trim.split(':').map(_.trim().toLowerCase)
        val mons = monsAndFreq.head.split(',')
        if (mons.head.trim == "") return ()
        val freq = if (monsAndFreq.size == 1) 0.2 
                   else if (monsAndFreq.last.forall(char => allowed.contains(char))
                            && monsAndFreq.last.filter(char => char == '.').size <= 1) monsAndFreq.last.toDouble
                   else throw new CorruptedFileException("Monster spawn frequency should be entered as follows:\n"+ 
                                                         "<data from monsters>:frequency")
        
        val toAdd = mons.map(mon => {
          val typeAmount = mon.split('*').map(_.trim())
          if (typeAmount.size != 2) throw new CorruptedFileException("Monster data entered wrong.")
          val monType = typeAmount.head
          val monAmount = if (typeAmount.last.forall(char => allowed.dropRight(1).contains(char))) typeAmount.last.toInt
                          else throw new CorruptedFileException("Monster amount was not entered correctly.") 
          monType match {
            case "normal" => for (rep <- 0 until monAmount) yield new NormalMonster(-25, -25, -25*rep)
            case "fast"   => for (rep <- 0 until monAmount) yield new FastMonster(-25, -25, -25*rep)
            case "tank"   => for (rep <- 0 until monAmount) yield new TankMonster(-25, -25, -25*rep)
            case _        => throw new CorruptedFileException("Monsters are not formatted correctly in the file\n"
                                                            + "they should be written <type>*<amount> e.g. fast*10")
          }
        })
        GUI.monsLoaded = true
        World.waves.+=(new Wave(toAdd.flatten.toBuffer,freq))
      }
      
      def readStatData(str: String): Unit = {
        val allowed = 48 until 58 map(_.toChar)
        val paramAndScalar = str.split('=').map(_.trim().toLowerCase)
        val param  = paramAndScalar.head
        val scalar = paramAndScalar.last
        if (!(param == "health" || param == "money")) throw new CorruptedFileException("Parameters of statistics are inputted wrong.")
        if (!scalar.forall(char => allowed.contains(char))) throw new CorruptedFileException("The scalars of Stats are in an incorect format.")
        if (param == "health") World.setHP(scalar.toInt) else World.setMoney(scalar.toInt)
      }
      
      
      
    }
    
    try {
      if (gameFile.nonEmpty) {
        val lines = gameFile.getLines()
        lines.foreach(readLine)
      }
    } catch {
      case cfe: CorruptedFileException => {
        GUI.fileCor = true
        GUI.errorMessage = cfe.message
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
