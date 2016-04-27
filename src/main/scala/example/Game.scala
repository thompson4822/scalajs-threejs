package example

import example.models.{Cavern, FloodFill}
import org.scalajs.dom.raw.{HTMLElement, WebGLRenderingContext}


import scala.scalajs.js
import js.annotation._
import org.denigma.threejs._
import org.scalajs.dom
import js.JSConverters._


@JSExport
object Game {
  @JSExport
  def main(container: HTMLElement) {
    renderer.setSize(window.innerWidth, window.innerHeight)
    container.appendChild(renderer.domElement)

    //scene.add(cube)
    camera.position.z = 10

    val loader = new JSONLoader()
    loader.load( "flask.json", {(geometry: JSonLoaderResultGeometry, materials: js.Array[Material]) =>
      flask = new Mesh(geometry, materials(0))
      flask.scale.set(3, 3, 3)
      scene.add(flask)
      println("Added flask ... ?!?!")
    })

    val ambientLight = new AmbientLight(0xffffff)
    scene.add(ambientLight)

    val directionalLight = new DirectionalLight(0xffffff)
    directionalLight.position.set(0, 1, 0)
    scene.add(directionalLight)

    //println(createCavern)

    renderScene(System.currentTimeMillis())
  }

  val window = dom.window
  val scene = new Scene
  val camera = new PerspectiveCamera(75, window.innerWidth / window.innerHeight, 1, 1000)
  val renderer = new WebGLRenderer
  val geometry = new BoxGeometry(1, 1, 1)
  val material = new MeshBasicMaterial(js.Dynamic.literal(color = 0x00ff00).asInstanceOf[MeshBasicMaterialParameters])
  val cube = new Mesh(geometry, material)

  var flask: Mesh = _

  def renderScene(timestamp: Double): Unit = {
    dom.window.requestAnimationFrame(renderScene _)
    if(flask != null) {
      flask.rotation.x += 0.01
      flask.rotation.y += 0.01
    }
    renderer.render(scene, camera)
  }

  def createCavern: Cavern = {
    // This is doing a couple of things. Not only does it generate a cavern,
    // but it also fills in all rooms except for the biggest (primary)
    val cavern = new FloodFill(Cavern(48, 48)).fillSecondary

    // There are a lot of other things that could happen here. For instance, this just creates an empty map, but that map
    // could be decorated with all manner of objects, creatures, etc.

    // This enlarges the cavern and smooths it out
    cavern.subdivide.smooth

  }

}