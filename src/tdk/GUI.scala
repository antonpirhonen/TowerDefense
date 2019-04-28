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
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.scene.text._
import scalafx.stage.FileChooser
import scalafx.scene.control.Button
import scalafx.scene.control.Alert


/* A gui that hopefully is not as bad a cat ass trophy as the last one*/

object GUI extends JFXApp{
 
  /* Stage, jossa pääikkuna scene
   * scenellä on content, johon voi lisätä Node-tyyppisiä olioita, joita on eri muodot teksti,Image jne
   * Rectangleja voi teksturoida image pattern - luokalla, 
   * Ticker metodi: Saa kellon 
   * clock: AnimationTimer("taski, jota tekee joka tickillä")
   * UpdateLogic/drawGraphics
   * */
  var gameFile = "levels/testLevel.level"
  var gameTime = 0L
  var monsLoaded = false
  var fileCor  = false
  var errorMessage = ""
  private var canBuyTower: Option[TowerType] = None
  val width = 1000
  val height = 600
  
  
  stage = new JFXApp.PrimaryStage {
    
    title = "TDK GUI TEST" //the title of the window
    val mainScene: Scene = new Scene(1000, 600) {
      
      val menuBar = new MenuBar
      val gameMenu = new Menu("Towers")
      val basicTower = new MenuItem("Basic Tower - 500$") {
        onAction = (ae: ActionEvent) => {
          buy(BasicTower)
        }
      }
      val mgTower = new MenuItem("Machinegun Tower - 1500$") {
        onAction = (ae: ActionEvent) => {
          buy(MachinegunTower)
        }
      }
      val shotgunTower = new MenuItem("Shotgun Tower - 1000$") {
        onAction = (ae: ActionEvent) => {
          buy(ShotgunTower)
        }
      }
      
      val loadMenu = new Menu("Game menu")
      val loadGame = new MenuItem("Load game") {
        onAction = (ae: ActionEvent) => {
          val fChooser = new FileChooser
          val selectedFile = fChooser.showOpenDialog(stage)
          if (selectedFile != null) {
            gameFile = selectedFile.toString().replace('\\', '/')
            World.initializeWorld()
            if (fileCor){
              corruptedFileAlert(errorMessage)   
              World.initializeWorld()
            }
            
          }
        }
      }
      val restart = new MenuItem("Restart") {
        onAction = (ae: ActionEvent) => {
          World.initializeWorld()
          LevelLoader.loadGame(gameFile)
        }
      }
      
      gameMenu.items = List(basicTower, shotgunTower, mgTower)
      loadMenu.items = List(loadGame, restart)
      menuBar.menus = List(gameMenu, loadMenu)
      val rootPane = new BorderPane
      rootPane.top = menuBar
            
      
      onMouseClicked = new EventHandler[MouseEvent] {
        override def handle(me: MouseEvent) {
          if (canBuyTower.nonEmpty) {
            val x = me.getSceneX
            val y = me.getSceneY
            canBuyTower.get match {
              case BasicTower => World.towers += new BasicTower(x.toInt - BasicTower.size/2, y.toInt - BasicTower.size/2)
              case MachinegunTower => World.towers += new MachinegunTower(x.toInt - BasicTower.size/2, y.toInt - BasicTower.size/2)
              case ShotgunTower => World.towers += new ShotgunTower(x.toInt - BasicTower.size/2, y.toInt - BasicTower.size/2)
              case _          => 
            }
            canBuyTower = None
          }          
        }
      }
      
      
      onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.SPACE => World.nextWave()
//          case KeyCode.B if canBuyTower.isEmpty => buy(BasicTower) //for some reason the thread didn't like this idea and it stopped the mouse
//          case KeyCode.M if canBuyTower.isEmpty => buy(MachinegunTower)
//          case KeyCode.S if canBuyTower.isEmpty => buy(ShotgunTower)
          case _ =>
        }
      }
      
//      val bg = new Image("file:images/TaustaAluksi.png")
//      val view = new ImageView(bg)
      
      val hpDisp = new Text("Health: 100") {
        x = 920
        y = 40
        fill = Color.Red
      }
      
