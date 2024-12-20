package com.dec.project.re;

public class PokerController {
	public static void main(String[] args) {
		PokerView view = new PokerView();
		PokerManage manage = new PokerManage();
		
		manage.addPlayer(view.setMe());
		end:
		while(true) {
			switch(view.printMenu()) {
			case 1:
				Player me = manage.getUser(); // 유저플레이어 객체
				int count = 0;
				// 같이 할 인원 정하기
				manage.addComPlayer(view.setPlayerNumber());
				// 플레이어들 정보 출력
				view.printPlayerInfor(manage.getPlayerList());
				for(int i=0;i<manage.getPlayerList().size();i++) {
					view.printBet(manage.getPlayerList().get(i), 5000);
					manage.betting(manage.getPlayerList().get(i), 5000);
				}
				// 플레이어들 정보 출력
				view.printPlayerInfor(manage.getPlayerList());
				// 카드 나눠주기
				for(int i=0; i<3;i++) {
					view.displayMessage("카드를 나눠드리겠습니다.");
					view.displayMessage("...");
					manage.giveCard();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				// 내 카드 출력
				view.printUserCard(me);
				// 테이블위에 올려놓을 카드 선택해 올려놓음
				manage.selectUserShowCard(view.selectUserShowCard(me));
				manage.selectComShowCard();
				view.printUserHand(me);
				view.printTable(manage.getPlayerList());
				// 베팅
				count++;
				betting(manage, view, count);
				for(int i=0;i<2;i++) {
					count++;
					// 테이블위에 카드 올려놓음
					view.displayMessage("카드를 나눠드리겠습니다.");
					view.displayMessage("...");
					manage.giveShowCard();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					view.printUserHand(me);
					view.printTable(manage.getPlayerList());
					// 베팅 반복
					betting(manage, view,count);
				} 
				// 누가이겼는지 출력
				view.printshowAll(manage.getPlayerList());
				for(int i=0; i<manage.getPlayerList().size();i++) {
					manage.evaluateDeck(manage.getPlayerList().get(i));
					view.printGameScore(manage.getPlayerList().get(i));
				}
				view.printWinner(manage.endGame(), manage.getPrize());
				manage.clearData();
				break;
			case 2:
				view.printMoney(manage.getUser());
				break;
			case 3:
				view.printScore(manage.getWin(), manage.getLose());
				break;
			case 4:
				view.displayMessage("프로그램을 종료합니다.");
				break end;
			default:
				view.displayMessage("메뉴번호를 입력해주세요.");
			}
		}
	}
	
	// 베팅 메소드
	public static void betting(PokerManage manage, PokerView view, int count) {
		System.out.println("===== "+count+"번째 베팅 =====");
		// 누가 이번턴의 초기베팅액을 정할지 결정
		Player startBet = manage.choiceStartBettingPlayer();
		// 총 상금
		int prize = manage.getPrize();
		// 이번턴 베팅액
		int bettingMoney = 0;
		if(startBet.isUser()) {
			// 유저일때 얼마 베팅할 것인지 결정
			bettingMoney = view.printStartBetting(startBet, prize);
		}else {
			// 컴퓨터면 컴퓨터가 정함
			bettingMoney = manage.comSetBettingMoney(startBet);
		}
		manage.setBettingMoney(bettingMoney);
		// 누가 얼마로 설정했는지 출력
		view.printStartBettingInfor(startBet, bettingMoney);
		end:
		while(true) {
			// 총 상금과 현재 베팅액 출력
			view.printBettingMoney(prize, bettingMoney);
			String checkFinish = "";
			String yesFinish = "";
			// 플레이어들이 콜/레이즈/다이를 결정함
			for(Player player : manage.getPlayerList()) {
				if(player.isUser()) { // 유저일때
					checkFinish += view.choiceBetting(player);
					if(player.getBettingChoice().equals("레이즈")) {
						bettingMoney += view.choiceRaiseMoney(player, bettingMoney);
					}else if(player.getBettingChoice().equals("다이")){
						view.displayMessage(player.getName()+"님이 다이를 선택하셨습니다.");
						manage.deletePlayer();
						continue end;
					}
				}else { // 컴퓨터일때
					checkFinish += manage.comChoiceBet(player);
					if(player.getBettingChoice().equals("레이즈")) {
						bettingMoney += manage.comChoiceRaiseMoney(player);
					}else if(player.getBettingChoice().equals("다이")){
						view.displayMessage(player.getName()+"님이 다이를 선택하셨습니다.");
						manage.deletePlayer();
						continue end;
					}
				}
				yesFinish += "콜";
			}
			manage.setBettingMoney(bettingMoney);
			view.printChoiceBet(manage.getPlayerList());
			if(checkFinish.equals(yesFinish)) {
				break end;
			}
		}
		view.printBettingMoney(manage.getPrize(), bettingMoney);
		for(Player player : manage.getPlayerList()) {
			manage.betting(player, bettingMoney);
			view.printBet(player, bettingMoney);
		}
		
	}
}
