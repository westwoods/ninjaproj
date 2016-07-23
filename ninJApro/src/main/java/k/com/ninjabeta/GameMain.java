package k.com.ninjabeta;

import android.content.Context;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import AssistClass.ConnectDB;
import AssistClass.UserData;
import GamemodeClass.InGame;
import GamemodeClass.Intro;
import GamemodeClass.MainMenu;
import GamemodeClass.UI;
import bayaba.engine.lib.Font;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class GameMain {
	static class UIButtonClass
	{

	}

	public GL10 mGL = null; // OpenGL 객체
	public Context MainContext;
	public Random MyRand = new Random(); // 랜덤 발생기
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
	public static int gamemode = 0; // intro, game, ending 을 구분할변수
	public static int Mainmode = 0; // 상태를 넘기기전에 초기화해주기위한변수
	// 스프라이트 파일을 불러올 클래스 변수
	public InGame game;
	private Intro intro;
	public MainMenu menu;
	public static UI ui;
	public static boolean canback=false;
	private Font font=new Font();
	GameObject[] Coin =new GameObject[100];
	Sprite[] coinSpr = new Sprite[1];
	private int totalcoin=0;
	public GameMain(Context context, GameInfo info) // 클래스 생성자 (메인 액티비티에서 호출)
	{
		MainContext = context; // 메인 컨텍스트를 변수에 보관한다.
		gInfo = info; // 메인 액티비티에서 생성된 클래스를 가져온다.
		for (int i = 0; i < coinSpr.length; i++) // 배경 3장을 연속되게끔 생성한다.
		{
		coinSpr[i] = new Sprite();
		}


	}

	public void LoadGameData() // SurfaceClass에서 OpenGL이 초기화되면 최초로 호출되는 함수
	{

		intro = new Intro(MainContext, gInfo, mGL);

		menu = new MainMenu(MainContext, gInfo, mGL);

		game = new InGame(MainContext, gInfo, mGL, new Ninja(),font);

		ui = new UI(MainContext, gInfo, mGL);
	}

	public void PushButton(boolean push) // OpenGL 화면에 터치가 발생하면 GLView에서 호출된다.
	{
			if (gamemode == 1) // 메뉴일때 터치판별
			{
				menu.PushButton(push, TouchX, TouchY);
			}
			if (gamemode == 2) // 게임중일때 터치판별
			{
				game.PushButton(push, TouchX, TouchY);
			} //////////////////// 게임중일때 터치판별
			if (gamemode == 3){
			ui.PushButton(push, TouchX, TouchY);
			}
		
	}

	public void DoGame() // 1/60초에 한번씩 SurfaceClass에서 호출된다. 게임의 코어 부분을 넣는다.
	{
		synchronized (mGL) {

			GameInfo.font.BeginFont(gInfo);	// 게임의 코어 부분을 코딩합니다.
			if (Mainmode == 0) {
				intro.DoIntroMethod();
				if (gamemode == 1){
					ui.SetItem();
					Mainmode = 1;
					}
			}
			if (Mainmode == 1) {
				menu.DoMenuMethod();
				if (gamemode == 2) {
					game.newGame(menu.setNinJa());
					Mainmode = 2;
				}
			}
			if (Mainmode == 2) {
				game.DogameMethod(); // 두게임 호출\

			}if (gamemode == 3){
			ui.DoUIMethod();}
		GameInfo.font.EndFont(gInfo);	
		}

	}

	public void EndGame() { // 게임끄면서 수행할것은 여기서 호출하면됨.
		intro.EndIntro();
		intro = null;
		menu.EndMainMenu();
		menu = null;
		game.EndDoGame();
		game = null;
		UserData.DelUserData();
		ConnectDB.downflag=false;
		canback=false;
		gamemode = 0;
		Mainmode = 0;
	}

	public void onBackPressed() {
		if (Mainmode == 1) {
			menu.onBackPressed();
		}else if (Mainmode == 2) {
			game.onBackPressed();
		}

	}
}
