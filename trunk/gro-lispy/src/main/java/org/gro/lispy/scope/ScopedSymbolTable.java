package org.gro.lispy.scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopedSymbolTable<T> {

	private final List<Map<String, T>> symbolTable;
	private int currentLevel;

	public ScopedSymbolTable() {
		this.symbolTable = new ArrayList<Map<String, T>>();
		this.currentLevel = -1;
	}

	public void createLevel() {
		this.currentLevel++;
		// lazy scope level allocation
		if (this.symbolTable.size() <= this.currentLevel) {
			this.symbolTable.add(new HashMap<String, T>());
		}
	}

	public void removeLevel() throws ScopedSymbolTableException {
		this.symbolTable.get(this.currentLevel).clear();
		this.currentLevel--;
	}

	public void define(final String name, final T value) throws ScopedSymbolTableException {
		if (this.symbolTable.get(this.currentLevel).keySet().contains(name)) {
			throw new ScopedSymbolTableException("symbol already defined");
		}
		this.symbolTable.get(this.currentLevel).put(name, value);
	}

	public T obtain(final String name) throws ScopedSymbolTableException {
		for (int i = this.currentLevel; i >= 0; i--) {
			if (this.symbolTable.get(i).keySet().contains(name)) {
				return this.symbolTable.get(i).get(name);
			}
		}
		throw new ScopedSymbolTableException("symbol not found: " + name);
	}

	/*
	(def add
	   (lambda x
	      (lambda y
	         (+ x y))))

	((add 2) 3)

	(def add
	   (lambda (x y)
	      (+ x y)))

	(add 2 3)

	;;; number
	(+ 2 3)
	;;; literal
	(concat "hello " "world")
	;;; boolean
	(< 2 3)
	;;; function
	(lambda a (+ a a))
	;;; object
	(new java.awt.Color)



	(define count (* 2 3))
	Function[define](Literal["count"], Function[*](Number[2], Number[3]))
	Function[define](Literal["count"], Number[6])

	<program> ::= { <expression> }
	<expression> ::= <func> | IDENT
	<func> ::= (IDENT {<expression>})


	(lambda x (+ x x))

	lambda -> x -> +
	               |
	               +--> + -> x -> x

	;;; (let color-list (news java.awt.Color 10))
	(mapcar
		(lambda x (set x.green 64))
		(news java.awt.Color 10))

	(mapcar
		(lambda x (+ x x))
		(list 1 2 3 4 5))

	(length
		(list 1 (list 2 3) 4 5))

	(car '(1 2 3))  ===  (car (quote (1 2 3))) ha de funcionar!!
		--> quote evalua com a dada
		--> altrament com a expressi� sint�ctica
		==> l'operador quote retorna una el tipus de dades amb qu� l'int�rpret representa les dades:
		    (quote (1 2 3)) --> [1,2,3]
		    (quote jou)   --> jou


	(def double
		(lambda x (+ x x)))

	*/
	public void let(final String name, final T value) throws ScopedSymbolTableException {
		for (int i = this.currentLevel; i >= 0; i--) {
			if (this.symbolTable.get(i).keySet().contains(name)) {
				this.symbolTable.get(i).put(name, value);
				return;
			}
		}
		throw new ScopedSymbolTableException("symbol not found: " + name);
	}

}
