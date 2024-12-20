package com.dec.project.re;

public interface ManageInterface {
	
	/**
	 * 플레이어를 받아와서 
	 * 플레이어리스트에 추가
	 * @param player
	 */
	public void addPlayer(Player player);
	
	/**
	 * 컴퓨터 몇명과 함께할지를 받아와서
	 * 컴퓨터플레이어 생성해서 리스트에 넣음
	 * @param playerNum
	 */
	public void addComPlayer(int playerNum);
	
	/**
	 * 리스트에 있는 플레이어들 중 유저를 선택해
	 * 리턴
	 * @return 유저
	 */
	public Player getUser();
	
	/**
	 * 플레이어들에게 카드를 나눠줌
	 */
	public void giveCard();
	
	/**
	 * 플레이어들의 테이블에 카드를 나눠줌
	 * (테이블 위에 올려둠)
	 */
	public void giveShowCard();
	
	/**
	 * 처음 컴퓨터가 카드를 받았을때
	 * 어떤 것을 테이블위에 올려둘지 정해서
	 * 올려둠
	 */
	public void selectComShowCard();
	
	/**
	 * 유저가 선택한 인덱스 번호를 얻어와서 
	 * 그 선택한 카드를 손패에서 테이블로 옮김
	 * @param cardListIndex
	 */
	public void selectUserShowCard(int cardListIndex);
	
	/**
	 * 받아온 플레이어가 정해진 베팅액을 내서 총 상금에 
	 * 지출하게 함
	 * @param player
	 * @param bettingMoney
	 */
	public void betting(Player player, int bettingMoney);
	
	/**
	 * 테이블위에 있는 카드들을 비교해서
	 * 가장 높은 번호의 카드를 가진 플레이어가
	 * 이번 턴의 베팅액을 결정함 -> 그래서 
	 * 그 플레이어를 결정해서 반환
	 * @return Player
	 */
	public Player choiceStartBettingPlayer();
	
	/**
	 * 컴퓨터가 첫베팅금액을 정하는 플레이어일때,
	 * 그 금액을 결정해서 반환
	 * @param com
	 * @return 첫 베팅금액
	 */
	public int comSetBettingMoney(Player com);
	
	/**
	 * 컴퓨터가 콜/레이즈/다이 중에 하나를 선택해서 
	 * 결정을 반환 해줌
	 * @param com
	 * @return 콜/레이즈/다이
	 */
	public String comChoiceBet(Player com);
	
	/**
	 * 컴퓨터가 레이즈를 선택했을 시,
	 * 그 금액을 결정해서 반환
	 * @param com
	 * @return raiseMoney
	 */
	public int comChoiceRaiseMoney(Player com);
}
