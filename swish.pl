%Simon Shrestha
%CS 326
%HW #7

%Question 1
%Part a
isSet(S) :-
	setof(X, member(X, S), Set),
	length(S, N),
	length(Set, N).
%Part b
subset([A|R],S) :- 
	member(A,S), 
	subset(R,S).
	subset([],_).
%Part c
union([A|Y],B,C) :- 
	member(A,B), 
	!,        
	union(Y,B,C). 
union([A|Y],B,[A|C]) :- 
	union(Y,B,C).
union([],B,B).
%Part d
intersection([A|Y],B,[A|C]) :- 
    member(A,B), 
    intersection(Y,B,C).
intersection([A|Y],B,C) :- 
    not(member(A,B)), 
    intersection(Y,B,C).
intersection([],B,[]).

%Question 2
tally(_, [], 0).
tally(E, [E | L], N) :-
	!, 
	tally(E, L, N1),
	N is N1 + 1.
tally(E, [_ | L], N) :-
	tally(E, L, N).

%Question 3
and(A,B) :- A, B.

or(A,_) :- A.
or(_,B) :- B.

equ(A,B) :- or(and(A,B), and(not(A),not(B))).

xor(A,B) :- not(equ(A,B)).

nor(A,B) :- not(or(A,B)).

nand(A,B) :- not(and(A,B)).

%Question 4
gcd(X, 0, X):- !.
gcd(0, Y, Y):- !.
gcd(X, Y, G):- 
	X > Y, 
	!, 
	Z is X mod Y, 
	gcd(Y, Z, G).
gcd(X, Y, G):- 
	Z is Y mod X, 
	gcd(X, Z, G).

%Question 5
flatten(A, B):-
	flatten(A, [], B).
	flatten([], B, B).
flatten([List|Tail], L1, B):-
	flatten(List, L2, B),
	flatten(Tail, L1, L2).
flatten(List, B, [List|B]):-
	 not(is_list(List)).










