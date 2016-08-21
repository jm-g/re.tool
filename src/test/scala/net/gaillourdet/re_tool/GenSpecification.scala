package net.gaillourdet.re_tool

import org.scalacheck._


/**
  * Created by jmg on 21.08.16.
  */
class GenSpecification extends Properties("Gen") {
  import Prop.{forAll, _}

  property("matches correctly") = forAll { (boundary: Int, testValue: Int) =>
    testValue.toString.matches(Gen.largerThan(boundary)) == (testValue > boundary)
  }
}
