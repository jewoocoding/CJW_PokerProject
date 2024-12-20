package com.dec.project.re;

import java.util.*;

public class PokerView implements ViewInterface {
	// 필드
	private Scanner sc;
	// 생성자
	public PokerView() {
		sc = new Scanner(System.in);
	}
	
	@Override
	public Player setMe() {
		Player user = new Player();
		user.setUser(true);
		System.out.println("게임을 시작합니다.");
		start:
		while(true) {
			try {
				System.out.print("당신의 이름을 입력해 주세요 : ");
				user.setName(sc.next());
				System.out.print("게임을 플레이하기 위한 초기 금액을 정해주세요 : ");
				user.setMoney(sc.nextInt());
				break start;
			}catch(InputMismatchException e) {
				System.out.println("숫자를 입력해주세요.");
				sc.next();
				continue;
			}
		}
		System.out.println();
		return user;
	}

	@Override
	public int printMenu() {
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
		System.out.println();
		return input;
	}

	@Override
	public void printMoney(Player player) {
		System.out.println("===== 보유 금액 확인 =====");
		System.out.printf("%s님의 현재 보유 금액은 %d입니다.\n",player.getName(),player.getMoney());
		System.out.println();
	}

	@Override
	public void printScore(int win, int lose) {
		System.out.println("===== 전적 =====");
		System.out.println("W I N : "+win);
		System.out.println("LOSE : "+lose);
		System.out.println();
	}

	@Override
	public void printBet(Player player, int bettingMoney) {
		System.out.printf("%s님이 %d원을 베팅하셨습니다.\n",player.getName(),bettingMoney);
		System.out.println();
	}

