package com.dec.project.re;

import java.util.*;

public class Player {
	// 필드
	private String name; // 이름
	private int money; // 자금
	private String bettingChoice; // 콜/레이즈/다이중 무엇을 선택했는지 저장하는 문자열
	private boolean user; // 유저인지 컴퓨터인지 판별해주는 변수
	private int deckScore; // 최종덱의 점수를 저장해주는 변수
	private List<Card> cardList; // 전체 카드 리스트
	private List<Card> handCardList; // 손패에 있는 카드리스트
	private List<Card> tableCardList; // 테이블 위에 있는 카드리스트
	
	// getter/setter
	public int getDeckScore() {
		return deckScore;
	}
	public void setDeckScore(int deckScore) {
		this.deckScore = deckScore;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getBettingChoice() {
		return bettingChoice;
	}
	public void setBettingChoice(String bettingChoice) {
		this.bettingChoice = bettingChoice;
	}
	public boolean isUser() {
		return user;
	}
	public void setUser(boolean user) {
		this.user = user;
	}
	// 리스트는 set을 쓰지 않고 add를 써 카드를 받는 행위를 표현
	public List<Card> getCardList() {
		return cardList;
	}
	public void addCardList(Card card) {
		cardList.add(card);
	}
	public List<Card> getHandCardList() {
		return handCardList;
	}
	public void addHandCardList(Card card) {
		handCardList.add(card);
	}
	public List<Card> getTableCardList() {
		return tableCardList;
	}
	public void addTableCardList(Card card) {
		tableCardList.add(card);
	}
	
	// 생성자
	public Player() {
		deckScore = 0;
		bettingChoice = "콜";
		cardList = new ArrayList<Card>();
		handCardList = new ArrayList<Card>();
		tableCardList = new ArrayList<Card>();
	}
	public Player(String name, int money, boolean user) {
		this.name = name;
		this.money = money;
		this.user = user;
		deckScore = 0;
		bettingChoice = "콜";
		cardList = new ArrayList<Card>();
		handCardList = new ArrayList<Card>();
		tableCardList = new ArrayList<Card>();
	}
	
}
