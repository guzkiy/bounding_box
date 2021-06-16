package com.evenfinancial.boundingbox.tests

import com.evenfinancial.boundingbox.Solution
import org.scalatest.funsuite.AnyFunSuite

class BoxFuncTestSuite  extends AnyFunSuite  {
  test("extend a box") {
    val box =  ( (1,3), (12,16) )
    assert(Solution.extendBox(box, (14,4)) == ((1,3), (14,16)) )
    assert(Solution.extendBox(box, (2,5)) == box )
  }

  test("inside the box") {
    val box1 = ((0,0),(11,3))
    assert(Solution.isInsideBox(box1, (4,0)))
  }

  test("is box overlap") {
    val box1 = ((0,0),(2,2))
    val box2 = ((1,1),(3,3))
    val box3 = ((0,0),(1,1))
    val box4 = ((2,2),(3,3))
    val box5 = ((1,0),(2,1))
    assert(Solution.isBoxOverlap(box1,box2))
    assert(!Solution.isBoxOverlap(box3,box4))
    assert(!Solution.isBoxOverlap(box3,box5))
  }

}
