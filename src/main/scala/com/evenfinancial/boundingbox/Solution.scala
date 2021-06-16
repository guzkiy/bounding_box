package com.evenfinancial.boundingbox

import scala.io.Source

object Solution {
  type CellType = (Int, Int)
  type Grid = Array[Array[Boolean]]
  type Box = (CellType, CellType)

  /**
   * Extends the minimal bounding box b to include the new point c
   * @param b
   * @param c
   * @return
   */
  def extendBox(b: Box, c: CellType): Box = {
    val (tl, br) = b
    ((tl._1 min c._1, tl._2 min c._2), (br._1 max c._1, br._2 max c._2))
  }

  /**
   * checks if the point c is inside the minimum bounding box b
   * @param b
   * @param c
   * @return
   */
  def isInsideBox(b: Box, c: CellType) = {
    val (tl, br) = b
    val (x, y) = c
    (x>=tl._1 && x<=br._1 && y>= tl._2 && y<=br._2)
  }

  /**
   * returns true if minimal bounding boxes b1 and b2 overlap
   * @param b1
   * @param b2
   * @return
   */
  def isBoxOverlap(b1: Box, b2: Box) = {
    val (tl1, br1) = b1
    val (tl2, br2) = b2
    !(br1._1 <= tl2._1 || br1._1 <= tl2._2 || tl1._1 >= br2._1 || tl1._2 >= br2._1)
  }

  /**
   * calculates the area of the box
   * that is used as a measure to select the biggest box
   * @param box
   * @return
   */
  def calcBoxArea(box: Box) = {
    val (tl, br) = box
    (br._1 - tl._1+1)*(br._2-tl._2+1)
  }

  /**
   * translate zero based coordinates to one based
   * @param c
   * @return
   */
  def to1based(c: CellType) = (c._1+1, c._2+1)

  /**
   * the function finds the minimal bounding box for the cluster of connected points
   * it uses DFS-like algorithm implemented in functional style, assuming points can be connected either vertically
   * or horizontally but not diagonally
   *
   * @param g grid definition
   * @param boxes a set of accumulated so far bounding boxes
   * @param visited a set of cells visited so far
   * @param startPoint starting point for the search
   * @return a tuple (boxes: Set[Box], visited: Set[CellType])
   */
  def findBoundingBox(g: Grid, boxes: Set[Box], visited: Set[CellType], startPoint: CellType) =  {
    val gridDims = ( (0, 0), (g(0).length-1, g.length-1))

    // helper function is called recursively visiting neighbours horizontally and vertically
    def helper(state:( Box, Set[ CellType ]), c: CellType): ( Box, Set[ CellType ]) = {
      val (boundingBox, visited) = state
      val (x, y) = c
      // check the cell c is '*' and is inside the grid and not visited previously
      if (isInsideBox(gridDims, c) && g(y)(x) && !visited.contains(c))  {
        val links = List( (x+1, y), (x-1, y), (x, y+1), (x, y-1) )
        // recursively call the helper() folding the results
        // the bounding box may be extended by subsequent calls
        links.foldLeft( (extendBox(boundingBox, c), visited+c))(helper)
      }
      else state
    }
    // start from a single point bounding box and extending it as we discover the connections
    val startBox = (startPoint,startPoint)
    val (boundingBox, updatedVisited) = helper((startBox, visited), startPoint )
    // we may get a small bounding box if the startCell stands alone but the cell has to be '*'
    // otherwise we prevent adding startCell bounding box
    (if (! g(startPoint._2)(startPoint._1) ) boxes else boxes + boundingBox, updatedVisited)
  }

  def main(args: Array[String]): Unit = {
    // getting the grid from the stdin
    val grid = Source.fromInputStream(System.in).getLines().map( r => r.map( _=='*').toArray ).toArray
    // all grid indexes
    val cells = for(x <- grid.last.indices; y <- grid.indices) yield (x,y)
    // separating connected groups and collecting their bounding boxes
    val (boundingBoxes, _) = cells.foldLeft( (Set.empty[Box], Set.empty[CellType]) ) { (a, c) => findBoundingBox(grid, a._1, a._2, c) }
    // filtering out those that overlap
    val nonOverlapping = boundingBoxes.filterNot{ box => (boundingBoxes - box).exists(isBoxOverlap(box, _)) }

    if (nonOverlapping.nonEmpty) {
      // reducing to take the largest by area
      val largest = nonOverlapping.reduce{ (l,r) => if (calcBoxArea(l)>calcBoxArea(r)) l else r }
      println(s"${to1based(largest._1)}${to1based(largest._2)}")
    }
    else {
      println("No boxes satisfying the requirements found")
    }
  }
}
