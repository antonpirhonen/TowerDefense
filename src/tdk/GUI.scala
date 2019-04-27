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
  private var canBuyTower: Option[TowerType] = None
  val width = 1000
  val height = 600
  
  
  stage = new JFXApp.PrimaryStage {
    
    title = "TDK GUI TEST" //the title of the window
    scene = new Scene(1000, 600) {
      
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
      
      gameMenu.items = List(basicTower, shotgunTower, mgTower)
      menuBar.menus = List(gameMenu)
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
      
      
      def updateStats() = {
        hpDisp.text = "Health: " + World.getHP
        moneyDisp.text = "Money: " + World.getMoney
        if (World.getHP < 0 ) gameLost()
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
    
    
    //Temporary
    def gameLost() = {
      scene = new Scene(1000, 600) {
        content = new Text("Game Lost! :(") {
          x = 100
          y = 100
          fill = Color.Black
        }
      }
    }
    
    
    
    
    
    
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
}
