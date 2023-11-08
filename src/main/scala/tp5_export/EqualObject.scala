package tp5_export

import utilities._
import Converter._

object EqualObject {

  // **This is the function that you have to implement**
  // Its implementation is simply a call to the equal function of the object exported
  // from Isabelle/HOL theories. The call you have to write should be something of the form
  // tp5.equal(t1,t2)
  def equal(t1: List[Rule], t2: List[Rule]): Boolean = {
    tp5.equal(t1, t2)
  }
}

// ----------------------------------------------------------------------------------------------------
// You do not need to modify code below this this part
// This object permits to convert Scala List[Rule] type to the type of rules
// used in the Isabelle/HOL export that should be in object tp5
import scala.language.implicitConversions
object Converter {
  implicit def ext2isabelle(rl: List[Rule]): List[tp5.rule] =
    rl match {
      case Nil => List()
      case (ExtAccept(adr_list)) :: rem =>
        (tp5.Accept(adr_list.map(address_convert))) :: ext2isabelle(rem)
      case (ExtDrop(adr_list)) :: rem =>
        (tp5.Drop(adr_list.map(address_convert))) :: ext2isabelle(rem)
    }

  def address_convert(a: Address): (Nat.nat, (Nat.nat, (Nat.nat, Nat.nat))) =
    a match {
      case Address(n1, n2, n3, n4) =>
        (
          (if (n1 >= 0 && n1 < 256) (Nat.Nata(n1): Nat.nat)
           else (Nat.Nata(0): Nat.nat)),
          (
            (if (n2 >= 0 && n2 < 256) (Nat.Nata(n2): Nat.nat)
             else (Nat.Nata(0): Nat.nat)),
            (
              (if (n3 >= 0 && n3 < 256) (Nat.Nata(n3): Nat.nat)
               else (Nat.Nata(0): Nat.nat)),
              ((if (n4 >= 0 && n4 < 256) (Nat.Nata(n4): Nat.nat)
                else (Nat.Nata(0): Nat.nat)))
            )
          )
        )
    }
}
