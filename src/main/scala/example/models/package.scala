package example

import example.models.MapTile.MapTile

/**
  * Created by steve at OverByte on 4/26/16.
  */
package object models {

  type MapType = Array[Array[MapTile]]

  object MapTile extends Enumeration {
    type MapTile = Value
    val Floor, Wall = Value
  }
}
