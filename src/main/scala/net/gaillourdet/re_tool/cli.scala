package net.gaillourdet.re_tool

import scopt.OptionParser

sealed trait Mode
case object Gte extends Mode
case object Gt extends Mode

case class Parameters(
  mode: Option[Mode] = None,
  num: Option[BigDecimal] = None
)

object Main extends App {

  val parser = new OptionParser[Parameters]("re.tool") {
    head("re.tool", "0.1")

    cmd("gte")
      .action((_: Unit, params: Parameters) => params.copy(mode = Some(Gte)))
      .text("generate a regular expression matching a any greater or equal than the given one")
      .children(
        arg[BigDecimal]("<NUMBER>")
          .action((n, params) => params.copy(num = Some(n)))
          .text("the number to match")
      )

    cmd("gt")
      .action((_: Unit, params: Parameters) => params.copy(mode = Some(Gt)))
      .text("generate a regular expression matching a any greater than the given one")
      .children(
        arg[BigDecimal]("<NUMBER>")
          .action((n, params) => params.copy(num = Some(n)))
          .text("the number to match")
      )
  }

  parser.parse(args, Parameters()) match {
    case Some(Parameters(Some(Gt), Some(n)))  => println(Gen.greaterThan(n.toInt))
    case Some(Parameters(Some(Gte), Some(n))) => println(Gen.greaterThanOrEqual(n.toInt))
    case Some(Parameters(None, _))            => parser.errorOnUnknownArgument
    case Some(Parameters(_, None))            => parser.errorOnUnknownArgument
    case None                                 => parser.errorOnUnknownArgument
  }
}


