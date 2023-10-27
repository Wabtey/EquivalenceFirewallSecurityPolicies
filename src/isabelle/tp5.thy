theory tp5
imports Main "~~/src/HOL/Library/Code_Target_Nat"
begin

type_synonym address= "nat*nat*nat*nat"
datatype rule= Drop "(address list)" | Accept "(address list)" 
type_synonym chain= "rule list"

definition "aChain1= [(Drop [(1,1,1,1)]),(Accept [(1,1,1,1),(2,2,2,2)])]"
definition "aChain2= [(Accept [(1,1,1,1),(2,2,2,2)]),(Drop [(1,1,1,1)])]"
definition "aChain3= [(Accept [(2,2,2,2)])]"

(* ---------   This is the TRUSTED BASE! So do not modify those two functions! ------------ *)
(* But, you SHOULD carefully read their code to understand how chains are used *)

(* The function defining if an address is accepted by a chain *)
fun filter:: "address \<Rightarrow> chain \<Rightarrow> bool"
  where
"filter a [] = False" |
"filter a ((Accept al)#r) = (if (List.member al a) then True else (filter a r))"|
"filter a ((Drop al)#r) = (if (List.member al a) then False else (filter a r))" 

value "filter (1,1,1,1) aChain1"
value "filter (1,1,1,1) aChain2"
value "filter (2,2,2,2) aChain1"
value "filter (2,2,2,2) aChain2"

(* ------------------------------------------------------------------------------------------ *)
(* TP/Lab session starts here!*)

(* Counterexample generators and some useful options for this lab session *)
(* nitpick *)
(* nitpick [timeout=120] *)
(* quickcheck [tester=exhaustive] *)
(* quickcheck [tester=narrowing] *)
(* quickcheck [tester=narrowing, size=7, timeout=120] *)
(* Delete all iteration of e in the given list *)

fun delete::"'a => 'a list => 'a list"
  where
    "delete _ [] = []" |
    "delete e (x # xs) = (
      if (e=x) then delete e xs
      else (x # delete e xs)
    )"

(* list1 - list2 *)
fun difference::"'a list \<Rightarrow> 'a list \<Rightarrow> 'a list"
  where
  "difference _ [] = []" |
  "difference xs (y # ys) = difference (delete y xs) ys"
  (*
  "difference xs (y # ys) = (
    if List.member xs y then difference (delete y xs) ys
    else difference xs ys
  )"
  *)

fun intersection::"'a list => 'a list => 'a list"
  where
 (* "intersection _ [] = []" | *)
    "intersection [] _ = []" |
    "intersection (x # xs) ys = (
      if List.member ys x \<and> \<not>List.member xs x then (x # (intersection xs ys))
      else intersection xs ys
    )"

(*
  The first rules override the following.
  As we are working backwards,
  a list of rules (Accept A, Drop D) will contain all the A
*)
fun acceptedAddresses::"chain \<Rightarrow> address list"
  where
  "acceptedAddresses [] = []" |
  "acceptedAddresses ((Accept as) #rs) = (
    List.union (acceptedAddresses rs) as
  )" |
  "acceptedAddresses ((Drop as) #rs) = (
    difference (acceptedAddresses rs) as
  )"

(* The function/predicate to program and to prove! *)
fun equal:: "chain \<Rightarrow> chain \<Rightarrow> bool"
  where
"equal c1 c2 =
   acceptedAddresses c1 = acceptedAddresses c2
"

value "equal aChain1 aChain2"

lemma "equal c1 c2 \<longrightarrow> True"
  nitpick  [timeout=120]
  quickcheck [tester=narrowing]
  oops

(* Code exportation directive *)
export_code equal in Scala
end