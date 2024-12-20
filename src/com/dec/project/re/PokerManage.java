package com.dec.project.re;

import java.util.*;

public class PokerManage implements ManageInterface {
	// 필드
	private List<Card> cardDeck; // 전체 트럼프카드 덱
	private List<Player> playerList; // 플레이어리스트
	private Player user;
	private int win; // 유저의 승리 횟수
	private int lose; // 유저의 패배 횟수
	private int bettingMoney; // 한 턴의 베팅금액을 저장하는 변수
	private int prize; // 총 상금(모든 턴의 베팅액을 더한 금액)
	

	// getter/setter
	public List<Card> getCardDeck() {
		return cardDeck;
	}
	
	public List<Player> getPlayerList() {
		return playerList;
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

	public int getBettingMoney() {
		return bettingMoney;
	}

	public void setBettingMoney(int bettingMoney) {
		this.bettingMoney = bettingMoney;
	}

	public int getPrize() {
		return prize;
	}

	public void setPrize(int prize) {
		this.prize = prize;
	}

	
	// 생성자
	public PokerManage() {
		// 필드 초기화
		win = 0;
		lose = 0;
		bettingMoney = 0;
		prize = 0;
		user = null;
		playerList = new ArrayList<Player>();
		cardDeck = new ArrayList<Card>();
		// 트럼프 카드덱 생성하여 저장
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
	}
	
	@Override
	public void addPlayer(Player player) {
		// 유저를 받아와서 리스트에 저장
		user = player;
		playerList.add(player);
	}

	@Override
	public void addComPlayer(int playerNum) {
		// 컴퓨터플레이어를 생성해 플레이어 리스트에 저장
		for(int i=0; i<playerNum;i++) {
			Player com = new Player();
			com.setName("com"+(i+1));
			// 컴퓨터의 자금은 랜덤으로 생성해 부여해줌
			com.setMoney((int)(Math.random()*400000+100000));
			// 만든 컴퓨터플레이어를 리스트에 넣음
			playerList.add(com);
		}
	}

	@Override
	public Player getUser() {
		// 플레이어리스트에 있는 플레이어들 중 유저를 반환
		Player user = null;
		for(Player player : playerList) {
			if(player.isUser()) {
				user = player;
			}
		}
		return user;
	}

	@Override
	public void giveCard() {
		// 플레이어들에게 카드를 나눠줌
		for(Player player : playerList) {
			// 전체 카드에서 랜덤으로 뽑아서 나눠줌
			int give = (int)(Math.random()*cardDeck.size());
			Card card = cardDeck.get(give);
			cardDeck.remove(give);
			player.addCardList(card);
		}
	}

	@Override
	public void giveShowCard() {
		// 카드를 뽑아서 각 플레이어 테이블에 올려둠
		for(Player player : playerList) {
			// 랜덤으로 뽑아서 올려둠
			int give = (int)(Math.random()*cardDeck.size());
			Card card = cardDeck.get(give);
			cardDeck.remove(give);
			player.addTableCardList(card);
			player.addCardList(card);
		}
		
	}

	@Override
	public void selectComShowCard() {
		// 첫 턴에 카드를 받고 무엇을 테이블에 올려둘 것인지 컴퓨터가 랜덤하게 선택하게 함
		for(Player player : playerList) {
			if(!player.isUser()) { // 컴퓨터만 선택함
				// 랜덤하게 선택
				int choice = (int)(Math.random()*player.getCardList().size());
				for(int i=0; i< player.getCardList().size();i++) {
					Card card = player.getCardList().get(i);
					if(choice == i) {
						player.addTableCardList(card);
					}else {
						player.addHandCardList(card);						
					}
				}
			}
		}
	}

	@Override
	public void selectUserShowCard(int cardListIndex) {
		// 유저가 테이블위에 올려둘 카드의 인덱스를 받아와
		// 그 카드를 테이블위에 올려둠
		Player user = null;
		// 리스트중에 유저플레이어를 선택
		for(Player player : playerList) {
			if(player.isUser()) {
				user = player;
				break;
			}
		}
		for(int i=0; i< user.getCardList().size();i++) {
			// 카드를 테이블에 올려둠
			Card card = user.getCardList().get(i);
			if(i == cardListIndex) {
				user.addTableCardList(card);
			}else {
				user.addHandCardList(card);
			}
		}
	}

	@Override
	public void betting(Player player, int bettingMoney) {
		// 플레이어가 베팅액을 지출함
		if(player.getMoney() > bettingMoney) {
			this.prize += bettingMoney;
			player.setMoney(player.getMoney()-bettingMoney);			
		}else {
			// 플레이어의 자금이 베팅액보다 적을 시 올인
			this.prize += player.getMoney();
			player.setMoney(0);
		}
	}

	@Override
	public Player choiceStartBettingPlayer() {
		int max = 0;
		Player startBettingPlayer = null;
		for(Player player : playerList) {
			for(Card card : player.getTableCardList()) {
				if(card.getNumber() > max) {
					max = card.getNumber();
					startBettingPlayer = player;	
				}
			}
		}
		return startBettingPlayer;
	}

	@Override
	public int comSetBettingMoney(Player com) {
		int money = prize/2 > com.getMoney() 
				? (int)(Math.random()*(com.getMoney()+1)) 
						: (int)(Math.random()*(prize/2));
		return money;
	}

	@Override
	public String comChoiceBet(Player com) {
		if(com.getMoney()-bettingMoney > bettingMoney) {
			int max = 0;
			for(Card card : com.getCardList()) {
				if(card.getNumber() > max)
					max = card.getNumber();
			}
			int rand = 0;
			switch(max) {
			case 2,3:
				rand = (int)(Math.random()*5+1);
				break;
			case 13,1:
				rand = (int)(Math.random()*6+5);
				break;
			default:
				rand = (int)(Math.random()*10+1);
			}
			switch(rand) {
			case 1:
				com.setBettingChoice("다이");
				break;
			case 10:
				com.setBettingChoice("레이즈");
				break;
			default:
				com.setBettingChoice("콜");
			}
		}else {
			com.setBettingChoice("콜");
		}
		
		return com.getBettingChoice();
	}

	@Override
	public int comChoiceRaiseMoney(Player com) {
		int canRaise = com.getMoney() - bettingMoney;
		int raise = bettingMoney > canRaise ? (int)(Math.random()*canRaise+1)
				: (int)(Math.random()*(bettingMoney/2)+1);
		
		return raise;
	}
	
	public void deletePlayer() {
		for(int i=0; i< playerList.size();i++) {
			if(playerList.get(i).getBettingChoice().equals("다이")) {
				playerList.remove(i);
			}
		}
	}
	
	public void evaluateDeck(Player player) {
		
		boolean royalStraightFlush = false; // 12점
		boolean backStraightFlush = false; // 11점
		boolean straightFlush = false; // 10점
		boolean fourCard = false; // 9점
		boolean fullHouse = false; // 8점
		boolean flush = false; // 7점
		boolean mountain = false; // 6점
		boolean backStraight = false; // 5점
		boolean straight = false; // 4점
		boolean triple = false; // 3점
		boolean twoFair = false; // 2점
		boolean oneFair = false; // 1점
		
		List<Card> deck = player.getCardList();
		List<Integer> numberList = new ArrayList<Integer>();
		Set<String> shapeSet = new HashSet<String>();
		for(Card card : deck) {
			numberList.add(card.getNumber());
			shapeSet.add(card.getShape());
		}
		// 선택 정렬
		for(int i=0; i< numberList.size();i++) {
			for(int j=i;j<numberList.size();j++) {
				if(numberList.get(i) > numberList.get(j)) {
					int temp = numberList.get(i);
					numberList.set(i, numberList.get(j));
					numberList.set(j, temp);
				}
			}
		}
		// 플러시인지 판별
		if(shapeSet.size() == 1) {
			flush = true;
		}
		// 스트레이트 판별
		int num = numberList.get(0);
		for(int i=0;i<numberList.size();i++) {
			if(numberList.get(i)==num) {
				straight = true;
			}else {
				straight = false;
				break;
			}
			num++;
		}
		int [][] straightArrs = {{1,2,3,4,13}
								,{1,2,3,12,13}
								,{1,2,11,12,13}
								,{1,10,11,12,13}};
		if(numberList.get(0)==1 && numberList.get(numberList.size()-1)==13) {
			for(int i=0; i<straightArrs.length;i++) {
				column:
				for(int j=0; j<straightArrs[i].length;j++) {
					if(straightArrs[i][j]== numberList.get(j)) {
						straight = true;
					}else {
						straight = false;
						break column;
					}
				}
			}
		}
		// 마운틴 판별
		int[] mountainArr = {1,10,11,12,13};
		for(int i=0;i<5;i++) {
			if(mountainArr[i] == numberList.get(i)) {
				mountain = true;
			}else {
				mountain = false;
				break;
			}
		}
		// 백스트레이트 판별
		int[] backStraightArr = {1,2,3,4,5};
		for(int i=0;i<5;i++) {
			if(backStraightArr[i] == numberList.get(i)) {
				backStraight = true;
			}else {
				backStraight = false;
				break;
			}
		}
		
		Set<Integer> numberSet = new HashSet<Integer>();
		for(int i=0;i<numberList.size();i++) {
			numberSet.add(numberList.get(i));
		}
		Iterator<Integer> it = numberSet.iterator();
		switch(numberSet.size()) {
		case 2: // 포카드, 풀하우스 판별
			while(it.hasNext()) {
				int same = 0;
				int setNumber = it.next();
				for(int i=0; i<5;i++) {
					if(setNumber == numberList.get(i)) {
						same++;
					}
				}
				if(same == 4) {
					fourCard = true;
				}else {
					fullHouse = true;
				}
			}
			break;
		case 3: // 트리플, 투페어 판별
			while(it.hasNext()) {
				int same = 0;
				int setNumber = it.next();
				for(int i=0; i<5;i++) {
					if(setNumber == numberList.get(i)) {
						same++;
					}
				}
				if(same == 3) {
					triple = true;
				}else {
					twoFair = true;
				}
			}
			break;
		case 4:
			oneFair = true;
			break;
		default:
		}
		if(flush) {
			if(mountain) {
				royalStraightFlush = true;
			}
			if(backStraight) {
				backStraightFlush = true;
			}
			if(straight) {
				straightFlush = true;
			}
		}
		
		if(royalStraightFlush) {
			player.setDeckScore(12);
		}else if(backStraightFlush) {
			player.setDeckScore(11);
		}else if(straightFlush) {
			player.setDeckScore(10);
		}else if(fourCard) {
			player.setDeckScore(9);
		}else if(fullHouse) {
			player.setDeckScore(8);
		}else if(flush) {
			player.setDeckScore(7);
		}else if(mountain) {
			player.setDeckScore(6);
		}else if(backStraight) {
			player.setDeckScore(5);
		}else if(straight) {
			player.setDeckScore(4);
		}else if(triple) {
			player.setDeckScore(3);
		}else if(twoFair) {
			player.setDeckScore(2);
		}else if(oneFair) {
			player.setDeckScore(1);
		}else {
			player.setDeckScore(0);			
		}
	}
	
	public Player endGame() {
		Player winner = playerList.get(0);
		for(int i=0 ; i< playerList.size();i++) {
			if(winner.getDeckScore() < playerList.get(i).getDeckScore()) {
				winner = playerList.get(i);
			}else if(winner.getDeckScore() == playerList.get(i).getDeckScore()) {
				// 점수가 같은 경우 가지고 있는 카드중 가장 큰 카드를 가졌을때 우승
				int maxWinner = winner.getCardList().get(0).getNumber();
				int maxChallenger = playerList.get(i).getCardList().get(0).getNumber();
				int minWinner = winner.getCardList().get(0).getNumber();
				int minChallenger = playerList.get(i).getCardList().get(0).getNumber();
				
				for(int j=0;j<winner.getCardList().size();j++) {
					if(maxWinner < winner.getCardList().get(j).getNumber())
						maxWinner = winner.getCardList().get(j).getNumber();
					if(maxChallenger < playerList.get(i).getCardList().get(j).getNumber())
						maxChallenger = playerList.get(i).getCardList().get(j).getNumber();
					if(minWinner > winner.getCardList().get(j).getNumber())
						minWinner = winner.getCardList().get(j).getNumber();
					if(minChallenger > playerList.get(i).getCardList().get(j).getNumber())
						minChallenger = playerList.get(i).getCardList().get(j).getNumber();
				}
				if(minWinner ==1) {
					winner = winner;
				}else if(minChallenger == 1) {
					winner = playerList.get(i);
				}else if(maxChallenger > maxWinner) {
					winner = playerList.get(i);
				}
			}
		}
		
		if(winner.isUser()) {
			win++;
		}else {
			lose++;
		}
		
		return winner;
	}
	
	public void clearData() {
		Iterator<Player> it = playerList.iterator();
		int userDie = 0;
		while(it.hasNext()) {
			Player player = it.next();
			player.getCardList().clear();
			player.getHandCardList().clear();
			player.getTableCardList().clear();
			if(!player.isUser()) {
				it.remove();
			}else {
				userDie++;
			}
		}
		if(userDie == 0) {
			playerList.add(user);
			user.getCardList().clear();
			user.getHandCardList().clear();
			user.getTableCardList().clear();
		}
		
		cardDeck.clear();
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
		prize = 0;
	}
	
}