      val moneyDisp = new Text("Money: " + World.getMoney) {
        x = 830
        y = 40
        fill = Color.Orange
      }
      
      
      def updateStats(): Unit = {
        hpDisp.text = "Health: " + World.getHP
        moneyDisp.text = "Money: " + World.getMoney
        if(World.getHP < 1 ) gameLost
        if(World.waves.isEmpty && World.monsters.isEmpty && World.unspawnedMonsters.isEmpty && monsLoaded) gameWon()
      }
      
      def corruptedFileAlert(msg: String) = {
        timer.stop()
        new Alert(Alert.AlertType.Warning) {
          initOwner(stage)
          title = "Warning"
          headerText = "A Corrupted Load File"
          contentText = msg
        }.showAndWait()
        timer.start()
      }
      
      def buy(ttype: TowerType) = {
         if (World.buy(ttype.price)) {
            canBuyTower = Some(ttype)           
          }
      }
      
      
      def monsterImg(mon: Monster): Circle = {
        mon match {
          case a: NormalMonster => Circle(a.x, a.y, 10)
          case b: FastMonster   => {
            val temp = Circle(b.x, b.y, 5)
            temp.fill_=(Color.Green)
            temp
          }
          case c: TankMonster   => {
            val temp = Circle(c.x,c.y,20)
            temp.fill = Color.Red
            temp
          }
        }
      }
      
      val tiles = Grid.tiles.map(column => column.map(tile => {
        val img = Rectangle(tile.x, tile.y, tile.side, tile.side)
        if (tile.isPath) img.fill_=(Color.Gray) else img.fill_=(Color.LightGreen)
        img
      })).flatten

      
      val temp = new Button("temp") {
        onAction = (ae: ActionEvent) => {
          gameLost
        }
      }
      
      val timer = AnimationTimer(t => {
        updateStats()
        content = tiles ++ List(rootPane, hpDisp, moneyDisp) ++ World.update.map(entity => entity match {
          case monster: Monster => monsterImg(monster)
          case tower: Tower     => Rectangle(tower.x, tower.y, 30, 30)
          case projectile: Projectile => Circle(projectile.x, projectile.y, 5)      
        })
        gameTime = t
      })
      
      timer.start()
    }
    
    scene = mainScene
    
    //Temporary
    def gameLost() = {
      scene = new Scene(1000, 600) {
        World.initializeWorld()
        val bg = Rectangle(1000,1000)
        val text = new Text("Game Over!")
        text.fill_=(Color.Red)
        text.scaleX = 15
        text.scaleY = 10
        text.setX(470)
        text.setY(250)
        val button = new Button("Play Again!") {
          onMouseClicked = (me: MouseEvent) => {
            stage.setScene(mainScene)
          }
        }
        button.relocate(470, 400)
        
        
        content = List(bg, text, button)
      }
    }
    
    def gameWon() = {
      scene = new Scene(1000,600) {
        World.initializeWorld()
        val bg = Rectangle(1000,1000)
        bg.fill = Color.LightBlue
        val text = new Text("Congratulations!\nYou won the level!")
        text.fill = Color.CORAL
        text.scaleX = 10
        text.scaleY = 7
        text.relocate(453, 150)
        val button = new Button("Play Again!") {
          onMouseClicked = (me: MouseEvent) => {
            
//            val fChooser = new FileChooser
//            val selectedFile = fChooser.showOpenDialog(stage)
//              if (selectedFile != null) {
//                gameFile = selectedFile.toString().replace('\\', '/')
//                World.initializeWorld()
//                LevelLoader.loadGame(gameFile)
//                if (fileCor){
//                  new Alert(Alert.AlertType.Warning) {
//                    initOwner(stage)
//                    title = "Warning"
//                    headerText = "A Corrupted Load File"
//                    contentText = errorMessage
//                  }.showAndWait()   
//                  World.initializeWorld()
//                }
//              }
            stage.setScene(mainScene)
          }
        }
        button.relocate(470, 400)
        content = List(bg, text, button)
      }
    }
    
    
    
    
    
    
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
}
