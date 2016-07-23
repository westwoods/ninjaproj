package GamemodeClass;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import AssistClass.ItemSet;
import AssistClass.UserData;
import bayaba.engine.lib.ButtonObject;
import bayaba.engine.lib.ButtonType;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.Sprite;
import bayaba.engine.lib.UITool;

public class UI {
	static class UIButtonClass {
		static class Group0 {
			static final int POPUP_000 = 0;
			static final int POPUP_001 = 1;
			static final int POPUP_002 = 2;
			static final int POPUP_003 = 3;
			static final int CHECKBOX_004 = 4;
			static final int POPUP_005 = 5;
			static final int PREGRESS_RIGHT_006 = 6;
			static final int ONE_CLICK_007 = 7;
			static final int CHECKBOX_008 = 8;
			static final int KEEP_CLICK_009 = 9;
		}

		static class Group1 {
			static final int POPUP_000 = 10;
			static final int POPUP_001 = 11;
			static final int ONE_CLICK_002 = 12; // 공격
			static final int ONE_CLICK_003 = 13; // 방어
			static final int ONE_CLICK_004 = 14; // 회복
			static final int ONE_CLICK_005 = 15; // 특수
		}

		static class Group2 {
			static final int POPUP_000 = 16;
			static final int POPUP_001 = 17;
		}

	}

	static class ShopUIButtonClass {

		static class Group0 {
			static final int POPUP_000 = 0;
			static final int RADIO_001 = 1;
			static final int RADIO_002 = 2;
			static final int RADIO_003 = 3;
			static final int RADIO_004 = 4;
			static final int RADIO_005 = 5;
			static final int POPUP_006 = 6;
		}

		static class Group1 {
			static final int POPUP_000 = 7;
			static final int POPUP_001 = 8;
			static final int POPUP_002 = 9;
			static final int POPUP_003 = 10;
			static final int POPUP_004 = 11;
			static final int POPUP_005 = 12;
			static final int POPUP_006 = 13;
		}

		static class Group2 {
			static final int POPUP_000 = 14;
			static final int POPUP_001 = 15;
			static final int POPUP_002 = 16;
		}

	}

	public boolean TouchFlag = false;
	public float TouchX, TouchY;
	public float OldTouchX, OldTouchY;
	public int SwipeStatus = 0;

	public int Shopmode = 1;

	public long Settingtimer = 0;
	public int heroType = -1;
	private Sprite itemSpr = new Sprite();

	private Sprite shopSpr = new Sprite();
	private int TotalButton = 0;

	private ArrayList<ButtonObject> Button3 = new ArrayList<ButtonObject>();
	public ArrayList<ButtonObject> Button2 = new ArrayList<ButtonObject>(); // 버튼
	public ArrayList<ButtonObject> hero = new ArrayList<ButtonObject>();

	private ArrayList<ButtonObject> Buybutton = new ArrayList<ButtonObject>();
	public ButtonObject[] Button = new ButtonObject[10];
	public GL10 mGL = null; // OpenGL 객체
	public Context MainContext;
	public Random MyRand = new Random(); // 랜덤 발생기
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public UITool OptionUI = new UITool();

	public UITool ShopUI = new UITool();
	boolean volumON = true;
	boolean vibeON = true;
	int volum = 100;
	int volumx = 260;
	final int maxvolum = 100;

	private Sprite buttonSpr = new Sprite();
	private ItemSet ItemSet;
	public ArrayList<ButtonObject> ninja1Item;
	public ArrayList<ButtonObject> ninja2Item;
	public ArrayList<ButtonObject> ninja3Item;
	public ArrayList<ButtonObject> nowItem;
	public ArrayList<ButtonObject> Item;
	public UI() {
	}

	private Sprite heroSpr[] = new Sprite[3];

