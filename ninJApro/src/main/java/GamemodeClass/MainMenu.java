
package GamemodeClass;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import AssistClass.UserData;
import bayaba.engine.lib.ButtonObject;
import bayaba.engine.lib.ButtonType;
import bayaba.engine.lib.Font;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;
import bayaba.engine.lib.UITool;
import k.com.ninjabeta.GameMain;
import k.com.ninjabeta.Ninja;

public class MainMenu {
	static class UIButtonClass {
		static class Group0 {
			static final int POPUP_000 = 0;
			static final int POPUP_001 = 1;
			static final int ONE_CLICK_002 = 2; // 시작
			static final int ONE_CLICK_003 = 3; // 설정
			static final int ONE_CLICK_004 = 4; // 마켓
			static final int ONE_CLICK_005 = 5;
			static final int ONE_CLICK_006 = 6;
			static final int POPUP_007 = 7;
			static final int POPUP_008 = 8;
		}

	}

	public MainMenu() {
	}

	public MainMenu(Context context, GameInfo info, GL10 mGL) {// 로드게임에서 호출하면된다.
		this.MainContext = context;
		this.gInfo = info;
		this.mGL = mGL;
		MenuUI.LoadUI(mGL, MainContext, "menuui.ui");
		MenuUI.AddGroup(0, 0);
		for (int i = 0; i < 3; i++) // 주인공 스프라이트 생성
		{
			heroSpr[i] = new Sprite();
		}
		ButtonObject temp;
		heroSpr[0].LoadSprite(mGL, MainContext, "ninja1.spr");
		heroSpr[1].LoadSprite(mGL, MainContext, "ninja2.spr");
		heroSpr[2].LoadSprite(mGL, MainContext, "ninja1.spr");

		buttonSpr.LoadSprite(mGL, MainContext, "button.spr");
		backSpr.LoadSprite(mGL, MainContext, "background.spr");
		for (int i = 0; i < Button.length; i++)
			Button[i] = new ButtonObject();
		TotalButton = 0;
		for (int i = 0; i < 3; i++) // 배경 3장을 연속되게끔 생성한다.
		{
			GameObject temp2 = new GameObject();
			temp2.SetObject(backSpr, 0, 0, 240, 400 - (i * 640), 0, 0); // 배경
																		// 1장의

			Game.add(temp2);
		}
		for (int i = 0; i < 3; i++) {
			Button[TotalButton++].SetButton(buttonSpr, 0, 0, 240, 380, 7);
			Button[TotalButton - 1].x += GameInfo.SinTBL2[i * 1200] * 160;
			Button[TotalButton - 1].y -= GameInfo.CosTBL2[i * 1200] * 30;
			Button[TotalButton - 1].direct = i * 1200;
			ButtonObject temp2 = new ButtonObject();
			temp2.SetButton(heroSpr[i], 0, 0, Button[TotalButton - 1].x, Button[TotalButton - 1].y, 0);
			temp2.SetLinkTo(Button[TotalButton - 1]);

			hero.add(temp2);
		}

		for (int i = 0; i < 7; i++) {
			temp = new ButtonObject();
			temp.SetButton(buttonSpr, ButtonType.TYPE_LEFTRIGHT_LIST, 0, 120 + (i * 100), 543, 8); // 리스트뷰
			Button2.add(temp);

			/*
			 * 버튼을 생성해서 방금 위에서 생성했던 리스트뷰에 연결시키는 과정입니다. 체크박스나 라디오 버튼 등도 연결이
			 * 가능합니다.
			 */
			if (i < 3) {
				ButtonObject temp2 = new ButtonObject();
				temp2.SetButton(buttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 120 + (i * 100), 543, 13 + i);
				temp2.SetLinkTo(temp);
				temp2.direct = 1;
				Button2.add(temp2);
			}
		}
		gInfo.SetListView(50, 400, 420, 600, 100, Button2);

	}

	Font font = new Font();
	public UITool MenuUI = new UITool();
	ArrayList<GameObject> Game = new ArrayList<GameObject>();
	public ArrayList<ButtonObject> Button2 = new ArrayList<ButtonObject>(); // 버튼
	public ArrayList<ButtonObject> hero = new ArrayList<ButtonObject>();

	public ButtonObject[] Button = new ButtonObject[10];
	private int TotalButton = 0;
	public GL10 mGL = null; // OpenGL 객체
	public Context MainContext;
	public Random MyRand = new Random(); // 랜덤 발생기
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
	public int SwipeStatus = 0;
	public float OldTouchX, OldTouchY;
	public boolean TouchFlag = false;
	private Sprite buttonSpr = new Sprite();
	private Sprite heroSpr[] = new Sprite[3];
	public int checkedbutton = -1;
	Sprite backSpr = new Sprite();
	public int chargeShotType = -1;

