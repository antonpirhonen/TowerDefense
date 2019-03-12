import scalafx.application.JFXApp
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

object GUI extends JFXApp {
  
  stage = new JFXApp.PrimaryStage {
    title = "Tower defense game"
    scene = new Scene(1000, 600) {
      
      fill = scalafx.scene.paint.Color.AQUAMARINE
      
      val button = new Button("Click")
      content = List(button)
      
      val menuBar = new MenuBar
      val gameMenu = new Menu("Game menu")
      val newGame = new MenuItem("New game")
      val loadGame = new MenuItem("Load game")
      val saveGame = new MenuItem("Save game")
      gameMenu.items = List(newGame, loadGame, new SeparatorMenuItem, saveGame)
      menuBar.menus = List(gameMenu)
      
      val rootPane = new BorderPane
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