package com.dec.project.re;

import java.util.*;

public interface ViewInterface {
	
	/**
	 * 유저의 이름과 초기금액을 결정해서
	 * 플레이어 객체로 만들어 반환
	 * @return User
	 */
	public Player setMe();
	
	/**
	 * 메뉴화면을 출력하고 
	 * 유저가 선택한 번호를 리턴
	 * @return menuChoice
	 */
	public int printMenu();
	
	/**
	 * 플레이어가 가진 금액을 출력
	 * @param player
	 * @return
	 */
	public void printMoney(Player player);
	
	/**
	 * 플레이어의 전적 출력
	 * @param win
	 * @param lose
	 */
	public void printScore(int win, int lose);
	/**
	 * 플레이어가 베팅액을 얼마 냈는지 출력
	 * @param player
	 * @param bettingMoney
	 */
	public void printBet(Player player, int bettingMoney);
	
	/**
	 * 몇 명과 함께 플레이할지 결정해서 
	 * 반환
	 * @return playerNumber
	 */
	public int setPlayerNumber();
	
	/**
	 * 전체 플레이어들의 정보를 출력
	 * @param playerList
	 */
	public void printPlayerInfor(List<Player> playerList);
	
	/**
	 * 유저가 가진 카드들을 출력
	 * @param user
	 */
	public void printUserCard(Player user);
	
	/**
	 * 몇번째 인덱스의 카드를 테이블위에 올려둘건지
	 * 선택하고 그 인덱스값을 반환
	 * @param user
	 * @return selectCardIndex
	 */
	public int selectUserShowCard(Player user);
	
	/**
	 * 테이블 출력
	 * @param playerList
	 */
	public void printTable(List<Player> playerList);
	
	/**
	 * 베팅액을 결정하는 플레이어가 유저일 때,
	 * 그 금액을 결정해서 반환
	 * @param user
	 * @param prize
	 * @return bettingMoney
	 */
	public int printStartBetting(Player user, int prize);
	
	/**
	 * 유저가 콜/레이즈/다이를 선택하여 
	 * 문자열을 반환
	 * @param user
	 * @return 콜/레이즈/다이
	 */
	public String choiceBetting(Player user);
	
	/**
	 * 유저가 레이즈를 선택했을 시,
	 * 레이즈할 금액을 결정해서 반환
	 * 유저가 가진 금액이 베팅액보다 부족할시 다시 입력
	 * @param user
	 * @return raiseMoney
	 */
	public int choiceRaiseMoney(Player user, int bettingMoney);
	
	/**
	 * 플레이어들의 선택을 출력(콜/레이즈/다이)
	 * @param playerList
	 */
	public void printChoiceBet(List<Player> playerList);
	
	/**
	 * 메세지 출력
	 * @param message
	 */
	public void displayMessage(String message);
}
