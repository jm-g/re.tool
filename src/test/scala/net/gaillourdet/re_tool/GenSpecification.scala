/*
 *     re.tool - a regular expression generation tool
 *     Copyright (C) 2016  Jean-Marie Gaillourdet
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
