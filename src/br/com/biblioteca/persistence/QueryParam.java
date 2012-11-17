package br.com.biblioteca.persistence;

import java.io.Serializable;

public class QueryParam implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private Object value;
	
	public QueryParam(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	protected String getName() {
		return name;
	}

	protected Object getValue() {
		return value;
	}	
	
}
