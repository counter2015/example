package io.github.counter2015

object UrlShortener {
  val chars: Seq[Char] = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')

  def randChar: Char = chars(scala.util.Random.nextInt(chars.size))

  def nextFreeToken: String = (1 to 8).foldLeft("")((acc, _) => acc + randChar)
}
