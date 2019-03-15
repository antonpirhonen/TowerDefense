import scalafx.application.JFXApp
import scalafx._
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.control.Menu
import scalafx.scene.control.MenuBar
import scalafx.scene.control.MenuItem
import scalafx.scene.control.SeparatorMenuItem
import scalafx.scene.layout.BorderPane
import scalafx.event.ActionEvent
import scalafx.scene.shape.Rectangle
import scalafx.scene.canvas.Canvas
import scalafx.geometry.Point2D
import scalafx.scene.paint.Color
import scalafx.scene.layout.Pane

object GUI extends JFXApp {
  
  object Updater {
    private var _start = new Point2D(0, 0)
    private var _end = new Point2D(0, 0)

    val rectangle = new Rectangle {
      fill = Color.Blue
    }
  }
  
  /* Stage, jossa pääikkuna scene
   * scenellä on content, johon voi lisätä Node-tyyppisiä olioita, joita on eri muodot teksti Image????
   * Rectangleja voi teksturoida image pattern - luokalla, 
   * Ticker metodi: Saa kellon 
   * clock: AnimationTimer("taski, jota tekee joka tickillä")
   * UpdateLogic/drawGraphics
   * 
   * 
   * */
  
  stage = new JFXApp.PrimaryStage {
    title = "Tower defense game"
    scene = new Scene(1000, 600) {
      
      val button = new Button("Click")
      content = List(button)
      
      val menuBar = new MenuBar
      val gameMenu = new Menu("Game menu")
      val newGame = new MenuItem("New game")
      val loadGame = new MenuItem("Load game")
      val saveGame = new MenuItem("Save game")
      gameMenu.items = List(newGame, loadGame, new SeparatorMenuItem, saveGame)
      menuBar.menus = List(gameMenu)
      
      
      val pane = new Pane {
        children = Updater.rectangle
      }
      
      val rootPane = new BorderPane {
        center = pane
      }
      rootPane.top = menuBar
      root = rootPane
      
      val canvasBorder = new BorderPane
      val canvas = new Canvas(2000, 2000)      
      val gc = canvas.graphicsContext2D
      gc.fillRect(10, 10, 10, 10)
      canvasBorder.center = canvas
      
    }
  }
  
  
}