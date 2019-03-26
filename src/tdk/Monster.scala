package tdk

class Monster(val x: Int, val y: Int, val speed: Int, val location: Int) {
  
  val p = Path.path
  def newx = if (p.indices contains location) p(location).x else x
  def newy = if (p.indices contains location) p(location).y else y
  def advance = new Monster(newx, newy, speed, location + speed)
  
}