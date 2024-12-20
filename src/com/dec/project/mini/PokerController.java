package com.dec.project.mini;

import java.util.*;

public class PokerController {
	public static void main(String[] args) {
		PokerView view = new PokerView();
		// 초기 자본금 입력받고 manage객체 생성
		PokerManage manage = new PokerManage();
		// 본인 정보 입력 -> 인덱스 0번째에 나의 정보를 저장
		manage.pushPlayer(view.setMe());
		end:
		while(true) {
			switch(view.printMenu()) {
			case 1:
				// 같이 플레이할 인원설정해 playerList에 추가
				manage.pushPlayer(view.setPlayer());
				// 입장료
				view.printEnter(manage.enter(5000));
				// 플레이어 정보 출력
				view.printPlayerInfor(manage.getPlayerList());
				// 카드 나눠줌
				for(int i=0;i<4;i++) {
					view.displayMessage("카드 지급");
					manage.giveCard();				
				}
				// 내 카드 출력
				view.printMyCards(manage.getPlayerList());
				// 컴퓨터가 무슨 카드를 테이블에 올려놓을지 선택
				manage.selectShowCard();
				// 나는 무슨 카드를 올려놓을지 선택
				view.selectMyShowCard(manage.getPlayerList());
				// 테이블 출력
				view.printTable(manage.getPlayerList());	
				
				// 첫 베팅
				switch(betting(manage,view)) {
				case "다이":
					continue end;
				default:
				}
				// 베팅 끝
				
				for(int i=0;i<2;i++) {
					// 공개할 카드 지급
					view.displayMessage("카드 지급");
					manage.giveShowCard();
					// 테이블 출력
					view.printTable(manage.getPlayerList());
					view.printMyCards(manage.getPlayerList());
					
					// 베팅
					
					// 베팅 끝
					
				}
				manage.giveCard();
				view.printMyCards(manage.getPlayerList());
				
				// 마지막 베팅
				
				// 베팅 끝
				
				// 게임결과 출력 
				
				break;
			case 2:
				// 나의 객체의 money를 출력
				view.printMoney(manage.getPlayer(0).getMoney());
				break;
			case 3:
				// 전적 출력
				view.printScore(manage.getWin(), manage.getLose());
				break;
			case 4:
				// 프로그램 종료
				view.displayMessage("프로그램을 종료합니다.");
				break end;
			default:
				// 다른 숫자를 입력했을 때 출력
				view.displayMessage("메뉴에 있는 숫자를 입력해주세요.");
				continue;
			}
		}
		
		
	}
	public static String betting(PokerManage manage, PokerView view) {
		Player startBet = manage.choiceStartBettingPlayer(); // 베팅액을 제시할 인원 고르기
		int bettingMoney = 0; // 제시한 베팅액
		if(startBet == manage.getPlayer(0)) {
			bettingMoney = view.setBettingMoney(startBet, manage.getBettingMoney()); // 유저가 베팅액 설정
		}else {
			bettingMoney = manage.comSetBettingMoney(startBet); // 컴퓨터가 베팅액 설정
		}
		view.printBettingMoney(startBet, bettingMoney);
		// 베팅을 위해 플레이어 리스트를 만듦
		List<Player> bettingList = new ArrayList<Player>();
		for(Player player : manage.getPlayerList())
			bettingList.add(player);
		
		bettingEnd:
		while(true) {
			String finishBet = ""; // 모두 콜했는지 확인하는 문자열
			
			for(int i=0;i<bettingList.size();i++) {
				if(bettingList.get(i).isUser()) {
					finishBet += view.choiceBet();
					switch(finishBet) {
					case "콜" :
						manage.choiceCall(manage.getPlayer(i), bettingMoney);
						break;
					case "레이즈" :
						bettingMoney += manage.betting(manage.getPlayer(i)
								, view.choiceRaiseBettingMoney(manage.getPlayer(i), bettingMoney));
						break;
					case "다이" :
						manage.choiceDie(manage.getPlayer(i));
						return "다이";
					}
				}else {
					String comChoice = manage.comChoiceBet(manage.getPlayer(i));
					switch(comChoice) {
					case "콜" :
						manage.choiceCall(manage.getPlayer(i), bettingMoney);
						break;
					case "레이즈" :
						bettingMoney += manage.comSetBettingMoney(manage.getPlayer(i));
						break;
					case "다이" :
						manage.choiceDie(manage.getPlayer(i));
						break;
					}
					finishBet += manage.comChoiceBet(manage.getPlayer(i));
				}
			}
			
			for(int i=1;i<manage.getPlayerList().size();i++) {
				// 컴퓨터의 선택
				// 레이즈한다면 베팅액 추가
				String comChoice = manage.comChoiceBet(manage.getPlayer(i));
				switch(comChoice) {
				case "콜" :
					manage.choiceCall(manage.getPlayer(i), bettingMoney);
					break;
				case "레이즈" :
					bettingMoney += manage.comSetBettingMoney(manage.getPlayer(i));
					break;
				case "다이" :
					manage.choiceDie(manage.getPlayer(i));
					break;
				}
				finishBet += manage.comChoiceBet(manage.getPlayer(i));
			}
			String finish = "";
			for(int j=0;j<manage.getPlayerList().size();j++) {
				finish += "콜";
			}
			if(finishBet.equals(finish)) {
				break bettingEnd;						
			}
		}
		return "";
	}
}
