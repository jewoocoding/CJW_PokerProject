package com.dec.project.mini;

import java.util.*;

import javax.crypto.Mac;

public class Player {
	// 필드
	private String name;
	private int money;
	private String choice;
	private boolean user;
	private List<Card> myCard;
	private List<Card> showCard;
	
	// 메소드
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
	// 카드 주기
	public void getCard(Card card) {
		myCard.add(card);
	}
	// 숨긴패의 개수
	public int getMyCardCount() {
		return myCard.size();
	}
	// 숨긴패 반환
	public List<Card> getMyCard() {
		return myCard;
	}
	// 테이블위에 올려둘 카드 선택
	public void setShowCard(int index) {
		showCard.add(myCard.get(index)); // 여기서 오류
		myCard.remove(index);
	}
	// 테이블에 있는 카드리스트 반환
	public List<Card> getShowCard() {
		return showCard;
	}
	// 생성자
	public Player() {}
	public Player(String name, int money, boolean user) {
		this.name = name;
		this.money = money;
		this.setUser(user);
		setChoice("콜");
		myCard = new ArrayList<Card>();
		showCard = new ArrayList<Card>();
	}
	// getter/setter
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public boolean isUser() {
		return user;
	}
	public void setUser(boolean user) {
		this.user = user;
	}
}
