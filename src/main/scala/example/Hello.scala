package example

import better.files.File
import scopt.OParser

case class Config(
                   path: String = "src/main/resources/input"
                 )

object Hello extends App {
  private val outputFilePath = "src/main/resources/output"
  private val builder = OParser.builder[Config]
  private val parser1 = {
    import builder._
    OParser.sequence(
      programName("Hello world app"),
      head("Hello world app", "1.0"),
      opt[String]('p', "path")
        .action((x, c) => c.copy(path = x))
        .text("Path represents path to an input file"),
    )
  }

  OParser.parse(parser1, args, Config()) match {
    case Some(config) =>
     val inputFile = File(config.path)
      val outputFile = File(outputFilePath)
      outputFile.write(inputFile.lines.map(line => Integer.parseInt(line)).sum.toString)
    case _ =>
    println("Something went wrong. Normal processing aborted")
  }
}
