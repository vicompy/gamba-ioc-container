package org.gro.lispy.parser;

import static org.junit.Assert.*;
import org.junit.Test;

public class ParserTest {

	@Test
	public void testBasics() {

		// aggregates basics: nested, vars, stupid expressions
		assertEquals("[12]", parse("( (+ 1 2 3 (+ 1 2 3)) )"));
		assertEquals("[12]", parse("( (+ 1 (+ 1 2 3)2 3 ) )"));
		assertEquals("[12]", parse("( (+ (+ 1 2 3)1 2 3 ) )"));
//		assertEquals("[1.0]", parse("( (+ version ZERO) )"));
		assertEquals("[1]", parse("( (+ 1) )"));
		assertEquals("[0]", parse("( (+) )"));

		assertEquals("[0]", parse("( (-) )"));
		assertEquals("[5]", parse("( (- 5) )"));
		assertEquals("[5]", parse("( (- 6 1) )"));

		assertEquals("[1, 2, 6]", parse("((*)(* 2)(* 1 2 3))"));
		assertEquals("[hello world]", parse("((concat \"hello \" \"world\" ))"));
		assertEquals("[hello world 6.28318]", parse("((concat \"hello \" \"world \" (* 3.14159 2)))"));

		// longints vs. doubles
		assertEquals("[3]", parse("( (+ 1 2) )"));
		assertEquals("[3.0]", parse("( (+ 1.0 2) )"));
		assertEquals("[3.0]", parse("( (+ 1 2.0) )"));

		// quotes
		assertEquals("[[1, 2, 3]]", parse("((quote (1 2 3)))"));
		assertEquals("[[1, 2, 3]]", parse("((' (1 2 3)))"));
		assertEquals("[jou]", parse("( (quote jou))"));
		assertEquals("[[+, 1, 2]]", parse("( (quote (+ 1 2)))"));

		// assertEquals("", parse("()"));
	}

	@Test
	public void testLambda() {

		// testa el retorn d'una expressió lambda
		assertEquals("[1, [x, =>, +, x, x], 1]", parse("( (*) (lambda (x => + x x)) (*) )"));

		// testa l'aplicació d'una funció lambda, amb diferent nombre
		// d'arguments:
		// de 0 a 4, i amb definicions composades.
		assertEquals("[6]", parse("( ((lambda (=> 6))) )"));
		assertEquals("[6]", parse("( ((lambda (=> (+ 4 2)))) )"));
		assertEquals("[6]", parse("( ((lambda (x => (+ x x))) 3) )"));
		assertEquals("[5]", parse("( ((lambda (x y => (+ x y))) 3 2) )"));
		assertEquals("[9]", parse("( ((lambda (x y z => (+ x y z))) 2 3 4) )"));
		assertEquals("[14]", parse("( ((lambda (x y z => (+ x (* y z)))) 2 3 4) )"));
	}

	// TODO falta testar let!
	@Test
	public void testDef() {

		assertEquals("[5, 10]", parse("( (def chorras 5) (* 2 chorras) )"));
		assertEquals("[100, 200]", parse(
			"( 						\n" +
			"	(def chorras 		\n" +
			"		(* 10 10)) 		\n" +
			"	(* 2 chorras) 		\n" +
			")"
		));

		assertEquals("[[x, =>, [*, x, x]], 4761]", parse(
			"( 									\n" +
			"	(def double 					\n" +
			"		(lambda (x => (* x x)))) 	\n" +
			"	(double 69) 					\n" +
			")"
		));

		assertEquals("[[x, =>, [*, x, 2]], [x, =>, [+, x, 1]], 5]", parse(
			"( 									\n" +
			"	(def double 					\n" +
			"		(lambda (x => (* x 2)))) 	\n" +
			"	(def ince 						\n" +
			"		(lambda (x => (+ x 1)))) 	\n" +
			"	 								\n" +
			"	(ince (double 2)) 				\n" +
			")"
		));

		assertEquals("[[x, =>, [*, x, x]], 25]", parse(
			"( 									\n" +
			"	(def sqr 						\n" +
			"		(lambda (x => (* x x)))) 	\n" +
			"	(sqr 5) 						\n" +
			")"
		));
	}

