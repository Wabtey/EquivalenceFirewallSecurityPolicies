package tp5_export

object HOL {

  trait equal[A] {
    val `HOL.equal`: (A, A) => Boolean
  }
  def equal[A](a: A, b: A)(implicit A: equal[A]): Boolean = A.`HOL.equal`(a, b)
  object equal {
    implicit def `Product_Type.equal_prod`[A: equal, B: equal]: equal[(A, B)] =
      new equal[(A, B)] {
        val `HOL.equal` = (a: (A, B), b: (A, B)) =>
          Product_Type.equal_proda[A, B](a, b)
      }
    implicit def `Nat.equal_nat`: equal[Nat.nat] = new equal[Nat.nat] {
      val `HOL.equal` = (a: Nat.nat, b: Nat.nat) => Nat.equal_nata(a, b)
    }
  }

  def eq[A: equal](a: A, b: A): Boolean = equal[A](a, b)

} /* object HOL */

object Code_Numeral {

  def integer_of_nat(x0: Nat.nat): BigInt = x0 match {
    case Nat.Nata(x) => x
  }

} /* object Code_Numeral */

object Nat {

  abstract sealed class nat
  final case class Nata(a: BigInt) extends nat

  def equal_nata(m: nat, n: nat): Boolean =
    Code_Numeral.integer_of_nat(m) == Code_Numeral.integer_of_nat(n)

} /* object Nat */

object Product_Type {

  def equal_proda[A: HOL.equal, B: HOL.equal](x0: (A, B), x1: (A, B)): Boolean =
    (x0, x1) match {
      case ((x1, x2), (y1, y2)) => HOL.eq[A](x1, y1) && HOL.eq[B](x2, y2)
    }

} /* object Product_Type */

object Lista {

  def fold[A, B](f: A => B => B, x1: List[A], s: B): B = (f, x1, s) match {
    case (f, x :: xs, s) => fold[A, B](f, xs, (f(x))(s))
    case (f, Nil, s)     => s
  }

  def member[A: HOL.equal](x0: List[A], y: A): Boolean = (x0, y) match {
    case (Nil, y)     => false
    case (x :: xs, y) => HOL.eq[A](x, y) || member[A](xs, y)
  }

  def insert[A: HOL.equal](x: A, xs: List[A]): List[A] =
    (if (member[A](xs, x)) xs else x :: xs)

  def union[A: HOL.equal]: (List[A]) => (List[A]) => List[A] =
    (
        (a: List[A]) =>
          (b: List[A]) =>
            fold[A, List[A]](
              ((aa: A) => (ba: List[A]) => insert[A](aa, ba)),
              a,
              b
            )
    )

} /* object Lista */

object tp5 {

  abstract sealed class rule
  final case class Drop(a: List[(Nat.nat, (Nat.nat, (Nat.nat, Nat.nat)))])
      extends rule
  final case class Accept(a: List[(Nat.nat, (Nat.nat, (Nat.nat, Nat.nat)))])
      extends rule

  def delete[A: HOL.equal](uu: A, x1: List[A]): List[A] = (uu, x1) match {
    case (uu, Nil) => Nil
    case (e, x :: xs) =>
      (if (HOL.eq[A](e, x)) delete[A](e, xs) else x :: delete[A](e, xs))
  }

  def difference[A: HOL.equal](uu: List[A], x1: List[A]): List[A] =
    (uu, x1) match {
      case (uu, Nil)     => Nil
      case (xs, y :: ys) => difference[A](delete[A](y, xs), ys)
    }

  def acceptedAddresses(
      x0: List[rule]
  ): List[(Nat.nat, (Nat.nat, (Nat.nat, Nat.nat)))] =
    x0 match {
      case Nil => Nil
      case Accept(as) :: rs =>
        Lista
          .union[(Nat.nat, (Nat.nat, (Nat.nat, Nat.nat)))]
          .apply(acceptedAddresses(rs))
          .apply(as)
      case Drop(as) :: rs =>
        difference[(Nat.nat, (Nat.nat, (Nat.nat, Nat.nat)))](
          acceptedAddresses(rs),
          as
        )
    }

  def listEquality[A](list1: List[A], list2: List[A]): Boolean =
    listEquality[A](list1, list2)

  def equal(c1: List[rule], c2: List[rule]): Boolean =
    listEquality[(Nat.nat, (Nat.nat, (Nat.nat, Nat.nat)))](
      acceptedAddresses(c1),
      acceptedAddresses(c2)
    )

} /* object tp5 */
