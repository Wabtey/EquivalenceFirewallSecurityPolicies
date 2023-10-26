package tp5_export

// Here you should paste the Scala code generated from Isabelle/HOL
// and replace all the code below (object tp5 and Nat)
object tp5 {
  abstract sealed class rule
  final case class Drop(a: List[(Nat.nat, (Nat.nat, (Nat.nat, Nat.nat)))])
      extends rule
  final case class Accept(a: List[(Nat.nat, (Nat.nat, (Nat.nat, Nat.nat)))])
      extends rule
}

object Nat {
  abstract sealed class nat
  final case class Nata(a: BigInt) extends nat
}