	@Test
	public void testHeadsAndTails() {

		assertEquals("[1]", parse(
			"( (car (quote (1 2 3))) )"
		));
		assertEquals("[[2, 3]]", parse(
			"( (cdr (quote (1 2 3))) )"
		));
		assertEquals("[[1, 2, 3]]", parse(
			"( (cons 1 (quote (2 3))) )"
		));
		assertEquals("[[1, 2, 3]]", parse(
			"( (cons (quote (1 2)) 3) )"
		));

		assertEquals("[[1, 2, 3]]", parse(
			"( (list 1 2 3) )"
		));

		assertEquals("[1]", parse(
			"( (car (list 1 2 3)) )"
		));
		assertEquals("[[2, 3]]", parse(
			"( (cdr (list 1 2 3)) )"
		));
		assertEquals("[[1, 2, 3]]", parse(
			"( (cons 1 (list 2 3)) )"
		));

		assertEquals("[3]", parse(
			"( (length (quote (1 2 3))) )"
		));
		assertEquals("[0]", parse(
			"( (length (quote ())) )"
		));




		assertEquals("[[]]", parse(
			"( (list) )"
		));
		assertEquals("[[]]", parse(
			"( (car (list)) )"
		));
		assertEquals("[1]", parse(
			"( (car (list 1)) )"
		));

		assertEquals("[[]]", parse(
			"( (cdr (list)) )"
		));
		assertEquals("[[]]", parse(
			"( (cdr (list 1)) )"
		));

		assertEquals("[1]", parse(
			"( (assert nil (cdr (list 1))) )"
		));
		assertEquals("[1]", parse(
			"( (assert nil (car (list))) )"
		));

	}

	@Test
	public void testEvalIncDec() {

		assertEquals("[[+, 1, 2, 3]]", parse("( (quote (+ 1 2 3))  )"));
		assertEquals("[6]", parse("( (eval (quote (+ 1 2 3)))  )"));
		assertEquals("[6]", parse("( (eval 6) )"));
		assertEquals("[6]", parse("( (eval (+ 2 4)) )"));

		assertEquals("[4]", parse("( (inc 3) )"));
		assertEquals("[4]", parse("( (dec 5)  )"));
	}

	@Test
	public void testIf() {

		assertEquals("[0]", parse("( (if 1 0 1)  )"));
		assertEquals("[1]", parse("( (if 0 0 1)  )"));

		assertEquals("[0]", parse("( (if (quote 1) (quote 0)(quote 1))  )"));
		assertEquals("[1]", parse("( (if (quote 0) (quote 0)(quote 1))  )"));

		assertEquals("[0]", parse("( (if (true) (quote 0)(quote 1))  )"));
		assertEquals("[1]", parse("( (if (false) (quote 0)(quote 1))  )"));


		assertEquals("[1]", parse("( (true) )"));
		try {
			// la definició lambda de true i false impedeixen que aquest tipus
			// booleà es pugui operar
			assertEquals("[1]", parse("( (+ true 1) )"));
			fail();
		} catch (final RuntimeException e) {}
	}


	@Test
	public void testFact() {

		assertEquals("[[N, =>, [if, N, [*, N, [fact, [-, N, 1]]], 1]], 120]", parse(
	            "(												\n" +
	            "	(def fact									\n"+
	            "  		(lambda (N =>							\n"+
	            "		    (if N								\n"+
	            "       		(* N (fact (- N 1)))			\n"+
	            "         		1								\n"+
	            "      		)									\n"+
	            "  		))										\n"+
	            "	)											\n"+
	            "	 											\n"+
	            "	(fact 5)									\n"+
	            ")												\n"
			));

		assertEquals("[[n, =>, [if, [=, n, 0], 1, [*, n, [fact, [-, n, 1]]]]], 120]", parse(
	            "(												\n"+
	            "	(def fact									\n"+
	            "  		(lambda (n =>							\n"+
	            "		    (if (= n 0)							\n"+
	            "         		1								\n"+
	            "       		(* n (fact (- n 1)))			\n"+
	            "      		)									\n"+
	            "  		))										\n"+
	            "	)											\n"+
	            "	 											\n"+
	            "	(fact 5)									\n"+
	            ")												\n"
			));

	}

//	@Test
//	public void test() {
//
//		assertEquals("[]", parse(
//            "(												\n" +
//            "	 											\n"+
//            "	 											\n"+
//            "	 											\n"+
//            "	 											\n"+
//            "	 											\n"+
//            ")												\n"
//		));
//
//	}

	@Test
	public void testAssert() {

		assertEquals("[1]", parse(
            "(												\n" +
            "	(assert 5 (+ 2 3))						\n"+
            "	 											\n"+
            ")												\n"
		));
		assertEquals("[1]", parse(
            "(												\n" +
            "	(assert (- 7 2) (+ 2 3))					\n"+
            "	 											\n"+
            ")												\n"
		));

		try {
    		assertEquals("[0]", parse(
                "(												\n" +
                "	(assert (- 5 2) (+ 2 3))					\n"+
                "	 											\n"+
                ")												\n"
    		));
			fail();
		} catch(final RuntimeException e) {}

	}