	@Override
	public int setPlayerNumber() {
		int playerNumber = 0;
		set:
		while(true) {
			try {
				System.out.print("같이 플레이할 인원(1인~3인) : ");
				playerNumber = sc.nextInt();
				switch(playerNumber) {
				case 1,2,3:
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
		System.out.println();
		return playerNumber;
	}

		@Override
	public void printPlayerInfor(List<Player> playerList) {
		System.out.println("===== 플레이어 =====");
		System.out.print("이름\t보유 금액\n");
		for(Player player : playerList) {
			System.out.printf("%s\t%d\n",player.getName(),player.getMoney());
		}
		System.out.println();
	}

	@Override
	public void printUserCard(Player user) {
		System.out.println("내가 가진 카드");
		for(int i=0;i<user.getCardList().size();i++) {
			System.out.println(i+"번 "+user.getCardList().get(i).getShape()+" "+user.getCardList().get(i).getNumber());
			System.out.println();
		}
	}

	@Override
	public int selectUserShowCard(Player user) {
		int input = 0;
		while(true) {
			try {
				System.out.print("내가 공개할 카드 : ");
				input = sc.nextInt();
				if(input<0 || input>user.getCardList().size()-1) {
					System.out.println("인덱스값을 입력해주세요");
					continue;
				}
				break;
			}catch(InputMismatchException e) {
				System.out.println("숫자를 입력해주세요");
				sc.next();
			}
		}
		System.out.println();
		return input;
	}

	@Override
	public void printTable(List<Player> playerList) {
		System.out.println("===== 테이블 =====");
		for(Player player : playerList) {
			System.out.println(player.getName());
			for(int i=0; i<player.getTableCardList().size();i++)
				System.out.println(player.getTableCardList().get(i).toString());
		}
		System.out.println();
	}
	
	public void printshowAll(List<Player> playerList) {
		System.out.println("===== 테이블 =====");
		for(Player player : playerList) {
			System.out.println(player.getName());
			for(int i=0; i<player.getCardList().size();i++)
				System.out.println(player.getCardList().get(i).toString());
		}
		System.out.println();
	}

	@Override
	public int printStartBetting(Player user, int prize) {
		System.out.println("이번턴에 베팅하실 분은 "+user.getName()+"님입니다.");
		System.out.println("현재 총 상금 : "+prize);
		int input = 0;
		set:
		while(true) {
			try {
				System.out.print("얼마를 베팅하시겠습니까? ");
				input = sc.nextInt();
				if(input > user.getMoney()) {
					System.out.println("현재 가진 돈 이내로 베팅해주세요.");
					continue;
				}else if(input > prize/2) {
					System.out.println("총 상금의 절반을 넘지 않도록 해주세요.");
					continue;
				}
				break set;
			}catch(InputMismatchException e) {
				System.out.println("정수를 입력해주세요.");
				sc.next();
				continue;
			}
		}
		System.out.println();
		return input;
	}
	
	public void printStartBettingInfor(Player player, int bettingMoney) {
		System.out.println(player.getName()+"님이 기본베팅액을 "+bettingMoney
				+"원으로 설정하셨습니다.");
		System.out.println();
	}

	@Override
	public String choiceBetting(Player user) {
		while(true) {
			System.out.print("베팅할지 결정해주세요.(콜/레이즈/다이) : ");
			String answer = sc.next();
			switch(answer) {
			case "콜":
				user.setBettingChoice("콜");
				System.out.println();
				return answer;
			case "다이":
				user.setBettingChoice("다이");
				System.out.println();
				return answer;
			case "레이즈":
				if(user.getMoney() < 0) {
					System.out.println("돈이 부족합니다.");
					System.out.println();
					return "콜";
				}
				user.setBettingChoice("레이즈");
				System.out.println();
				return answer;
			default: System.out.println("명령어를 다시 입력해주세요.");
			}
		}
	}

	@Override
	public int choiceRaiseMoney(Player user, int bettingMoney) {
		int input = 0;
		while(true) {
			try {
				System.out.print("얼마를 더 거시겠습니까? (현재 가진 돈 : "+user.getMoney()+"): ");
				input = sc.nextInt();
				if(input > user.getMoney()) {
					System.out.println("돈이 부족합니다.");
					continue;
				}else if(input > bettingMoney/2) {
					System.out.println("올릴 금액은 베팅액의 절반을 넘을 수 없습니다.");
					continue;
				}else if(input+bettingMoney > user.getMoney()){
					System.out.println("돈이 부족합니다.");
					continue;
				}
				break;
			}catch(InputMismatchException e) {
				System.out.println("숫자를 입력해주세요.");
			}
		}
		System.out.println();
		return input;
	}

	@Override
	public void printChoiceBet(List<Player> playerList) {
		for(Player player : playerList)
			System.out.println(player.getName()+"님 : "+player.getBettingChoice());
		System.out.println();
	}
	
	public void printBettingMoney(int prize, int bettingMoney) {
		System.out.println("이번게임 총 상금 : "+prize);
		System.out.println("이번턴 베팅액 : "+bettingMoney);
		System.out.println();
	}
	public void printGameScore(Player player) {
		int score = player.getDeckScore();
		String highDeck = "";
		switch(score) {
		case 12:
			highDeck = "royalStraightFlush";
			break;
		case 11:
			highDeck = "backStraightFlush";
			break;
		case 10:
			highDeck = "straightFlush";
			break;
		case 9:
			highDeck = "fourCard";
			break;
		case 8:
			highDeck = "fullHouse";
			break;
		case 7:
			highDeck = "flush";
			break;
		case 6:
			highDeck = "mountain";
			break;
		case 5:
			highDeck = "backStraight";
			break;
		case 4:
			highDeck = "straight";
			break;
		case 3:
			highDeck = "triple";
			break;
		case 2:
			highDeck = "twoFair";
			break;
		case 1:
			highDeck = "oneFair";
			break;
		default:
			highDeck = "noFair";
		}
		System.out.println(player.getName()+"님의 최종덱 : "+highDeck);
		System.out.println();
		
	}
	
	public void printWinner(Player player, int prize) {
		System.out.println(player.getName()+"님 축하합니다! 당신이 승리했습니다!");
		System.out.println("이번 게임 총 상금 : "+prize);
	}

	@Override
	public void displayMessage(String message) {
		System.out.println(message);
		System.out.println();
	}
	
}
