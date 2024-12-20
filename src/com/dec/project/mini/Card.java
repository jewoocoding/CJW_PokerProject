package com.dec.project.mini;

public class Card {
	// 필드
	private int number;
	private String shape;
		
	//메소드 
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	
	public Card() {}
	public Card(int number, String shape) {
		this.number = number;
		this.shape = shape;
	}
}
