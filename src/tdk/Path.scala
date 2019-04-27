package tdk


/*Represents the path a monster proceeds on
 * */

object Path {
  
  //the screen limits are atm x = 1000, y = 600
  val screenX = 1000
  val screenY = 600
  
  //from point x = 0 -> 500, y = 300 -> 300
  val x0to500   = for (x <- 0 to 500) yield (new Pos(x, 300))
  val y300to100 = for (y <- 300 to 100 by -1) yield (new Pos(500, y))
  val x500to1k  = for (x <- 500 to 1000) yield (new Pos(x, 100))
  
  val path = x0to500 ++ y300to100 ++ x500to1k
  
  val tilePath = Grid.tiles(5).map(tile => tile.isPath = true)
  
  
}

class Pos(val x: Int, val y: Int)

object Grid {
  
  var tiles = Array[Array[Tile]]()
  for (y <- 0 until 12) {
    val xRow = for (x<- 0 until 20) yield new Tile(x, y)
    tiles = tiles :+ xRow.toArray
  }
  
}

class Tile(val gridX: Int, val gridY: Int) {
  var isPath = false
  val side = 50
  def x = gridX*side
  def y = gridY*side
}











