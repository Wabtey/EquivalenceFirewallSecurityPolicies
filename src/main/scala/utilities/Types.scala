package utilities

// This is the type of rules in the Scala front-end
sealed trait Rule
case class ExtAccept(l: List[Address]) extends Rule
case class ExtDrop(l: List[Address]) extends Rule

// This is the type of addresses in the Scala front-end
case class Address(n1: Int, n2: Int, n3: Int, n4: Int)
