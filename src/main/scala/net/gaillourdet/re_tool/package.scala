package net.gaillourdet

/**
  * Created by jmg on 21.08.16.
  */
package object re_tool {

  def digit(x: Int) = {
    require(x >= 0, ">= 0")
    require(x <= 9, "<= 9x")
    Digit(x)
  }

  def toDigit(c: Char): Digit = {
    digit(Integer.parseInt(c.toString))
  }

  def fromDigit(d: Digit): Char = {
    d.value.toString.charAt(0)
  }


}
