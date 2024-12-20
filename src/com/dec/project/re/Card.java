package com.dec.project.re;

public class Card {
	// 필드
	private int number;
	private String shape;
	
	// getter/setter
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
	
	// 생성자
	public Card() {}
	public Card(int number, String shape) {
		this.number = number;
		this.shape = shape;
	}
	@Override
	public String toString() {
		if(number == 11) {
			return "Card [shape=" + shape + ", number=Jack]";
		}else if(number == 12) {
			return "Card [shape=" + shape + ", number=Queen]";
		}else if(number == 13) {
			return "Card [shape=" + shape + ", number=King]";
		}else if(number == 1) {
			return "Card [shape=" + shape + ", number=Ace]";
		}else {
			return "Card [shape=" + shape + ", number=" + number + "]";			
		}
	}
}
