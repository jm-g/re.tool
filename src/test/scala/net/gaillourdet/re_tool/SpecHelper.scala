package net.gaillourdet.re_tool

import org.scalacheck.{Gen => SCGen}

object SpecHelper {

  val digitGen  = SCGen.oneOf(Range.inclusive(0,9))

}
