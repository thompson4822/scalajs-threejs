package example

import org.scalajs.dom.raw.HTMLElement

import scala.scalajs.js
import scala.scalajs.js.{JSON, UndefOr}
import scala.scalajs.js.annotation.{JSName, ScalaJSDefined, JSExport}
import org.scalajs.dom
import org.scalajs.dom.html
import scala.util.Random
import js.JSConverters._

@ScalaJSDefined
class PersonalAttributes extends js.Object {
  @JSName("name")
  var _name: js.UndefOr[String] = js.undefined
  @JSName("age")
  var _age: js.UndefOr[Int] = js.undefined
}

object Builder {
  def personalAttributes(name: Option[String] = None, age: Option[Int] = None): PersonalAttributes = {
    new PersonalAttributes {
      _name = name.orUndefined
      _age = age.orUndefined
    }
  }
}

@JSExport
object ScalaJSExample {
  @JSExport
  def main(container: HTMLElement): Unit = {
    val myName = Some("Steve")
    val myAge: Option[Int] = None //Some(47)

    val attributes = Builder.personalAttributes(name = Some("Steve"), age = Some(47))
    println(JSON.stringify(attributes))
  }
}
