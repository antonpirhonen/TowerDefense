package tdk


/*Represents the path a monster proceeds on
 * */

object Path {
  
  //the screen limits are atm x = 1000, y = 600
  val screenX = 1000
  val screenY = 600
  
  //from point x = 0 -> 500, y = 300 -> 300
  val x0to500   = for (x <- 0 to 525) yield (new Pos(x, 325))
  val y300to100 = for (y <- 325 to 125 by -1) yield (new Pos(525, y))
  val x500to1k  = for (x <- 525 to 1000) yield (new Pos(x, 125))
  
  val path = x0to500 ++ y300to100 ++ x500to1k
  
  for (i <- 0 until 11) {
    Grid.tiles(6)(i).isPath = true
  }
  
  for (i <- 6 to 2 by -1) {
    Grid.tiles(i)(10).isPath = true
  }
  
  for (i <- 10 until 20) {
    Grid.tiles(2)(i).isPath = true
  }
  
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