	public int heroType = -1;

	public void UpdateGame() {
		for (int i = 0; i < Game.size(); i++) // 배경 화면 처리
		{
			Game.get(i).y += 10;
			if (Game.get(i).y1 >= gInfo.ScreenY) // 배경 오브젝트의 최상단이 화면의 맨 아래쪽을
													// 넘어선 경우, 배경 3장 중에 맨
													// 위쪽으로 옮겨준다.
			{
				Game.get(i).y -= 1920;
			}
		}

		for (int i = 0; i < TotalButton; i++) {
			/**
			 * x, y값은 중심축이 되는 좌표에서 얼만큼 멀어졌나를 계산하는 것이므로 항상 원래 좌표인 ox, oy에서 +,-를
			 * 해줘야 한다.
			 */
			Button[i].x = Button[i].ox + (GameInfo.SinTBL2[Button[i].direct] * 160);
			Button[i].y = Button[i].oy - (GameInfo.CosTBL2[Button[i].direct] * 30);

			/**
			 * SwipeStatus가 0이 아니면 왼쪽이나 오른쪽으로 스와이프한 상태이므로 direct 값에 SwipeStatus에
			 * 40을 곱한 값을 더해줘서 스와이프 방향에 따라 각도가 변하도록 해준다. 모든 버튼이 600 만큼 회전해야 다음
			 * 고양이가 나타나므로 마지막 버튼인 경우에만 각도의 % 600 값이 0인지를 체크해서 SwipeStatus를 0으로
			 * 리셋한다. 이 공식은 자기가 쓰기 편한 방식으로 바꾸어서 쓰는 것이 좋다.
			 */
			if (SwipeStatus != 0) {
				Button[i].direct += (SwipeStatus * 40);
				Button[i].direct = (Button[i].direct + 7200) % 3600;
				if (i == TotalButton - 1 && Button[i].direct % 1200 == 0)
					SwipeStatus = 0;
			}

			float scale;
			/**
			 * 원이 한바퀴를 돌면 3600도를 회전한 것이므로 1800 이하일때와 이상일때를 따로 계산해줘야 한다. 어느 쪽이든지
			 * 좌우의 값이 똑같게 설정해주었다. 아래 공식에서 0.5f는 이미지가 축소됐을 때의 최소 축소값을 나타낸다. 확대값은
			 * 최대 1800인 값을 1200으로 나누었으므로 1.5f + 0.5f = 2.0f가 최대값이 된다. 즉, 0.5f까지
			 * 축소 되었다가 2.0f까지 확대되면서 보여주게 된다.
			 */
			if (Button[i].direct < 1800)
				scale = 0.8f - ((float) Button[i].direct / 4000.0f);
			else
				scale = 0.8f - ((float) (3600 - Button[i].direct) / 4000.0f);
			Button[i].SetZoom(gInfo, scale , scale );

			hero.get(i).SetZoom(gInfo, scale * 1.5f, scale * 1.5f);
			if (scale > 0.78) {
				heroType = i;
				hero.get(i).AddFrameLoop(0.2f); // 맨앞에있는애만 애니메이션
			} else {
				hero.get(i).y += 120; // 박스에서 쳐져보이는 것을 수정함.
			}
		}

	}

	public void PushButton(boolean push, float TouchX, float TouchY) {
		MenuUI.Touch(gInfo, (int) TouchX, (int) TouchY, push);
		for (int i = 0; i < Button2.size(); i++)
			Button2.get(i).CheckButton(gInfo, push, TouchX, TouchY);
		if (push) {
			if (TouchX > 48 && TouchY > 200 && TouchX < 340 && TouchY < 390) // 케릭터
																				// 셀렉트창
																				// 터치했을경우
			{
				if (!TouchFlag) {
					OldTouchX = TouchX;
					OldTouchY = TouchY;
					TouchFlag = true;
				}
			}
		} else {
			if (TouchFlag) {
				if (SwipeStatus == 0) {
					/**
					 * 왼쪽으로 스와이프하면 SwipeStatus를 1, 오른쪽으로 스와이프하면 SwipeStatus를 -1로
					 * 설정한다.
					 */
					if (TouchX < OldTouchX)
						SwipeStatus = -1;
					else if (TouchX > OldTouchX)
						SwipeStatus = +1;
				}
				TouchFlag = false;
			}
		}
	}

