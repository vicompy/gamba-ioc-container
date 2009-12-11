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

	public void let(final String name, final T value) throws ScopedSymbolTableException {
//		System.out.println("let:"+name+":="+value.toString());
		for (int i = this.currentLevel; i >= 0; i--) {
			if (this.symbolTable.get(i).keySet().contains(name)) {
				this.symbolTable.get(i).put(name, value);
				return;
			}
		}
		throw new ScopedSymbolTableException("symbol not found: " + name);
	}

	public int getCurrentLevel() {
		return currentLevel;
	}


}
