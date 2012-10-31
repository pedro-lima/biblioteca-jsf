package br.com.biblioteca.persistence;

public class QueryParam {
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