	public void DoMenuMethod() {
		synchronized (mGL) {
			UpdateGame();
			for (int i = 0; i < Game.size(); i++) {
				Game.get(i).DrawSprite(gInfo); // 배경
			}
			MenuUI.Draw(mGL, gInfo, font);
			buttonSpr.PutValueCenter(gInfo, 155, 160, 23, UserData.gold, 13, true);
			// buttonSpr.PutAni(gInfo,210, 140, 1,0);

			buttonSpr.PutValueCenter(gInfo, 365, 160, 22, UserData.dia, 13, true);
			// buttonSpr.PutAni(gInfo, 430, 140, 4,0); 단위 숫자칸이 모자라서 뺌
			{
				int Data[] = new int[TotalButton], Sort[] = new int[TotalButton];

				for (int i = 0; i < TotalButton; i++) {
					Data[i] = i;
					Sort[i] = (int) -Button[i].y;
				}
				GameInfo.Sort(Data, Sort, 0, TotalButton - 1);

				for (int k = 0; k < TotalButton; k++) {
					int i = Data[k];
					Button[i].DrawSprite(mGL, 0, gInfo, GameInfo.font);
					hero.get(i).DrawSprite(mGL, 0, gInfo, GameInfo.font);

				}
			}

			for (int i = 0; i < Button2.size(); i++) {
				Button2.get(i).DrawSprite(mGL, 0, gInfo, GameInfo.font);
				if (Button2.get(i).type == ButtonType.TYPE_ONE_CLICK) // 버튼 타입인지
																		// 체크한다.
				{
					if (Button2.get(i).click == ButtonType.STATE_CLK_BUTTON) {

						chargeShotType = i;
						Button2.get(i).ResetButton();
					}
					if (chargeShotType == i) {
						if (Button2.get(i).angle >= 30) {
							Button2.get(i).direct = -1;
						} else if (Button2.get(i).angle <= -30) {
							Button2.get(i).direct = +1;
						}

						Button2.get(i).angle += Button2.get(i).direct * 3;
					} else {
						Button2.get(i).angle = 0;
					}
				}
			}
			for (int i = 0; i < MenuUI.UIList.size(); i++) // 모든 UI의 버튼 터치 상태를
															// 체크하기 위한 루프
			{
				if (MenuUI.UIList.get(i).click == ButtonType.STATE_CLK_BUTTON) {
					switch (MenuUI.UIList.get(i).index) {
					case 2: // 플레이
						GameMain.gamemode = 2;
						MenuUI.UIList.get(i).ResetButton();
						break;

					case 3: // 옵션
						GameMain.canback = true;
						GameMain.ui.OptionUI.AddGroup(0, 1);
						GameMain.ui.Shopmode=0;
						if (GameMain.ui.vibeON == false) {
							GameMain.ui.OptionUI.UIList.get(8).frame = 1;
						} else {
							GameMain.ui.OptionUI.UIList.get(8).frame = 0;
						}
						if (GameMain.ui.volumON == false) {
							GameMain.ui.OptionUI.UIList.get(4).frame = 1;
						} else {
							GameMain.ui.OptionUI.UIList.get(4).frame = 0;
						}
						GameMain.gamemode = 3;

						MenuUI.UIList.get(i).ResetButton();
						break;
					case 4: // 마켓
						GameMain.canback = true;
						GameMain.ui.Shopmode=1;
						GameMain.ui.Settingtimer=System.currentTimeMillis();
						GameMain.ui.ShopUI.AddGroup(0, 1);
						GameMain.ui.ShopUI.AddGroup(1, 1);
						GameMain.ui.ShopUI.UIList.get(1).frame = 0;
						GameMain.ui.ShopUI.UIList.get(2).frame = 1;
						GameMain.ui.ShopUI.UIList.get(3).frame = 1;
						GameMain.ui.ShopUI.UIList.get(4).frame = 1;
						GameMain.ui.ShopUI.UIList.get(5).frame = 1;
						
						GameMain.gamemode = 3;
						MenuUI.UIList.get(i).ResetButton();
						break;
					}
				}
			}
		}
	}

	public Ninja setNinJa() {
		return new Ninja(15, 5, 10, 40, heroType, chargeShotType < 0 ? 13 : Button2.get(chargeShotType).motion);
	}

	public void EndMainMenu() {
		Button2 = null;
		this.hero = null;
		TotalButton = 0;
		MyRand = null;
		Button = null;
		SwipeStatus = 0;
		TouchFlag = false;
		this.buttonSpr = null;
		this.heroSpr = null;
		this.checkedbutton = 0;
	}

	public void onBackPressed() {
		if (GameMain.canback ) {
			GameMain.ui.Deletepage();
			if (GameMain.ui.OptionUI.Get(1, 0) == null) {
			
				GameMain.canback = false;
				GameMain.gamemode = 1;
			}
		} else {
			
		}
	}

}