	public UI(Context context, GameInfo info, GL10 mGL) {// 로드게임에서 호출하면된다.
		this.MainContext = context;
		this.gInfo = info;
		this.mGL = mGL;
		nowItem=new  ArrayList<ButtonObject>();
		ninja1Item=new  ArrayList<ButtonObject>();
		ninja2Item=new  ArrayList<ButtonObject>();
		ninja3Item=new  ArrayList<ButtonObject>();
		Item=new  ArrayList<ButtonObject>();

		for (int i = 0; i < 3; i++) // 주인공 스프라이트 생성
		{
			heroSpr[i] = new Sprite();
		}
		heroSpr[0].LoadSprite(mGL, MainContext, "ninja1.spr");
		heroSpr[1].LoadSprite(mGL, MainContext, "ninja2.spr");
		heroSpr[2].LoadSprite(mGL, MainContext, "ninja1.spr");

		buttonSpr.LoadSprite(mGL, MainContext, "button.spr");
		for (int i = 0; i < Button.length; i++)
			Button[i] = new ButtonObject();
		TotalButton = 0;
		for (int i = 0; i < 3; i++) {
			Button[TotalButton++].SetButton(buttonSpr, 0, 0, 80, 220, 7);
			Button[TotalButton - 1].x += GameInfo.SinTBL2[i * 1200] * 160;
			Button[TotalButton - 1].y -= GameInfo.CosTBL2[i * 1200] * 30;
			Button[TotalButton - 1].direct = i * 1200;
			ButtonObject temp2 = new ButtonObject();
			temp2.SetButton(heroSpr[i], 0, 0, Button[TotalButton - 1].x, Button[TotalButton - 1].y, 0);
			temp2.SetLinkTo(Button[TotalButton - 1]);

			hero.add(temp2);
		}
		buttonSpr.LoadSprite(mGL, MainContext, "button.spr");
		OptionUI.LoadUI(mGL, MainContext, "optionsui.UI");
		ShopUI.LoadUI(mGL, MainContext, "shop.ui");
		itemSpr.LoadSprite(mGL, MainContext, "playui.spr"); // 18~77 까지 아이템
		shopSpr.LoadSprite(mGL, MainContext, "shop.spr"); // 18~77 까지 아이템
		for (int i = 0; i < 20; i++) {
			ButtonObject temp = new ButtonObject();
			temp.SetButton(buttonSpr, ButtonType.TYPE_UPDOWN_LIST, 0, 85, 600 + i * 60, 0); // 리스트뷰
			temp.trans = 0;
			Button2.add(temp);

		}
		for (int i = 0; i < 20; i++) {
			ButtonObject temp = new ButtonObject();
			temp.SetButton(shopSpr, ButtonType.TYPE_UPDOWN_LIST, 0, 224, 100 + i * 120, 10); // 리스트뷰
			Buybutton.add(temp);
			ButtonObject temp1 = new ButtonObject();
			temp1.SetButton(shopSpr, ButtonType.TYPE_ONE_CLICK, 1, 384, 130 + i * 120, 15); // 리스트뷰
			temp1.SetText(0, -100, 0, 255, 255,0, 20, "1000", 100, 30);
			temp1.SetText(1, -20, 0, 255, 255, 0, 20, "G", 100, 30);
			temp1.SetLinkTo(temp);
			Buybutton.add(temp1);
			temp1 = new ButtonObject();
			temp1.SetButton(shopSpr, ButtonType.TYPE_ONE_CLICK, 1, 94, 100 + i * 120, 9); // 리스트뷰
			temp1.SetLinkTo(temp);
			Buybutton.add(temp1);
			temp1 = new ButtonObject();
			temp1.SetButton(itemSpr, ButtonType.TYPE_ONE_CLICK, 0, 94, 100 + i * 120, 10);
			temp1.frame = i;
			temp1.SetLinkTo(temp);
			Buybutton.add(temp1);
		}

		gInfo.SetListView(35, 30, 450, 500, 40, Buybutton);
		gInfo.SetListView(62, 580, 400, 780, 30, Button2);
	}
	public void SetItem(){
		//ninja item setting
		for(int i=0;i<UserData.Item.size();i++){
			ButtonObject temp=new ButtonObject();
			temp.SetButton(itemSpr, ButtonType.TYPE_ONE_CLICK, 0, 0.5f,0.5f, 10);
			temp.frame=UserData.Item.get(i).itemcode;
			temp.SetZoom(gInfo, 0.5f, 0.5f);
			Item.add(temp);
		}
		for(int i=0;i<UserData.Item1.size();i++){
			ButtonObject temp=new ButtonObject();
			temp.SetButton(itemSpr, ButtonType.TYPE_ONE_CLICK, 0, 0.5f,0.5f, 10);
			temp.frame=UserData.Item1.get(i).itemcode;
			temp.SetZoom(gInfo, 0.5f, 0.5f);
			ninja1Item.add(temp);
		}
		for(int i=0;i<UserData.Item2.size();i++){
			ButtonObject temp=new ButtonObject();
			temp.SetButton(itemSpr, ButtonType.TYPE_ONE_CLICK, 0, 0.5f,0.5f, 10);
			temp.frame=UserData.Item2.get(i).itemcode;
			temp.SetZoom(gInfo, 0.5f, 0.5f);
			ninja2Item.add(temp);
		}
		for(int i=0;i<UserData.Item3.size();i++){
			ButtonObject temp=new ButtonObject();
			temp.SetButton(itemSpr, ButtonType.TYPE_ONE_CLICK, 0, 0.5f,0.5f, 10);
			temp.frame=UserData.Item3.get(i).itemcode;
			temp.SetZoom(gInfo, 0.5f, 0.5f);
			ninja3Item.add(temp);
		}
	}

