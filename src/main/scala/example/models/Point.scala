package example.models

/**
  * Created by steve at OverByte on 4/26/16.
  */
case class Point(row: Int, col: Int) {
  def left: Point = this.copy(col = col - 1)
  def right: Point = this.copy(col = col + 1)
  def up: Point = this.copy(row = row - 1)
  def down: Point = this.copy(row = row + 1)
}
