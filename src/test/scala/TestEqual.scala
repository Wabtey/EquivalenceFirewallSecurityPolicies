package tp5_export
import org.junit.Test
import org.junit.Assert._
import utilities._

class TestEqual {
  // Write your unit tests here!
  @Test
  def test0(): Unit = {
    val table1 =
      List(
        (ExtDrop(List(Address(1, 1, 1, 1), Address(3, 3, 3, 3)))),
        (ExtAccept(List(Address(2, 2, 2, 2), Address(3, 3, 3, 3))))
      )

    assertTrue("First test", EqualObject.equal(table1, table1))
  }

  @Test
  def testNoDrop(): Unit = {
    val table1 =
      List(
        (ExtDrop(List(Address(1, 1, 1, 1), Address(3, 3, 3, 3)))),
        (ExtAccept(List(Address(2, 2, 2, 2), Address(3, 3, 3, 3))))
      )
    val table2 =
      List(
        (ExtAccept(List(Address(2, 2, 2, 2))))
      )

    assertTrue("Half Drop test", EqualObject.equal(table1, table2))
  }
}
