package tp5_export

import utilities._

object Main {
  def main(args: Array[String]): Unit = {
    // You can perform simple tests. For more extensive testing you should
    // implement them in the test/scala/TestEqual.scala JUnit file
    val table1 = List(
      (ExtAccept(List(Address(1, 1, 1, 1), Address(3, 3, 3, 3))))
    )
    val table2 = List(
      (ExtAccept(List(Address(2, 2, 2, 2), Address(3, 3, 3, 3))))
    )
    println(EqualObject.equal(table1, table2))

    // Call to the automatic tester
    // It takes around one minute to perform the testing
    println("\n\nAutomatic testing\n-----------------\n")
    checker.ExhaustiveTester.runTests(EqualObject.equal(_, _))
  }
}
