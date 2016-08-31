package net.gaillourdet.re_tool

case class Digit(value: Int)

object Gen {

  def gt: Int => String = seperateDigits _ andThen (wrapDigits(_, gtDigits)) andThen render
  def gte: Int => String = seperateDigits _ andThen (wrapDigits(_, gteDigits)) andThen render

  def greaterThan: Int => String = gt
  def greaterThanOrEqual: Int => String = gte

  def seperateDigits(x: Int): List[Digit] = x.toString.toList.map(toDigit)

  sealed trait Regex
  case class RChar(char: Char) extends Regex
  case class RClass(chars: List[Char]) extends Regex
  case class ROr(left: Regex, right: Regex) extends Regex
  case class RSeq(rs: List[Regex]) extends Regex
  case class RAtLeast(n: Int, re: Regex) extends Regex
  case class RNegLookAhead(re: Regex) extends Regex
  case class RStar(re: Regex) extends Regex

  def render(re: Regex): String = re match {
    case RChar(c)          => c.toString
    case RClass(cs)        => s"[${cs.mkString}]"
    case RSeq(res)         => res.map(render).mkString
    case ROr(l,r)          => s"(${render(l)}|${render(r)})"
    case RAtLeast(n, re)   => s"${render(re)}{$n,}"
    case RNegLookAhead(re) => s"(?!${render(re)})"
    case RStar(re)         => s"(${render(re)})*"
  }

  // gte
  def gteDigit(d: Digit): Regex = d match {
    case Digit(d) => RClass(Range.inclusive(d, 9).map(Digit.apply _ andThen fromDigit).toList)
  }

  def gtDigit(d: Digit): Option[Regex] = d match {
    case Digit(d) if d < 9 => Some(RClass(Range.inclusive(d + 1, 9).map(Digit.apply _ andThen fromDigit).toList))
    case _                 => None
  }


  def wrapDigits(digits: List[Digit], compiler: List[Digit] => Regex): Regex = {
    RSeq(
      ROr(
        RSeq(RStar(anyDigit) :: nonZero :: atLeastDigits(digits.length) :: Nil),
        RSeq(RStar(zero) :: compiler(digits) :: Nil)
      ) :: noDigit :: Nil)
  }


  def gteDigits(digits: List[Digit]): Regex = digits match {
    case Nil => ???
    case d :: Nil => gteDigit(d)
    case d :: ds => gtDigit(d) match {
      case Some(gtRegex) =>
        ROr(RSeq(gteDigit(d) :: gteDigits(ds) :: Nil), RSeq(gtRegex :: atLeastDigits(ds.length) :: Nil))
      case None =>
        RSeq(gteDigit(d) :: gteDigits(ds) :: Nil)
    }
  }

  def gtDigits(digits: List[Digit]): Regex = digits match {
    case Nil => ???
    case d :: Nil => gtDigit(d) match {
      case Some(re) => re
      case None => never
    }
    case d :: ds => gtDigit(d) match {
      case Some(gtRegex) =>
        ROr(RSeq(gteDigit(d) :: gtDigits(ds) :: Nil), RSeq(gtRegex :: atLeastDigits(ds.length) :: Nil))
      case None =>
        RSeq(gteDigit(d) :: gtDigits(ds) :: Nil)
    }
  }

  def zero: Regex = RChar('0')
  def nonZero: Regex = RClass(Range.inclusive(1, 9).map(Digit andThen fromDigit).toList)
  def anyDigit: Regex = RClass(Range.inclusive(0, 9).map(Digit andThen fromDigit).toList)

  def atLeastDigits(len: Int): Regex = RAtLeast(len,anyDigit)

  def noDigit: Regex = RNegLookAhead(anyDigit)

  def never: Regex = RSeq(RNegLookAhead(RChar('a')) :: RChar('a') :: Nil)

  def digitLTE(digit: Digit): String = {
    s"[${fromDigit(digit)}-9]"
  }

  def digitLT(digit: Digit): String = {
    require(digit.value + 1 <= 9)
    s"[${digit.value+1}-9]"
  }

}
