package tdk



import scalafx.application.JFXApp
import scalafx.scene.shape.Circle
import scalafx.scene.Scene
import scalafx.animation.AnimationTimer
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.Includes._
import scalafx.scene.control.Menu
import scalafx.scene.control.MenuBar
import scalafx.scene.control.MenuItem
import scalafx.scene.layout.BorderPane
import scalafx.event.ActionEvent
import javafx.scene.input.MouseEvent
import javafx.event.EventHandler
import scalafx.scene.image._
import scalafx.scene.layout.StackPane


/* A gui that hopefully is not as bad a cat ass trophy as the last one*/

object GUI extends JFXApp{
 
  /* Stage, jossa pääikkuna scene
   * scenellä on content, johon voi lisätä Node-tyyppisiä olioita, joita on eri muodot teksti,Image jne
   * Rectangleja voi teksturoida image pattern - luokalla, 
   * Ticker metodi: Saa kellon 
   * clock: AnimationTimer("taski, jota tekee joka tickillä")
   * UpdateLogic/drawGraphics
   * */
  var gameTime = 0L
  
  stage = new JFXApp.PrimaryStage {
    
    title = "TDK GUI TEST" //the title of the window
    scene = new Scene(1000, 600) {
      
      val menuBar = new MenuBar
      val gameMenu = new Menu("Towers")
      val basicTower = new MenuItem("Basic Tower - 500$") {
        onMouseClicked = new EventHandler[MouseEvent] {
          override def handle(event: MouseEvent) {
            val x = event.getSceneX
            val y = event.getSceneY
            World.towers += new Tower(x.toInt,y.toInt,1,100)
          }
        }
      }
      
      gameMenu.items = List(basicTower)
      menuBar.menus = List(gameMenu)
      
      val rootPane = new BorderPane
      rootPane.top = menuBar
      
      val bg = new Image("file:///C:/Users/anton/Desktop/Antonin%20tiedostot/Koulu/Ohjelmointi/O2/tower-defense/src/images/TaustaAluksi.png")
      val view = new ImageView(bg)
      
      
      val stackPane = new StackPane {
        content = view
      }
      
      
            
      val timer = AnimationTimer(t => {
        content = List(view, rootPane) ++ World.update.map(entity => entity match {
          case monster: Monster => Circle(monster.x, monster.y, 10)
          case tower: Tower     => Rectangle(tower.x, tower.y, 30, 30)
          case projectile: Projectile => Circle(projectile.x, projectile.y, 5)      
        })
        gameTime = t
      })
      
     timer.start()
    }
  }
}