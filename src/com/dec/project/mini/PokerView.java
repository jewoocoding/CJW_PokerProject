package com.dec.project.mini;

import java.util.*;

public class PokerView {
	// 필드
	private Scanner sc;
	//메소드
	// 생성자 
	public PokerView() {
		sc = new Scanner(System.in);
	}
	
	// 본인의 정보 입력
	public Player setMe() {
		int input = 0;
		String name = "";
		// 초기 자본을 입력 받음
		start:
		while(true) {
			try {
				System.out.print("당신의 이름을 입력해 주세요 : ");
				name = sc.next();
				System.out.print("게임을 플레이하기 위한 초기 금액을 정해주세요 : ");
				input = sc.nextInt();
				break start;
			}catch(InputMismatchException e) {
				System.out.println("숫자를 입력해주세요.");
				sc.next();
				continue;
			}
		}
		Player me = new Player(name, input,true);
		return me;
	}
	
	public int printMenu() {
		// 메인메뉴 출력 후, 선택한 번호 반환
		int input = 0;
		while(true) {
			try {
				System.out.println("===== 메인 메뉴 =====");
				System.out.println("1. 게임 시작");
				System.out.println("2. 보유 금액 확인");
				System.out.println("3. 전적 확인");
				System.out.println("4. 프로그램 종료");
				System.out.print("선택 >> ");
				input = sc.nextInt();
				break;
			}catch(InputMismatchException e) {
				System.out.println("숫자를 입력해주세요.");
				sc.next();
				continue;
			}
		}
		return input;
	}
	
	// 보유금액 확인 화면 출력
	public void printMoney(int money) {
		System.out.println("===== 보유 금액 확인 =====");
		System.out.printf("현재 보유 금액은 %d입니다.\n",money);
	}
	// 전적 출력
	public void printScore(int win, int lose) {
		System.out.println("===== 전적 =====");
		System.out.println("W I N : "+win);
		System.out.println("LOSE : "+lose);
	}
	// 게임 시작 시 기본금 빠져나가는 화면 출력
	public void printEnter(int enter) {
		System.out.println("게임 기본 베팅액 "+enter+"원을 지불하셨습니다.");
	}
	
	// 게임인원 정해서 몇 명인지 리턴
	public int setPlayer() {
		int player = 0;
		set:
		while(true) {
			try {
				System.out.print("같이 플레이할 인원(2인~4인) : ");
				player = sc.nextInt();
				switch(player) {
				case 2,3,4:
					break set;
				default:
					System.out.println("2~4인만 플레이가 가능합니다.");
					continue;
				}
			}catch(InputMismatchException e) {
				System.out.println("숫자를 입력해주세요.");
				sc.next();
				continue;
			}
		}
		
		return player;
	}
	
	// 플레이어 정보 출력
	public void printPlayerInfor(List<Player> playerList) {
		System.out.println("===== 플레이어 =====");
		System.out.print("플레이어 이름\t");
		for(Player player : playerList) {
			System.out.printf("%s\t",player.getName());
		}
		System.out.println();
		System.out.print("보유 금액\t");
		for(Player player : playerList) {
			System.out.printf("%s\t",player.getMoney());
		}
		System.out.println();
	}
	// 플레이어의 카드 출력
	public void printMyCards(List<Player> playerList) {
		Player me = playerList.get(0);
		System.out.println("내가 가진 카드");
		for(int i=0;i<me.getMyCardCount();i++) {
			System.out.println(i+"번 "+me.getMyCard().get(i).getShape()+" "+me.getMyCard().get(i).getNumber());
			System.out.println();
		}
	}
	// 내가 공개할 카드 선택
	public void selectMyShowCard(List<Player> playerList) {
		Player me = playerList.get(0);
		select:
		while(true) {
			try {
				System.out.print("내가 공개할 카드 : ");
				int input = sc.nextInt();
				if(input<0 || input>me.getMyCardCount()-1) {
					System.out.println("인덱스값을 입력해주세요");
					continue;
				}
				me.setShowCard(input);
				break select;
			}catch(InputMismatchException e) {
				System.out.println("숫자를 입력해주세요");
				sc.next();
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("인덱스값을 입력해주세요");
			}
		}
	}
	// 테이블 출력
	public void printTable(List<Player> playerList) {
		System.out.println("===== 테이블 =====");
		for(int i=0; i< playerList.size();i++) {
			System.out.println(playerList.get(i).getName());
			for(int j=0; j< playerList.get(i).getShowCard().size();j++) {
				System.out.printf("%s %d\n",playerList.get(i).getShowCard().get(j).getShape()
						,playerList.get(i).getShowCard().get(j).getNumber() );
			}
			System.out.println();
		}
	}
	// 베팅액 설정 화면
	public int setBettingMoney(Player player, int bettingMoney) {
		System.out.println("이번턴에 베팅하실 분은 "+player.getName()+"님입니다.");
		int input = 0;
		set:
		while(true) {
			try {
				System.out.print("얼마를 베팅하시겠습니까? ");
				input = sc.nextInt();
				if(input > player.getMoney()) {
					System.out.println("현재 가진 돈 이내로 베팅해주세요.");
					continue;
				}else if(input > bettingMoney/2) {
					System.out.println("현재 베팅액의 절반을 넘지 않도록 해주세요.");
					continue;
				}
				break set;
			}catch(InputMismatchException e) {
				System.out.println("정수를 입력해주세요.");
				sc.next();
				continue;
			}
		}
		return input;
	}
	// 현재 베팅액 출력
	public void printBettingMoney(int bet) {
		System.out.println("현재 베팅액은 "+bet+"원입니다.");
	}
	// 얼마 베팅했는지 출력
	public void printBettingMoney(Player player, int bet) {
		System.out.println(player.getName()+"님이 "+bet+"원을 베팅하셨습니다.");
	}
	// 플레이어가 콜/레이즈/다이 결정
	public String choiceBet() {
		while(true) {
			System.out.print("베팅할지 결정해주세요.(콜/레이즈/다이) : ");
			String answer = sc.next();
			switch(answer) {
			case "콜","레이즈","다이":
				return answer;
			default: System.out.println("명령어를 다시 입력해주세요.");
			}
		}
	}
	// 플레이어가 레이즈를 선택했을때 올릴 금액 결정
	public int choiceRaiseBettingMoney(Player me, int bet) {
		int input = 0;
		while(true) {
			try {
				System.out.print("얼마를 더 거시겠습니까? : ");
				input = sc.nextInt();
				if(input > me.getMoney()) {
					System.out.println("돈이 부족합니다.");
					continue;
				}else if(input > bet/2) {
					System.out.println("올릴 금액은 베팅액의 절반을 넘을 수 없습니다.");
					continue;
				}
				break;
			}catch(InputMismatchException e) {
				System.out.println("숫자를 입력해주세요.");
			}
		}
		return input;
	}

	// 메세지 출력
	public void displayMessage(String msg) {
		System.out.println(msg);
	}
	
}
