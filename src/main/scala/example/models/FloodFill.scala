package example.models

import example.models.MapTile.MapTile

/**
  * Created by steve at OverByte on 4/26/16.
  */
class FloodFill(cavern: Cavern) {

  // Get all of the floor positions
  def getFloor: Set[Point] = {
    (for {
      row <- 0 until cavern.height
      col <- 0 until cavern.width
      if cavern.isFloor(row, col)
    } yield Point(row, col)).toSet
  }

  def subcaverns: Seq[Set[Point]] = {
    def recSubcaverns(floorRemaining: Set[Point], accum: Seq[Set[Point]]): Seq[Set[Point]] = floorRemaining match {
      case floor if floor.isEmpty => accum
      case other =>
        val point = other.head
        val room = getRoom(point)
        recSubcaverns(floorRemaining.diff(room), accum :+ room)
    }
    val allFloor = getFloor
    recSubcaverns(allFloor, Nil)
  }

  // This function has decent performance for what we need.
  def getRoom(point: Point): Set[Point] = {
    import scala.collection.mutable.{Set => MSet}
    var accum: MSet[Point] = MSet[Point]()
    //val (row, col) = point.row -> point.col
    def recGetRoom(point: Point): MSet[Point] = {
      if(isWall(point) || accum.contains(point))
        return accum
      else {
        accum += point
      }
      accum = recGetRoom(point.right)
      accum = recGetRoom(point.left);
      accum = recGetRoom(point.down);
      recGetRoom(point.up);
    }
    recGetRoom(point).toSet
  }

  def isWall(point: Point): Boolean =
    point.row < 0 || point.row >= cavern.height ||
    point.col < 0 || point.col >= cavern.width ||
    cavern.map(point.row)(point.col) == MapTile.Wall

  // Fill all but the largest subcavern
  def fillSecondary: Cavern = {
    // Produce a map that is all wall
    val newMap = Array.fill[MapTile](cavern.height, cavern.width){ MapTile.Wall }
    // Carve the floor out of the map
    val largestRoom = subcaverns.sortBy(_.size).reverse.head
    largestRoom.foreach(point => newMap(point.row)(point.col) = MapTile.Floor)
    cavern.copy(map = newMap)
  }

}