	void SetHero() {


		for (int i = 0; i < nowItem.size(); i++) {
			itemSpr.PutAni(gInfo, (int) nowItem.get(i).x, (int) nowItem.get(i).y, 9, 0);

			if (nowItem.get(i).dead)
				nowItem.remove(i--);
			else {
				nowItem.get(i).DrawSprite(mGL, 0, gInfo, GameInfo.font);
			}
		}
		for (int i = nowItem.size(); i < 12; i++) {
			int x = 85 + (i % 6) * 55;
			int y = 308 + (int) (i / 6) * 60;
			itemSpr.PutAni(gInfo, x, y, 9, 0);
			buttonSpr.PutAni(gInfo, x, y, 20, 0);

		}
		{// 히어로 소팅
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
	}

	private void ShopUICheck() {
//캐릭터 관리, 무기 , 방어구 소모품, 신기한 물건
		for (int i = 1; i <= 5; i++) {
			if (ShopUI.UIList.get(i).click == ButtonType.STATE_DOWN_BUTTON) {
				Shopmode = i;
				for (int i1 = 1; i1 <= 5; i1++) {
					if (i1 != i) {
						ShopUI.UIList.get(i1).frame = 1;
					} else {
						for (int j = 0; j < nowItem.size(); j++) {
							nowItem.get(j).ResetButton();
						}
						for (int j1 = 0; j1 < Item.size(); j1++) {
						Item.get(j1).ResetButton();
						}

						ShopUI.UIList.get(i1).frame = 0;
						GGumul(0.03f, 0.03f, ShopUI.UIList.get(i1), 8, 1f);
						ShopUI.DeleteLastGroup(gInfo);
						Settingtimer = System.currentTimeMillis();
						if (Shopmode == 1) {
							ShopUI.AddGroup(1, 1);
						} else {
							ShopUI.AddGroup(2, 1);
						}
					}
				}

				ShopUI.UIList.get(i).click = ButtonType.STATE_NON_BUTTON;
			}
			if (ShopUI.UIList.get(i).move >= 1) {
				GGumul(0.03f, 0.03f, ShopUI.UIList.get(i), 8, 1f);
			}
		}
		if (Shopmode == 1) {
			ShopUI.UIList.get(10).AddFrameLoop(0.35f);
			ShopUI.UIList.get(12).AddFrameLoop(0.35f);

		}
		int Data[] = new int[Item.size()], Sort[] = new int[Item.size()];

		for (int j = 0; j < Item.size(); j++) {
			Data[j] = j;
			Sort[j] = (int)Item.get(j).frame;
		}
		GameInfo.Sort(Data, Sort, 0,Item.size() - 1);
		for (int k = 0; k <Item.size(); k++) {
			int i = Data[k];
			Item.get(i).x = 85 + (k % 6) * 55;
			Item.get(i).y = Button2.get((int) (k / 6)).y;
			Item.get(i).SetLinkTo(Button2.get((int) (k / 6)));
			if (Item.get(i).click == ButtonType.STATE_CLK_BUTTON) {
				Item.get(i).direct = 1;
				Item.get(i).ResetButton();
				for (int j = 0; j < Item.size(); j++) {

					if (j != i) {
						Item.get(j).direct = 0;
						Item.get(j).move = 0;
						if (Item.get(j).frame > 0) {
							Item.get(j).SetZoom(gInfo, 0.5f, 0.5f);
						}
					}
				}
			}

			if (Item.get(i).direct == 1) {
				if (nowItem.size() < 12) {
					ButtonObject temp1 = new ButtonObject();
					temp1.CopyFrom(Item.get(i));
					temp1.SetZoom(gInfo, 0.5f, 0.5f);
					nowItem.add(temp1);

					Item.get(i).dead = true;

				}
			}
		}

		int Data1[] = new int[nowItem.size()], Sort1[] = new int[nowItem.size()];

		for (int j = 0; j < nowItem.size(); j++) {
			Data1[j] = j;
			Sort1[j] = (int)nowItem.get(j).frame;
		}
		if (nowItem.size() > 1) {
			GameInfo.Sort(Data1, Sort1, 0, nowItem.size() - 1);
		}
		for (int k = 0; k < nowItem.size(); k++) {
			int i = Data1[k];
			nowItem.get(i).x = 85 + (k % 6) * 55;
			nowItem.get(i).y = 308 +  (k / 6) * 60;

			if (nowItem.get(i).click == ButtonType.STATE_CLK_BUTTON) {
				nowItem.get(i).direct = 1;
				nowItem.get(i).ResetButton();
				for (int j = 0; j < nowItem.size(); j++) {
					if (j != i) {
						nowItem.get(j).direct = 0;
						nowItem.get(j).move = 0;
						if (nowItem.get(j).frame> 0) {
							nowItem.get(j).SetZoom(gInfo, 0.5f, 0.5f);
						}
					}
				}
			}
			if (nowItem.get(i).direct == 1) { //아이템 판매
				ButtonObject temp = new ButtonObject();
				temp.CopyFrom(nowItem.get(i));
				temp.SetZoom(gInfo, 0.5f, 0.5f);
				temp.SetLinkTo(Button2.get(Item.size() / 6));
				Item.add(temp);

				nowItem.get(i).dead = true;

			}

		}
	}

	void OptionUICheck() {
		for (int i = 0; i < OptionUI.UIList.size(); i++) // 모든 UI의 버튼 터치 상태를
		// 체크하기 위한 루프
		{
			int click = OptionUI.UIList.get(i).click;
			switch (OptionUI.UIList.get(i).index) {
			case 4: // 음량

				if (OptionUI.UIList.get(i).frame == 1) {
					if (volumON == true) {
						volumx = (int) OptionUI.UIList.get(9).x;
						volumON = false;
					}
					OptionUI.UIList.get(9).x = OptionUI.UIList.get(5).x1 + 5;
				} else {
					if (volumON == false) {
						volumON = true;
						OptionUI.UIList.get(9).x = volumx;
					}

				}
				break;

			case 6: // 볼륨
				volum = (int) ((OptionUI.UIList.get(9).x - OptionUI.UIList.get(5).x1 + 5)
						/ (OptionUI.UIList.get(5).x2 - OptionUI.UIList.get(5).x1 - 10) * 100);
				OptionUI.UIList.get(i).energy = volum - 5;
				break;

			case 7: // 진동
				if (click == ButtonType.STATE_CLK_BUTTON) {
					if (OptionUI.UIList.get(8).frame == 0) {
						OptionUI.UIList.get(8).frame = 1;
						vibeON = false;
					} else {
						OptionUI.UIList.get(8).frame = 0;
						vibeON = true;
					}
				}
				OptionUI.UIList.get(i).ResetButton();
				break;

			case 8: // 진동

				if (click == ButtonType.STATE_DOWN_BUTTON) {
					if (OptionUI.UIList.get(i).frame == 0) {
						vibeON = false;
					} else {
						vibeON = true;
					}
				}
				break;

			case 9: // 스위치

				if (click == ButtonType.STATE_DOWN_BUTTON && volumON) {
					if (TouchX > OptionUI.UIList.get(5).x1 + 5 && TouchX < OptionUI.UIList.get(5).x2 - 5)
						OptionUI.UIList.get(i).x = TouchX;
					else {
						OptionUI.UIList.get(i).x = TouchX < OptionUI.UIList.get(5).x1 + 5
								? OptionUI.UIList.get(5).x1 + 5 : OptionUI.UIList.get(5).x2 - 5;
					}

					volumx = (int) OptionUI.UIList.get(9).x;
				} else if (volumON) {
					// 터치되지않았을경우.
					OptionUI.UIList.get(i).x = volumx;
				}
				break;
			}

		}

	}

	public void UpdateGame() {

		for (int i = 0; i < TotalButton; i++) {
			/**
			 * x, y값은 중심축이 되는 좌표에서 얼만큼 멀어졌나를 계산하는 것이므로 항상 원래 좌표인 ox, oy에서 +,-를
			 * 해줘야 한다.
			 */
			Button[i].x = Button[i].ox + (GameInfo.SinTBL2[Button[i].direct] * 100);
			Button[i].y = Button[i].oy - (GameInfo.CosTBL2[Button[i].direct] * 30);

			/**
			 * SwipeStatus가 0이 아니면 왼쪽이나 오른쪽으로 스와이프한 상태이므로 direct 값에 SwipeStatus에
			 * 40을 곱한 값을 더해줘서 스와이프 방향에 따라 각도가 변하도록 해준다. 모든 버튼이 600 만큼 회전해야 다음
			 * 닌가가 나타나므로 마지막 버튼인 경우에만 각도의 % 600 값이 0인지를 체크해서 SwipeStatus를 0으로
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
			Button[i].SetZoom(gInfo, scale, scale);
			Button[i].trans = 0;
			hero.get(i).SetZoom(gInfo, scale * 1.5f, scale * 1.5f);
			if (scale > 0.68) {
				heroType = i;
				switch (i) {
				case 0:
					nowItem=ninja1Item;

					break;

				case 1:

					nowItem=ninja2Item;
					break;

				case 2:
					nowItem=ninja3Item;

					break;
				}
				hero.get(i).trans = 1f;
				hero.get(i).AddFrameLoop(0.2f); // 맨앞에있는애만 애니메이션
			} else {
				hero.get(i).trans = 0;
				hero.get(i).y += 120; // 박스에서 쳐져보이는 것을 수정함.
			}
		}
	}

	public void DoUIMethod() {
		GameInfo.font.BeginFont(gInfo);
		UpdateGame();

		if (!OptionUI.UIList.isEmpty()) {
			OptionUICheck();

			OptionUI.Draw(mGL, gInfo, GameInfo.font);
		}
		if (!ShopUI.UIList.isEmpty()) {
			ShopUICheck();
		}
		ShopUI.Draw(mGL, gInfo, GameInfo.font);
		if (System.currentTimeMillis() - Settingtimer >= 300 && Shopmode > 0) {
			for (int i = 0; i < Item.size(); i++) {

				if (Item.get(i).dead) {
					Item.remove(i--);
					Button3.remove(Button3.size() - 1);
				} else {
					if (Button3.size() < i + 2) {
						ButtonObject temp = new ButtonObject();
						temp.SetButton(itemSpr, ButtonType.TYPE_POPUP, 0, 1, 1, 9);
						Button3.add(temp);
					}

					Button3.get(i).x =Item.get(i).x;
					Button3.get(i).y =Item.get(i).y;
					Button3.get(i).SetLinkTo(Item.get(i).target);
					Button3.get(i).DrawSprite(mGL, 0, gInfo, GameInfo.font);
					Item.get(i).DrawSprite(mGL, 0, gInfo, GameInfo.font);
				}
			}
			if (Shopmode == 1) {
				SetHero();
			}
			for (int i = 0; i < Button2.size(); i++) {
				Button2.get(i).DrawSprite(mGL, 0, gInfo, GameInfo.font);
			}
			if (Shopmode > 1) {
				for (int i = 0; i < Buybutton.size(); i++) {
					Buybutton.get(i).DrawSprite(mGL, 0, gInfo, GameInfo.font);
				}
			}
		}

		GameInfo.font.EndFont(gInfo);
	}

	public void PushButton(boolean push, float TouchX, float TouchY) {
		OptionUI.Touch(gInfo, (int) TouchX, (int) TouchY, push);
		if (!ShopUI.UIList.isEmpty()) {
			for (int i = 1; i <= 5; i++) {
				if (ShopUI.UIList.get(i).CheckPos((int) TouchX, (int) TouchY)) {
					if (ShopUI.UIList.get(i).click == ButtonType.STATE_DOWN_BUTTON) {

					} else {
						ShopUI.UIList.get(i).click = ButtonType.STATE_DOWN_BUTTON;
					}
				}
			}
		}
		if (push) {
			if (TouchX > 48 && TouchY > 0 && TouchX < 340 && TouchY < 250) // 케릭터
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
		for (int i = 0; i < Item.size(); i++) {

			Item.get(i).CheckButton(gInfo, push, TouchX, TouchY);

		}
		if(Shopmode==1){
		for (int i = 0; i < nowItem.size(); i++) {

			nowItem.get(i).CheckButton(gInfo, push, TouchX, TouchY);
		}}


	}

	void GGumul(float x, float y, ButtonObject buttonObject, int delay, float size) {
		if (buttonObject.move == 0) {
			int rnd = 0;// MyRand.nextInt(3);

			if (rnd == 0) {
				buttonObject.power = 200; // 점프할 중력값
				buttonObject.move = 1; // move를 1로 셋팅
			}
		} else if (buttonObject.move == 1) // 가로를 축소하고, 세로를 길게 늘린다.
		{
			buttonObject.Zoom(gInfo, -x, y);
			if (++buttonObject.delay >= delay) {
				buttonObject.delay = 0;
				buttonObject.move = 2;
			} // 8회 반복한다.
		} else if (buttonObject.move == 2) // 캐릭터가 세로로 길어진 상태이므로, 다시 원래 상태로 돌린다.
											// 가로를
											// 확대하고 세로를 축소한다.
		{
			buttonObject.Zoom(gInfo, x, -y);
			if (++buttonObject.delay >= delay * 2) {
				buttonObject.delay = 0;
				buttonObject.move = 3;
			} // 가로를 16회까지 늘려서 옆으로 길쭉해진 느낌을 내게 한다.
		} else if (buttonObject.move == 3) // move가 2일때쯤 16회 반복한 탓에 땅에 짜부된 느낌이
											// 나게 된다.
											// 세로로 길쭉하게 8회 반복해서 원상 복구시킨다.
		{
			buttonObject.Zoom(gInfo, -x, y);

			if (++buttonObject.delay >= delay) {
				buttonObject.delay = 0;
				buttonObject.SetZoom(gInfo, size, size); // 캐릭터를 다시 원래 비율로 되돌린다.
				buttonObject.move = 0;
			}
		}

		if (buttonObject.move >= 1) // move가 1 이상이면 점프한 상태이므로 중력값에 따라 점프시킨다. 최초
									// 생성시
		// 위치인 oy보다 커지면 y좌표를 리셋한다.
		{
			// buttonObject.y -= buttonObject.power / 100;
			// buttonObject.power -= 25;
			// if ( buttonObject.y >= buttonObject.oy ) buttonObject.y =
			// buttonObject.oy;
		}

	}

	public void Deletepage() {
		OptionUI.DeleteLastGroup(gInfo);
		ShopUI.DeleteLastGroup(gInfo);

		ShopUI.DeleteLastGroup(gInfo);
	}
}
