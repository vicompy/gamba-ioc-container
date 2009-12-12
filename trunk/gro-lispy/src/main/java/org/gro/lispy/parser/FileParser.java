package org.gro.lispy.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileParser {

	public static List<Object> parse(final String fileName) throws IOException {
		final BufferedReader in = new BufferedReader(new FileReader(fileName));
		String line;
		final StringBuffer strb = new StringBuffer();

		while ((line = in.readLine()) != null) {
			strb.append(line);
			strb.append('\n');
		}
		in.close();

		return new Parser(strb.toString()).parseProgram();
	}

}




/*
(

	(def nil (quote ()))
	(def true  (quote 1))
	(def false (quote 0))
	(def inc (lambda (x => (+ x 1))))
	(def dec (lambda (x => (- x 1))))


	(def displn
		(lambda (x =>
			(disp x (newline)))))


	(def fact
  		(lambda (n =>
		    (if n
	       		(* n (fact (- n 1)))
         		1))))

	(def neg
		(lambda (x =>
			(if x
				false
				true))))


	;
	;
	; High-order functions
	;
	;

	;;
	;; mapcar
	;;
	;; example:
	;; 	(_mapcar (lambda (x => (* x 10))) nil (list 1 2 3 4 5))
	;; 	(mapcar (lambda (x => (* x 10))) (list 1 2 3 4 5))

	(def _mapcar
	 	(lambda (fun lp l =>
	 		(if (length l)
	 			(_mapcar fun (cons lp (fun (car l))) (cdr l))
	 			lp))))

	(def mapcar
	 	(lambda (fun l =>
	 		(_mapcar fun nil l))))


	;;
	;; filter-by
	;;
	;; example:
	;; 	(_filter-by (lambda (x => (= x 3))) nil (list 1 2 3 4 5))
	;; 	(filter-by (lambda (x => (= x 3))) (list 1 2 3 4 5))

	(def _filter-by
	 	(lambda (fun lp l =>
	 		(if (length l)
	 			(if (fun (car l))
	 				(_filter-by fun lp (cdr l))
	 				(_filter-by fun (cons lp (car l)) (cdr l)))
	 			lp))))

	(def filter-by
		(lambda (fun l => (_filter-by fun nil l))))



)


*/
