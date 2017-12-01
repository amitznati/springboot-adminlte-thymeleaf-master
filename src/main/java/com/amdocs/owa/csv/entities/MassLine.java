package com.amdocs.owa.csv.entities;

import java.util.List;

public class MassLine {
	
	private List<Attribute> attributes;
	
	private int lineNumber;

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

}
