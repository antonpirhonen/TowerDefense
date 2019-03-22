package tdk

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.stage.StageStyle
import scalafx.scene.image.Image
import scalafx.animation.AnimationTimer
import scalafx.scene.shape.Circle
import scala.util.Random

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
      
      val enemies = World.monsters.map(m => Circle(m.x, m.y, m.size))
      val speed = 3.0
      
      val randColors = List(Color.ANTIQUEWHITE, Color.DARKSLATEGRAY, Color.POWDERBLUE, Color.LAWNGREEN, Color.WHEAT)
      
      content = enemies
      
      val timer = AnimationTimer(t => {
        val c = Circle(t%1000,t%600,t%600)
        c.setFill(randColors(Random.nextInt(5)))
        content = c
      })
      timer.start()
      
    }
  }
}