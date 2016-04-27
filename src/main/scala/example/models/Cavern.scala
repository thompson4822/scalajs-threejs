package example.models

import java.util.Date

import example.models.MapTile.MapTile

import scala.collection.{Set, mutable}
import scala.util.Random

object Cavern {
  val seed = new Date().getTime
  val rand = new Random(seed)

  // Create a cavern with the given information
  def apply(width: Int, height: Int, iterations: Int = 6, birthLimit: Int = 4, deathLimit: Int = 3): Cavern = {
    val initial = Array.fill[MapTile](height, width) {
      if (rand.nextDouble() < 0.40) MapTile.Wall else MapTile.Floor
    }
    var cave = Cavern(initial, birthLimit, deathLimit)
    (0 to iterations).foreach(_ => cave = nextGeneration(cave))
    cave
  }

  // Use simple cellular automata rules to generate a cavern map
  def nextGeneration(cave: Cavern): Cavern = {
    val newMap = Array.fill[MapTile](cave.height, cave.width){MapTile.Floor}
    for{
      rowIndex <- 0 until cave.height
      colIndex <- 0 until cave.width
      if(shouldBeWall(cave, rowIndex, colIndex))
    } yield {
      newMap(rowIndex)(colIndex) = MapTile.Wall
    }
    cave.copy(map = newMap)
  }

  // Should the given row and column in the provided map be a wall?
  def shouldBeWall(cave: Cavern, row: Int, col: Int): Boolean = {
    val map = cave.map
    val walls: Int = (for {
      rowIndex <- (row - 1) to (row + 1)
      colIndex <- (col - 1) to (col + 1)
      if !(rowIndex == row && colIndex == col) && (rowIndex < 0 || rowIndex >= cave.height || colIndex < 0 || colIndex >= cave.width || map(rowIndex)(colIndex) == MapTile.Wall)
    } yield 1).sum
    if(map(row)(col) == MapTile.Wall) walls >= cave.deathLimit else walls > cave.birthLimit
  }
}

case class Cavern(map: MapType, birthLimit: Int, deathLimit: Int) {
  /// What is a map where ~40% of its tiles are walls and the rest floor?

  def height = map.length

  def width = map(0).length

  /// Construct a cavern that is the subdivided form of an existing cavern.
  def subdivide: Cavern = {
    // Subdivide the current map. Could be done several times to get finer resolution
    val newCave = Array.tabulate[MapTile](height * 2, width * 2) { case (row, col) => map(row / 2)(col / 2) }
    this.copy(map = newCave)
  }

  /// Smooth out the current map so the walls flow more naturally
  /// (NOTE - only useful in larger maps. In small maps, cavern structure can be lost using this)
  def smooth: Cavern = {
    // TODO - I don't think there is getting around the side effects. In this method they are necessary.
    val result: MapType = map.clone()
    println(s"width/height = ${width}/${height}")
    for {
      rowIndex <- 0 until height
      colIndex <- 0 until width
      if map(rowIndex)(colIndex) == MapTile.Wall && wallCountMoore(rowIndex, colIndex, 1) == 3
    } result(rowIndex)(colIndex) = MapTile.Floor
    this.copy(map = result)
  }

  // Simple algorithm to count all of the walls within distance of the given location,
  // not including the location itself.
  def wallCountMoore(row: Int, col: Int, distance: Int): Int = {
    var walls = 0;
    for {
      rowIndex <- -distance to distance
      colIndex <- -distance to distance
      currentRow = row + rowIndex
      currentCol = col + colIndex
    } yield {
      if (currentRow < 0 || currentRow >= height || currentCol < 0 || currentCol >= width || map(currentRow)(currentCol) == MapTile.Wall)
        if (!(rowIndex == 0 && colIndex == 0))
          walls += 1
    }
    walls
  }

  // Just how much of the map is navigable (is flooring)?
  def percentNavigable: Double = {
    val floor = for {
      row <- map
      col <- row
      if col == MapTile.Floor
    } yield col
    floor.size / (map.length * map(0).length)
  }

  // Is the tile at the given row and column a floor?
  def isFloor(row: Int, col: Int): Boolean =
    map(row)(col) == MapTile.Floor

  /// What is the printable string representation of this cavern?
  override def toString: String = {
    def rowString(row: Array[MapTile]): String = {
      row.map(cell => if(cell == MapTile.Floor) '.' else '#').mkString
    }
    (for {
      row <- map
      text = rowString(row)
    } yield text).mkString("\n")
  }


}

