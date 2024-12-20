package com.dec.project.mini;

import java.util.*;

public class PokerManage {
	// 필드
	private List<Card> cardDeck;
	private List<Player> playerList;
	private int win;
	private int lose;
	private int bettingMoney;
	
	// 메소드
	// getter/setter
	public int getBettingMoney() {
		return bettingMoney;
	}

	public void setBettingMoney(int bettingMoney) {
		this.bettingMoney = bettingMoney;
	}
	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}
	// 생성자
	public PokerManage() {
		cardDeck = new ArrayList<Card>(); // 트럼프 카드덱 생성
		for(int i=1;i<=13;i++) {
			Card card = new Card(i, "Spade");
			cardDeck.add(card);
		}
		for(int i=1;i<=13;i++) {
			Card card = new Card(i, "Diamond");
			cardDeck.add(card);
		}
		for(int i=1;i<=13;i++) {
			Card card = new Card(i, "Clover");
			cardDeck.add(card);
		}
		for(int i=1;i<=13;i++) {
			Card card = new Card(i, "Heart");
			cardDeck.add(card);
		}
		playerList = new ArrayList<Player>(); // 게임을 할 플레이어 리스트 생성
		// 전적 초기화
		win = 0;
		lose = 0;
		// 배팅액 변수 초기화
		bettingMoney = 0;
	}
	// 플레이어를 playerList에 추가
	public void pushPlayer(Player player) {
		playerList.add(player);
	}
	// 인덱스에 있는 플레이어 리턴
	public Player getPlayer(int index) {
		return playerList.get(index);
	}
	
	// 플레이어 인원 설정
	public void pushPlayer(int player) {
		for(int i=0; i<player-1;i++) {
			// 같이 플레이할 컴퓨터 플레이어 생성
			int num = i+1;
			playerList.add(new Player("com"+num,(int)(Math.random()*400000+100000),false));
		}
	}
	// 플레이어 리스트 리턴
	public List<Player> getPlayerList(){
		return playerList;
	}
	

	public void giveCard() {
		// cardDeck에 있는 카드를 player의 카드리스트에 넣고
		// 그 카드를 cardDeck에서 제거
		for(int i=0;i<playerList.size();i++) {
			// 랜덤으로 카드를 뽑는다.
			int random = (int)(Math.random()*cardDeck.size());
			playerList.get(i).getCard(cardDeck.get(random));
			cardDeck.remove(random);
		}
	}
	public void giveShowCard() {
		// cardDeck 에서 카드 뽑아서 테이블에 전시
		for(int i=0;i<playerList.size();i++) {
			// 랜덤으로 카드를 뽑는다.
			int random = (int)(Math.random()*cardDeck.size());
			playerList.get(i).getCard(cardDeck.get(random));
			playerList.get(i).setShowCard(playerList.get(i).getMyCard().size()-1);
			cardDeck.remove(random);
		}
	}
	// 테이블 위에 공개할 카드 선택
	public void selectShowCard() {
		for(int i = 1;i<playerList.size();i++) {
			int rand = (int)(Math.random()*playerList.get(i).getMyCardCount());
			playerList.get(i).setShowCard(rand);
		}
	}
	// 나의 공개할 카드 선택
	public void selectShowCard(int index) {
		Player me = playerList.get(0);
		me.setShowCard(index);
	}
	// 입장료 지급
	public int enter(int enter) {
		for(int i=0;i<playerList.size();i++) {
			bettingMoney += enter;
			playerList.get(i).setMoney(playerList.get(i).getMoney()-enter);
		}
		return enter;
	}
	// 베팅을 시작할 인원 선택
	public Player choiceStartBettingPlayer() {
		List<Integer> maxNumber = new ArrayList<Integer>();
		// 플레이어 선택해서 테이블에 있는 카드중 가장 큰 수 고르기
		for(int i=0; i<playerList.size();i++) {
			// 테이블에 있는 카드중 0번째 인덱스를 일단 넣음
			int maxNum = playerList.get(i).getShowCard().get(0).getNumber();
			for(int j=0; j< playerList.get(i).getShowCard().size();j++) {
				// 하나씩 비교하여 더 큰수를 maxNum에 넣음
				maxNum = playerList.get(i).getShowCard().get(j).getNumber() > maxNum ? 
						playerList.get(i).getShowCard().get(j).getNumber() : maxNum;
			}
			// 가장 큰수를 리스트에 넣음
			maxNumber.add(maxNum);
		}
		
		// 테이블에 있는 카드중 가장 큰 수를 가지고 있는 플레이어 고르기
		int bettingPlayerNum = 0;
		for(int i=0; i< maxNumber.size()-1;i++) {
			bettingPlayerNum = maxNumber.get(i) > maxNumber.get(i+1) ? i : i+1;
		}
		return playerList.get(bettingPlayerNum);
	}
	// 베팅
	public int betting(Player player, int bet) {
		bet = player.getMoney();
		bettingMoney += bet;
		player.setMoney(player.getMoney()-bet);
		return bet;
	}
	// 컴퓨터의 베팅액 설정
	public int comSetBettingMoney(Player com) {
		int money = bettingMoney/2 > com.getMoney() 
				? (int)(Math.random()*(com.getMoney()+1)) 
						: (int)(Math.random()*(bettingMoney/2+1));
		return money;
	}
	// 컴퓨터의 콜/레이즈/다이 여부 설정
	public String comChoiceBet(Player com) {
		List<Card> comCardList = new ArrayList<Card>();
		for(int i=0;i< com.getMyCard().size();i++) {
			comCardList.add(com.getMyCard().get(i));
		}
		for(int i=0;i<com.getShowCard().size();i++) {
			comCardList.add(com.getShowCard().get(i));
		}
		int max = 0;
		for(int i=0;i<comCardList.size();i++) {
			if(comCardList.get(i).getNumber() > max) {
				max = comCardList.get(i).getNumber();
			}
		}
		int rand = 0;
		switch(max) {
		case 2, 3:
			rand = (int)(Math.random()*2+1);
			break;
		case 13,1:
			rand = (int)(Math.random()*8+1);
			break;
		default:
			rand = (int)(Math.random()*10+1);
			break;
		}
		switch(rand) {
		case 1, 2:
			return "다이";
		case 10:
			return "레이즈";
		default:
			return "콜";
		}
	}
	// 컴퓨터의 레이즈 금액 설정
	public int comChoiceBettingMoney(Player com,int bet) {
		int result = 0;
		if(com.getMoney()> bet/2) {
			result = (int)(Math.random()*(bet/2)+1);
		}else {
			result = (int)(Math.random()*(com.getMoney())+1);
		}
		return result;
	}
	// 콜했을때 동작
	public void choiceCall(Player player, int bet) {
		bettingMoney += bet;
		player.setMoney(player.getMoney()-bet);
	}
	
	// 다이했을때 동작
	public void choiceDie(Player player) {
		for(int i=0;i<playerList.size();i++) {
			if(playerList.get(i).getName().equals(player.getName())) {
				playerList.remove(i);
			}
		}
	}
	
	// 플레이어 중 등수 설정
 	public int evaluateDeck(Player player) {
		
		boolean royalStraightFlush = false;
		boolean backStraightFlush = false;
		boolean straightFlush = false;
		boolean fourCard = false;
		boolean fullHouse = false;
		boolean flush = false;
		boolean mountain = false;
		boolean backStraight = false;
		boolean straight = false;
		boolean triple = false;
		boolean twoFair = false;
		boolean oneFair = false;
		
		int score = 0; // 리턴할 점수
		List<Integer> setList = new ArrayList<Integer>(); // 판별할 카드 5개를 골라서 저장할 리스트
		
		// 플레이어의 카드덱을 받아옴
		List<Card> deck = player.getMyCard();
		for(int i=0;i<player.getShowCard().size();i++) {
			deck.add(player.getShowCard().get(i));
		}
		// 숫자리스트와 모양리스트를 따로 분리
		List<Integer> numberList = new ArrayList<Integer>();
		List<String> shapeList = new ArrayList<String>();
		for(Card card : deck) {
			numberList.add(card.getNumber());
			shapeList.add(card.getShape());
		}
		
		// 숫자 정렬(선택정렬)
		for(int i=0;i<numberList.size();i++) {
			for(int j = i;j<numberList.size();j++) {
				if(numberList.get(i) > numberList.get(j)) {
					int temp = numberList.get(j);
					numberList.set(i, numberList.get(j));
					numberList.set(j, temp);
				}
			}
		}
		// shapeList를 set에 넣어 중복제거
		Set<String> shapeSet = new HashSet<String>();
		for(String shape : shapeList) {
			shapeSet.add(shape);
		}
		int notSame = shapeSet.size();
		
		// 스트레이트인지 판별
		notStraight:
		for(int i=0;i<3;i++) {
			// 0, 1, 2 인덱스부터 시작해 그뒤로 5개가 스트레이트가 되는지 확인
			int num = numberList.get(i);
			for(int j=0;j<5;j++) {
				// 원소 하나하나씩 비교해 스트레이트가 되는지 확인
				if(numberList.get(j)==num) {
					straight = true;
				}else {
					// 다른 원소가 있으면 false를 반환하고 break;
					// 마지막 반복이면 for문 전체 탈출, 0,1이면 j의 for문만 탈출
					straight = false;
					switch(i) {
					case 2: break notStraight;
					default: break;
					}
				}
				// 스트레이트를 확인하기위해 하나하나씩 더해서 비교
				num++;
			}
		}
		// 위의 for문으로 처리하지 못하는 스트레이트의 경우
		// 0번째 인덱스에 1(Ace) 마지막 인덱스에 13(King)이 되는 경우
		int [][] straightList = {{1,2,3,4,0,0,13}
		, {1,2,3,0,0,12,13}
		, {1,2,0,0,11,12,13}
		, {1,0,0,10,11,12,13}};
		notStraight2:
			// 가장작은 원소가 1이고, 가장 큰원소가 13일때 
		if(numberList.get(0) == 1 && numberList.get(numberList.size()-1) == 13) {
			for(int i=0;i<straightList.length;i++) {
				// 2차원배열의 행 반복
				for(int j=0;j<straightList[i].length;j++) {
					// 2차원배열의 열 반복
					if(straightList[i][j] != 0) { // 0이 아닌 부분만 비교
						if(straightList[i][j] == numberList.get(j)) {
							straight = true;
						}else {
							straight = false;
							switch(i) {
							case 3: break notStraight2; // 마지막 반복일때 for문 전체 탈출
							default: break;
							}
						}						
					}
				}
			}
		}
		
		// 스트레이트 플러시인지 판별
		
		
		// 플러시인지 판별
		int heart = 0, diamond = 0, clover = 0, spade = 0;
		for(int i=0; i<shapeList.size();i++) {
			switch(shapeList.get(i)) {
			case "Spade":
				spade++;
				break;
			case "Diamond":
				diamond++;
				break;
			case "Clover":
				clover++;
				break;
			case "Heart":
				heart++;
				break;
			}
		}
		flush = heart > 5 || diamond > 5 || clover > 5 || spade > 5
				? true : false;
		
		// 마운틴
		int[] mountainFlushArrs = {1,0,0,10,11,12,13};
		for(int i=0;i<7;i++) {
			if(mountainFlushArrs[i] != 0) {
				if(mountainFlushArrs[i] != 0) {
					if(numberList.get(i) == mountainFlushArrs[i]) {
						mountain = true;
					}else {
						mountain = false;
						break;
					}									
				}
			}
		}
		// 로얄스트레이트 플러시
		royalStraightFlush = mountain && flush ? true : false;
		
		// 백 스트레이트
		int[] backStraightFlushArrs = {1,2,3,4,5,0,0};
		for(int i=0;i<7;i++) {
			if(backStraightFlushArrs[i] != 0) {
				if(numberList.get(i) == backStraightFlushArrs[i]) {
					backStraight = true;
				}else {
					backStraight = false;
					break;
				}				
			}
		}
		// 백 스트레이트 플러쉬
		backStraightFlush = backStraight && flush ? true : false;
		
		
		// 스트레이트플러시
		// 포카드
		// 풀하우스
		// 플러시
		// 마운틴
		// 백스트레이트
		// 스트레이트
		// 트리플
		// 투페어
		// 원페어
		
		return score;
	}
	
}
