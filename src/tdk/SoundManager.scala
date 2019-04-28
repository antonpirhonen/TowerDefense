package tdk

import scalafx.Includes._
import scalafx.scene.media.AudioClip

object SoundManager extends {
  val click = new AudioClip("file:sound/TDK_Click.mp3")
  def playClick() = click.play()
  
  val pop = new AudioClip("file:sound/TDK_Pop.mp3")
  pop.setVolume(0.5)
  def playPop() = pop.play()
  
  val bip = new AudioClip("file:sound/TDK_Bip.mp3")
  def playBip() = bip.play()
  
  val bop = new AudioClip("file:sound/TDK_BopNew.mp3")
  def playBop() = bop.play()
}