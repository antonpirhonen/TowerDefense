package tdk



import scalafx.application.JFXApp
import scalafx.scene.shape.Circle
import scalafx.scene.Scene
import scalafx.animation.AnimationTimer
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

/* A gui that hopefully is not as bad a cat ass trophy as the last one*/

object GUI extends JFXApp{
 
  /* Stage, jossa pääikkuna scene
   * scenellä on content, johon voi lisätä Node-tyyppisiä olioita, joita on eri muodot teksti Image????
   * Rectangleja voi teksturoida image pattern - luokalla, 
   * Ticker metodi: Saa kellon 
   * clock: AnimationTimer("taski, jota tekee joka tickillä")
   * UpdateLogic/drawGraphics
   * */
  
  stage = new JFXApp.PrimaryStage {
    
    title = "TDK GUI TEST" //the title of the window
    scene = new Scene(1000, 600) {
      
      def monsters = World.monsters
      val enemies = World.monsters.map(m => Circle(m.x, m.y, 10))
      val speed = 3.0
      
      val randColors = List(Color.ANTIQUEWHITE, Color.DARKSLATEGRAY, Color.POWDERBLUE, Color.LAWNGREEN, Color.WHEAT)
      
      content = enemies
      
      val timer = AnimationTimer(t => {
        content = World.update.map(entity => entity match {
          case monster: Monster => Circle(monster.x, monster.y, 10)
          case tower: Tower     => Rectangle(tower.x, tower.y, 30, 30)
          case projectile: Projectile => Circle(projectile.x, projectile.y, 5)      
        })
      })
      timer.start()
      
    }
  }
}