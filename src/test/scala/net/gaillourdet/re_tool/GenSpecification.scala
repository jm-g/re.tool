package net.gaillourdet.re_tool

import net.gaillourdet.re_tool.Gen._
import org.scalacheck._


/**
  * Created by jmg on 21.08.16.
  */
class GenSpecification extends Properties("Gen") {
  import Prop.{BooleanOperators, forAll}
  import SpecHelper._

  property("greaterThan matches correctly") =
    forAll (posInt, posInt, posInt) { (boundary, testValue, leadingZeros) =>
      val zeros = List.fill(Math.log10(leadingZeros).toInt)('0').mkString
      (zeros + testValue.toString).matches(greaterThan(boundary)) == (testValue > boundary)
    }

  property("greaterThanOrEqual matches correctly") =
    forAll(posInt, posInt, posInt) { (boundary, testValue, leadingZeros) =>
      val zeros = List.fill(Math.log10(leadingZeros).toInt)('0').mkString
      (zeros + testValue.toString).matches(greaterThanOrEqual(boundary)) == (testValue >= boundary)
    }

  property("fromDigit is a string of length 1") = forAll(digitGen) { (d) =>
    fromDigit(digit(d)).toString.length == 1
  }

  property("digit bijective") = forAll { (digit: Int) =>
    (Range.inclusive(0,9) contains digit) ==>
      (Digit(digit) == toDigit(fromDigit(Digit(digit))))
  }
}