	@Test
	public void testMultiEval() {

		assertEquals("[1]", parse(
            "(												\n" +
            "	(assert nil (multi (disp 5) (disp (newline)) (disp(+ 2 3))))	\n"+
            "	 											\n"+
            ")												\n"
		));
		assertEquals("[[]]", parse(
            "(												\n" +
            "	(disp \"Gro says:\" (newline) (concat \"hello \" \"world!\"))	\n"+
            "	 											\n"+
            ")												\n"
		));

		assertEquals("[[], [3], [2, 3], [1, 2, 3], []]", parse(
            "(												\n" +
            "	 											\n"+
            "	 (def l nil)								\n"+
            "	 											\n"+
            "	 (let l (cons 3 l))							\n"+
            "	 (let l (cons 2 l))							\n"+
            "	 (let l (cons 1 l))							\n"+
            "	 (disp l)									\n"+
            "	 											\n"+
            "	 											\n"+
            "	 											\n"+
            ")												\n"
		));


		assertEquals("[[n, l, =>, [if, n, [_recur, [-, n, 1], [cons, n, l]], l]], [n, =>, [_recur, n, nil]], " +
				"[1, 2, 3, 4, 5], [1, 2, 3, 4, 5]]", parse(
            "(												\n" +
            "	 											\n"+
            "	(def _recur									\n"+
            "	 	(lambda (n l =>							\n"+
            "	 		(if n								\n"+
            "	 			(_recur (- n 1) (cons n l))		\n"+
            "	 			l								\n"+
            "	 		)									\n"+
            "		))	 									\n"+
            "	) 											\n"+
            "	 											\n"+
            "	(def sequence 								\n"+
            "	 	(lambda (								\n"+
            "	 		n => (_recur n nil)					\n"+
            "	 											\n"+
            "	 	))										\n"+
            "	) 											\n"+
            "	 											\n"+
            "	 											\n"+
            "	(_recur 5 nil)								\n"+
            "	(sequence 5) 								\n"+
            "	 											\n"+
            ")												\n"
		));



	}


	@Test
	public void testHighOrderFunctions() {

		assertEquals("[[FUN, LP, L, =>, [if, [length, L], [_mapcar, FUN, [cons, LP, [FUN, [car, L]]], [cdr, L]], LP]], [fun, l, =>, [_mapcar, fun, nil, l]], " +
		"[10, 20, 30, 40, 50], [10, 20, 30, 40, 50]]", parse(

        "(																	\n"+
        "	 																\n"+
        "	(def _mapcar 													\n"+
        "	 	(lambda (FUN LP L =>										\n"+
        "	 		(if (length L)											\n"+
        "	 			(_mapcar FUN (cons LP (FUN (car L))) (cdr L))		\n"+
        "	 			LP))))												\n"+
        "	 																\n"+
        "	(def mapcar 													\n"+
        "	 	(lambda (fun l =>											\n"+
        "	 		(_mapcar fun nil l))))									\n"+
        "	 																\n"+
        "	 																\n"+
        "	 																\n"+
        "	(_mapcar (lambda (x => (* x 10))) nil (list 1 2 3 4 5)) 		\n"+
        "	(mapcar (lambda (x => (* x 10))) (list 1 2 3 4 5)) 				\n"+
        "	 																\n"+
        ")																	\n"
	));
		assertEquals("[[fun, lp, l, =>, [if, [length, l], [if, [fun, [car, l]], [_filter-by, fun, lp, [cdr, l]], [_filter-by, fun, [cons, lp, [car, l]], [cdr, l]]], lp]], [fun, l, =>, [_filter-by, fun, nil, l]], " +
				"[1, 2, 4, 5], " +
				"[1, 2, 4, 5]]", parse(
            "(																	\n"+
            "	 																\n"+
            "	(def _filter-by 												\n"+
            "	 	(lambda (fun lp l =>										\n"+
            "	 		(if (length l)											\n"+
            "	 			(if (fun (car l))									\n"+
            "	 				(_filter-by fun lp (cdr l))						\n"+
            "	 				(_filter-by fun (cons lp (car l)) (cdr l)))		\n"+
            "	 			lp))))												\n"+
            "	 																\n"+
            "	 (def filter-by													\n"+
            "	 	(lambda (fun l => (_filter-by fun nil l))))					\n"+
            "	 																\n"+
            "	 																\n"+
            "	(_filter-by (lambda (x => (= x 3))) nil (list 1 2 3 4 5)) 		\n"+
            "	(filter-by (lambda (x => (= x 3))) (list 1 2 3 4 5)) 			\n"+
            "	 																\n"+
            ")																	\n"
		));
	}


	private String parse(final String program) {
		final Parser parser = new Parser(program);
		return parser.parseProgram().toString();
	}


}

// TODO testar errors i línies en missatges

