; Simon Shrestha
; CS 326
; HW #2

; Question 1
(define (subst x y l)
 (cond
  ((null? l) '())
  ((list? (car l)) (cons (subst x y (car l)) (subst x y (cdr l))))
  ((eq? (car l) x) (cons y (subst x y (cdr l))))
  (else (cons (car l) (subst x y (cdr l))))))

; Question 2
(define (all-different? L)
    (if (pair? L)
      (and (not (member (car L) (cdr L)))
        (all-different? (cdr L))) #T) )

;Question 3
(define T
 '(13
(5
 (1 () ())
 (8 ()
 (9 () ())))
(22
(17 () ())
(25 () ()))))

(define (left T)
  (car (cdr T)))

(define (right T)
  (car (cdr (cdr T))))

(define (val T)
  (car T))

; Question 3 a
(define (n-nodes T)
  (cond 
    ((null? T) 0)
      (else (+ 1
        (n-nodes (left  T))
        (n-nodes (right T))))))

; Question 3 c
(define (height T)
    (cond
      ((null? T) -1) 
      (else (+ 1 (max (height (left T)) (height (right T)))))))

;Question 3 b
(define (n-leaves T)
  (cond 
      ((null? T) 0) 
      ((and (null? (left T)) (null? (right T))) 1)
      (else (+ (n-leaves (left T)) (n-leaves (right T)))))) 

;Question 3 d
(define (postorder T)
  (cond 
    [(equal? (length T) 0) '()]
      [else (append (postorder (left T)) (postorder (right T)) (list (val T)))]))


; Question 4
(define (flatten L)
  (cond 
    ((null? L) '())
    ((not (pair? L)) (list L))
      (else (append (flatten (car L))
    (flatten (cdr L))))))



;Question 5 (Extra Credit)
(define (member-bst? V T)
  (cond
    [(equal? (length T) 0) #f]
      [else (cond
        [(equal? V (val T)) #T]
          [else ( cond [(> V (val T)) (member-bst? V (right T))]
            [else (member-bst? V (left T))])])]))
