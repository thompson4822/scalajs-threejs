package example

import example.models.{Cavern, FloodFill}
import org.scalajs.dom.raw.{HTMLElement, WebGLRenderingContext}

import scala.scalajs.js.annotation.JSExport
import org.denigma.threejs._
import org.scalajs.dom

import scala.scalajs.js

@JSExport
object Game {
  @JSExport
  def main(container: HTMLElement) {
/*
    renderer.setSize(window.innerWidth, window.innerHeight)
    container.appendChild(renderer.domElement)

    scene.add(cube)
    camera.position.z = 5

    renderScene(60)
*/
    val cavern = Cavern(48, 48)
    println("Okay, just built a cavern!")
    val floodFill = new FloodFill(cavern)
    println(s"There are ${floodFill.subcaverns.length} subcaverns here")
    val bigger = cavern.subdivide
    println(bigger)
  }

/*
  val window = dom.window
  val scene = new Scene
  val camera = new PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000)
  val renderer = new WebGLRenderer
  val geometry = new BoxGeometry(1, 1, 1)
  val material = new MeshBasicMaterial(js.Dynamic.literal(color = 0x00ff00).asInstanceOf[MeshBasicMaterialParameters])
  val cube = new Mesh(geometry, material)

  def renderScene(time: Double): Unit = {
    dom.window.requestAnimationFrame(renderScene(_: Double))
    cube.rotation.x += 0.01
    cube.rotation.y += 0.01
    renderer.render(scene, camera)
  }
*/

  //val renderer = new
}