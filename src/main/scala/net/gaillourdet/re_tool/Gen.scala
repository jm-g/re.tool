package net.gaillourdet.re_tool

case class Digit(value: Int)

object Gen {
  import Digit._

  def largerThan(x: Int): String = {
    val digits = x.toString.toList.map(toDigit)
    "[0-9]*" + digits.init.map(digitLTE).mkString("") + digitLT(digits.last) + "([^0-9]|$)"
  }


  def digitLTE(digit: Digit): String = {
    s"[${fromDigit(digit)}-9]"
  }

  def digitLT(digit: Digit): String = {
    require(digit.value + 1 <= 9)
    s"[${digit.value+1}-9]"
  }

}
