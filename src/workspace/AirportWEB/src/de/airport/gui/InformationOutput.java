package de.airport.gui;

public class InformationOutput {
	private String key;
	private String value;
	
	public InformationOutput()
	{
		key = "hallo";
		value="welt";
	}
	public InformationOutput(String key, String value) {
		// TODO Auto-generated constructor stub
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		
		System.err.println("key?????");
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
