package GamemodeClass;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import AssistClass.AudioVibeEffect;
import AssistClass.GuidedMissile;
import AssistClass.UserData;
import bayaba.engine.lib.ButtonObject;
import bayaba.engine.lib.ButtonType;
import bayaba.engine.lib.Font;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;
import bayaba.engine.lib.UITool;
import k.com.ninjabeta.GameActivity;
import k.com.ninjabeta.GameMain;
import k.com.ninjabeta.Mons;
import k.com.ninjabeta.Ninja;

public class InGame {
	static class UIButtonClass {
		static class Group0 {
			static final int CHECKBOX_000 = 0;
			static final int PREGRESS_UP_001 = 1;
			static final int ONE_CLICK_002 = 2;
			static final int POPUP_003 = 3;
			static final int POPUP_004 = 4;
		}

		static class Group1 {
			static final int POPUP_000 = 5;
			static final int POPUP_001 = 6;
			static final int POPUP_002 = 7;
			static final int ONE_CLICK_003 = 8;
			static final int PREGRESS_RIGHT_004 = 9;
			static final int PREGRESS_RIGHT_005 = 10;
			static final int POPUP_006 = 11;
			static final int POPUP_007 = 12;
			static final int POPUP_008 = 13;
			static final int POPUP_009 = 14;
			static final int POPUP_010 = 15;
			static final int POPUP_011 = 16;
			static final int POPUP_012 = 17;
			static final int POPUP_013 = 18;
			static final int POPUP_014 = 19;
			static final int POPUP_015 = 20;
			static final int POPUP_016 = 21;
			static final int POPUP_017 = 22;
		}

	}

	private Font font;

	public InGame() {
	}

	public InGame(Context context, GameInfo info, GL10 mGL, Ninja myhero, Font font) {
		this.MainContext = context;
		this.gInfo = info;
		this.mGL = mGL;
		this.myHero = myhero;
		this.font = font;
		for (int i = 0; i < 3; i++) // 주인공 스프라이트 생성
		{
			heroSpr[i] = new Sprite();
		}
		// pig_action_spr = new Sprite();
		vibe = new AudioVibeEffect(MainContext, 0);
		explosionSpr1.LoadSprite(mGL, MainContext, "explosion.spr");
		backSpr.LoadSprite(mGL, MainContext, "background.spr"); // 배경 스프라이트
		heroSpr[0].LoadSprite(mGL, MainContext, "ninja1.spr"); // 주인공 스프라이트
		heroSpr[1].LoadSprite(mGL, MainContext, "ninja2.spr");
		heroSpr[2].LoadSprite(mGL, MainContext, "ninja1.spr");
		monsSpr.LoadSprite(mGL, MainContext, "bee spritev2.spr"); // 몬스터 스프라이트
		dustSpr.LoadSprite(mGL, MainContext, "dust.spr"); // 폭발 스프라이트
		score.LoadSprite(mGL, MainContext, "score.spr");
		bombSpr.LoadSprite(mGL, MainContext, "bolloon.spr");
		buttonSpr.LoadSprite(mGL, MainContext, "button.spr");
		spatk1Spr.LoadSprite(mGL, MainContext, "spatk1.spr");
		itemSpr.LoadSprite(mGL, MainContext, "playui.spr");
		bullet.LoadSprite(mGL, MainContext, "BulletCollection.spr");
		for (int i = 0; i < 3; i++) // 배경 3장을 연속되게끔 생성한다.
		{
			GameObject temp = new GameObject();
			temp.SetObject(backSpr, 0, 0, 240, -400+ (i * 640), 0, 0); // 배경 1장의
																		// 세로
																		// 길이가
																		// 640이다.
			Game.add(temp);
		}

		spatk1.SetObject(spatk1Spr, 0, 0, myHero.x, myHero.y, 0, 0);
		button.SetObject(buttonSpr, 0, 0, 430, 750, 3, 0);

	}

	GameObject spatk1 = new GameObject();
	public UITool MainUI = new UITool();
	public UITool CoinUI[] = new UITool[5];
	public boolean pauseOn = false;
	public GL10 mGL = null; // OpenGL 객체
	public Context MainContext;
	public Random MyRand = new Random(); // 랜덤 발생기
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.

	Sprite itemSpr = new Sprite();
	Sprite spatk1Spr = new Sprite();
	Sprite backSpr = new Sprite();
	Sprite heroSpr[] = new Sprite[3];
	Sprite monsSpr = new Sprite();
	Sprite dustSpr = new Sprite();
	Sprite score = new Sprite();
	Sprite explosionSpr1 = new Sprite();
	Sprite bombSpr = new Sprite();
	Sprite buttonSpr = new Sprite();
	Sprite pig1atkSpr = new Sprite();
	Sprite bullet = new Sprite();
	// 각 오브젝트들을 처리할 어레이리스트
	ArrayList<GameObject> Game = new ArrayList<GameObject>();
	ArrayList<Mons> Monster = new ArrayList<Mons>();

	ArrayList<GameObject> Crashck = new ArrayList<GameObject>();
	ArrayList<GameObject> Bullet = new ArrayList<GameObject>();
	ArrayList<GameObject> EnemyBullet = new ArrayList<GameObject>();
	ArrayList<GameObject> Effect = new ArrayList<GameObject>();
	ArrayList<ButtonObject> Item;
	// 주인공 오브젝트
	public Ninja myHero;
	public GameObject button = new GameObject();
	private AudioVibeEffect vibe;

	public void newGame(Ninja hero)// 초기값인자로 넘겨주면되겠다.
	{
		MonsterTimer = System.currentTimeMillis() + 1000; // 몬스터 리젠 타이머
		MainUI = new UITool();
		Game = new ArrayList<GameObject>();
		Item =new ArrayList<ButtonObject>();
		Monster = new ArrayList<Mons>();
		Bullet = new ArrayList<GameObject>();
		EnemyBullet = new ArrayList<GameObject>();
		Effect = new ArrayList<GameObject>();
		myHero = hero;
		button = new GameObject();
		for (int i = 0; i < 3; i++) // 배경 3장을 연속되게끔 생성한다.
		{
			GameObject temp = new GameObject();
			temp.SetObject(backSpr, 0, 0, 240, 400 - (i * 640), 0, 0); // 배경 1장의
																		// 세로
																		// 길이가
																		// 640이다.
			Game.add(temp);
		}

		MainUI.LoadUI(mGL, MainContext, "playui.ui");
		MainUI.AddGroup(1, 0);
		MainUI.AddGroup(0, 0);
		MainUI.Get(0, 1).energy = 0;
		myHero.SetObject(heroSpr[myHero.charactertype], 0, 0, 240, 700, 0, 0);
		switch (myHero.charactertype) {
			//TODO
		case 0:
			for(int i=0;i<UserData.Item1.size();i++){
				ButtonObject temp=new ButtonObject();
				temp.SetButton(itemSpr, ButtonType.TYPE_ONE_CLICK, 0, 0.5f,0.5f, 10);
				temp.frame=UserData.Item1.get(i).itemcode;
				temp.SetZoom(gInfo, 0.5f, 0.5f);
				Item.add(temp);
			}
			break;
		case 1:	for(int i=0;i<UserData.Item2.size();i++){
			ButtonObject temp=new ButtonObject();
			temp.SetButton(itemSpr, ButtonType.TYPE_ONE_CLICK, 0, 0.5f,0.5f, 10);
			temp.frame=UserData.Item2.get(i).itemcode;
			temp.SetZoom(gInfo, 0.5f, 0.5f);
			Item.add(temp);
		}
			break;
		case 2:	for(int i=0;i<UserData.Item3.size();i++){
			ButtonObject temp=new ButtonObject();
			temp.SetButton(itemSpr, ButtonType.TYPE_ONE_CLICK, 0, 0.5f,0.5f, 10);
			temp.frame=UserData.Item3.get(i).itemcode;
			temp.SetZoom(gInfo, 0.5f, 0.5f);
			Item.add(temp);
		}
			break;
		}
	}

	void MakeBullet(int shotType) {
		if (shotType == 0) {
			if (System.currentTimeMillis() - myHero.timer >= 300) {
				if (myHero.frame >= 3) {
					for (int i = 0; i < myHero.multibullet; i++) {

						GameObject temp = new GameObject();
						if (i == 0) {
							temp.SetObject(buttonSpr, 0, 0, myHero.x, myHero.y - 35, 13, 0);
							temp.angle = MyRand.nextInt(360);
						} else {
							temp.SetObject(buttonSpr, 0, 0, myHero.x + 50 + ((i - (myHero.multibullet + 1) / 2) * 50),
									myHero.y - 40, 13, 0);
							temp.angle = MyRand.nextInt(360);
						}

						temp.SetZoom(gInfo, 0.5f, 0.5f);
						Bullet.add(temp);
					}
					myHero.timer = System.currentTimeMillis();
				}
			}
		} // 일반공격
		if (shotType == 1) {
			GameObject temp = new GameObject();
			temp.SetObject(buttonSpr, 1, 0, myHero.x, myHero.y + 50, myHero.chargeshottype, 0); // 특수탄

			temp.SetZoom(gInfo, 1f, 1f);
			Bullet.add(temp);
			for (int i = 0; i < myHero.multibullet * 3; i++) {
				GameObject temp1 = new GameObject();

				temp1.SetObject(buttonSpr, 0, 0, myHero.x, myHero.y - 35, 13, 0);
				temp1.angle = MyRand.nextInt(360);
				temp1.direct = -30 + (30 / myHero.multibullet) * i;
				temp1.SetZoom(gInfo, 0.5f, 0.5f);
				Bullet.add(temp1);
			}
		}

		if (shotType == 2) {
			GameObject temp = new GameObject();
			temp.SetObject(buttonSpr, 1, 0, myHero.x, myHero.y + 50, myHero.chargeshottype, 0); // 특수탄
			temp.angle = 135;
			temp.SetZoom(gInfo, 2f, 2f);
			Bullet.add(temp);
		}
	}

	void StageUp() {
		GameObject temp = new GameObject();
		temp.SetObject(buttonSpr, 3, 0, 210, 400, 16, 0);
		Effect.add(temp);

	}

	void MakeEBullet() {
		for (int i = 0; i < Monster.size(); i++) // 몬스터 처리
		{
			if (Monster.get(i).type == 0) {
				if (Monster.get(i).timer == 0) { // 처음생성시
					if (Monster.get(i).motion == 1) {
						MakeEBullet1(Monster.get(i));
					} else if (System.currentTimeMillis() - MonsterTimer >= 300) // 500ms마다
					{
						Monster.get(i).motion = 1;
						Monster.get(i).frame = 0;
					}
				} else {
					if (Monster.get(i).motion == 1) {
						MakeEBullet1(Monster.get(i));
					} else if (System.currentTimeMillis() - Monster.get(i).timer >= 4000) {
						Monster.get(i).motion = 1;
						Monster.get(i).frame = 0;
					}
				}
			} else if (Monster.get(i).type == 1) // 일반몬스터가 아닐때
			{
				if (Monster.get(i).timer == 0) { // 처음생성시
					if (System.currentTimeMillis() - MonsterTimer >= 300) // 500ms마다
					{

						Monster.get(i).motion = 1;
						if (Monster.get(i).frame >= 8) {
							GameObject temp = new GameObject();
							temp.type = 1; // 유도탄
							temp.SetObject(bombSpr, temp.type, 0, Monster.get(i).x - 10, Monster.get(i).y - 35, 0, 0); // 몬스터
							temp.vertical = new GuidedMissile(temp, myHero).calcAngle();

							EnemyBullet.add(temp);
							Monster.get(i).timer = System.currentTimeMillis() + MyRand.nextInt(1000);
						}
					}
				} else {
					if (System.currentTimeMillis() - Monster.get(i).timer >= 3000) {
						Monster.get(i).motion = 1;
						if (Monster.get(i).frame >= 8) {
							GameObject temp = new GameObject();
							temp.type = 1;// 유도탄
							temp.SetObject(bombSpr, temp.type, 0, Monster.get(i).x - 10, Monster.get(i).y - 35, 0, 0); // 몬스터
							temp.vertical = new GuidedMissile(temp, myHero).calcAngle();
							EnemyBullet.add(temp);
							Monster.get(i).timer = System.currentTimeMillis() + MyRand.nextInt(1000);
						}
					}
				}
			}
		}
	}

	void MakeEBullet1(Mons monster) {
		if (monster.frame >= 6 && monster.frame < 7 && System.currentTimeMillis() - monster.timer > 100) {
			GameObject temp = new GameObject();
			temp.type = 0; // 일반탄
			temp.SetObject(bombSpr, temp.type, 0, monster.x - 10, monster.y - 35, temp.type, 0); // 몬스터
			temp.vertical = new GuidedMissile(temp, myHero).calcAngle() + MyRand.nextInt(20) - 10;
			temp.angle = temp.vertical;
			temp.direct = monster.bulletroll;
			temp.move = 0;
			EnemyBullet.add(temp);
			monster.timer = System.currentTimeMillis() + MyRand.nextInt(500) + 500;
		}
	}

	long MonsterTimer = 0;// 몬스터 리젠 타이머

	void MakeBonus() {
		for (int i = 1; i < 11; i++) {
			for (int k = 0; k < 4 + (i % 2); k++) {
				MakeCoin(i, k);
			}

		}

	}

	private void MakeCoin(int i, int k) {
		Mons temp = new Mons();
		temp.SetObject(buttonSpr, 4, 0, 114 + k * 84 - (i % 2) * 42, -100 + i * -150, 18, MyRand.nextInt(6)); // 보너스동전

		Monster.add(temp);
	}

	void MakeMonster() {

		int type = myHero.stair % 4;

		if (!Mons.fullflag) {
			if (myHero.roundnonhitted%2==0) {
				myHero.stage--;
				MakeBonus();
				Mons.fullflag = true;
			} else if (System.currentTimeMillis() - MonsterTimer >= 1000) // 7.5초마다
																			// 한번씩
			// 5마리씩
			// //다잡으면
			// 나오도록
			// 설정기능도
			// 만들어야겟다
			// 생성한다.
			{
				if (myHero.stair % 20 == 0 && myHero.stair >= 5) {
					Mons temp = new Mons();
					temp.SetObject(monsSpr, 1, 0, 240, -200, 0, 0);
					temp.SetZoom(gInfo, 1.25f, 1.25f);
					temp.energy = myHero.stair * 100 + 10;
					temp.attack = 0;
					temp.timer = 0;
					Monster.add(temp);
					GameObject temp2 = new GameObject();
					temp2.SetObject(monsSpr, 1, 0, 240, -200, 2, 0);
					temp2.target = temp;
					temp2.SetZoom(gInfo, 1.25f, 1.25f);
					Crashck.add(temp2);
				}
				for (int i = 0; i < 1; i++) // 1마리 몬스터 생성
				{
					Mons temp = new Mons();
					temp.SetObject(monsSpr, 0, 0, 0, -200, 0, 0);
					temp.SetZoom(gInfo, 0.65f, 0.65f);
					temp.AddFrame(MyRand.nextInt(6));
					temp.SetMon(type);
					temp.energy = myHero.stair * 10 + 10;
					temp.attack = 0;
					temp.timer = 0;
					Monster.add(temp);
					GameObject temp2 = new GameObject();

					temp2.SetObject(monsSpr, 1, 0, 240, -200, 2, 0);
					temp2.target = temp;
					temp2.SetZoom(gInfo, 0.65f, 0.65f);
					Crashck.add(temp2);
				}

				myHero.stair++;
				if (type == 3) {
					Mons.fullflag = true;

				}
				// 단계값을 증가시킴. 난이도를 올린다.
				// 몬스터들의 총알을 만든다
				MonsterTimer = System.currentTimeMillis(); // 몬스터 타이머 리셋
			}

		} else {
			if (Monster.size() == 0) {
				if(myHero.roundhitteddamage==0)
				{
					myHero.roundnonhitted++;
				}

				myHero.roundhitteddamage=0;
				myHero.stage++;
				StageUp();
				Mons.fullflag = false;
			}
		}
	}

	public GameObject CheckDamage(GameObject target) //
	{
		for (int i = 0; i < Bullet.size(); i++) {
			Bullet.get(i).PutSprite(gInfo);

			if (GameInfo.CrashCheck(target, Bullet.get(i), 0, 0))
				return Bullet.get(i); // 총알과 몬스터를 충돌 체크하여 충돌한 총알을 리턴한다.
		}
		return null; // 충돌한 총알이 없는 경우
	}

	public GameObject CheckHDamage(GameObject target) { // 적군총알에 피격된 히어로 체력꼐산
		for (int i = 0; i < EnemyBullet.size(); i++) {

			if (EnemyBullet.get(i).motion != 1) { // 터진총알은 판정하지 않는다
				EnemyBullet.get(i).PutSprite(gInfo);

				if (GameInfo.CrashCheck(target, EnemyBullet.get(i), -10, -30)) {
					EnemyBullet.get(i).motion = 1;
					EnemyBullet.get(i).frame = 1;
					EnemyBullet.get(i).SetZoom(gInfo, 1.5f, 1.5f);
					return EnemyBullet.get(i); // 총알과 몬스터를 충돌 체크하여 충돌한 총알을 리턴한다.
				}
			}

		}

		return null; // 충돌한 총알이 없는 경우
	}

	public GameObject UpGreadeNinJa(GameObject target) {

		// 동영상에서 버그가 있던 부분의 수정 코드, PutSprite는 실제로 화면에 출력하지 않고 x1,y1,x2,y2 값을
		// 재계산해준다. 좀더 정밀한 충돌 판정.
		target.PutSprite(gInfo);

		if (GameInfo.CrashCheck(target, myHero, 0, -5)) {
			if (target.type == 4 || target.type == 1) // 떨어지는 동전 1 보너스동전 4
			{
				myHero.gold++;
			} else if (target.type == 2) {
				if (target.frame == 0) {
					myHero.speed++;
				}
				if (target.frame == 1) {
					if (myHero.energy < myHero.maxenergy + 10)
						myHero.energy += 10;
					else {
						myHero.energy = myHero.maxenergy;
					}

				}
				if (target.frame == 2) {
					myHero.bulletType = 1;
					myHero.damage += 5;
				}
				if (target.frame == 3) {
					myHero.bulletType = 2;
				}
				if (target.frame == 4) {
					if (myHero.multibullet < 5) {
						myHero.multibullet++;
					}
				}
			}
			return target;
		}

		return null; // 충돌한 총알이 없는 경우
	}

	public void MakeEffect(GameObject target) // 몬스터 폭발 이펙트
	{
		for (int i = 0; i < 20; i++) // 20개의 폭발 먼지를 생성한다.
		{
			GameObject temp = new GameObject();
			temp.SetObject(dustSpr, 0, 0, target.x, target.y, 0, 0);
			float scale = 0.3f + ((float) MyRand.nextInt(10) / 10); // 스케일을 30%
																	// ~ 120%까지
																	// 다양하게
																	// 만들어지게 한다.
			temp.SetZoom(gInfo, scale, scale); // 계산한 스케일로 폭발 먼지 크기를 조절한다.
			temp.direct = MyRand.nextInt(360); // 폭발 먼지가 날아갈 방향을 360도 랜덤으로
												// 계산해둔다.
			Effect.add(temp);
		}
		GameObject temp = new GameObject();
		if (MyRand.nextInt(2) == 0) {
			temp.SetObject(buttonSpr, 1, 0, target.x, target.y, 18, 0); // 동전
																		// 오브젝트를
		} else // 총알업글
		{
			temp.SetObject(bullet, 2, 0, target.x, target.y, 0, MyRand.nextInt(5)); // 타입은
																					// 2임.
			temp.SetZoom(gInfo, 3.0f, 3.0f);
		}
		temp.power = 1000; // 동전이 점프할 파워, 값이 적을수록 낮게 점프한다.
		Effect.add(temp);
	}

	public void MakeCEffect(GameObject target) // 피격이펙트
	{
		for (int i = 0; i < 2; i++) // 2개의 폭발 먼지를 생성한다.
		{
			GameObject temp = new GameObject();
			temp.SetObject(explosionSpr1, 0, 0, target.x, target.y, 0, 0);
			float scale = 0.6f + ((float) MyRand.nextInt(3) / 10); // 스케일을 60%
			temp.energy = 30;// ~ 80%까지
								// 다양하게
								// 만들어지게 한다.
			temp.SetZoom(gInfo, scale, scale); // 계산한 스케일로 폭발 먼지 크기를 조절한다.
			Effect.add(temp);
		}
	}

	public void ChargeShot() {

		if (myHero.chargeShotflag) {
			if (System.currentTimeMillis() - myHero.chargeShottimer > 900) {

				if (myHero.chargeshottype == 13)
					MakeBullet(1);
				if (myHero.chargeshottype == 14)
					MakeBullet(2);
				if (myHero.chargeshottype == 15) {
					myHero.sldenergy = myHero.maxsldenergy;
				}
				myHero.chargeShotflag = false;
				MainUI.Get(0, 1).energy = 0;
				spatk1.frame = 0;
				myHero.motion = 0;
			} else {
				MainUI.Get(0, 1).energy = (System.currentTimeMillis() - myHero.chargeShottimer) / 9;
				if (myHero.frame >= 1 && myHero.frame <= 15 && myHero.motion == 1) {
					spatk1.frame = (myHero.frame - 1) % 8;

					spatk1.trans = 1f;
				}
				if (myHero.chargeshottype == 15) {
					if (myHero.sldenergy
							+ (System.currentTimeMillis() - myHero.chargeShottimer) / 9 < myHero.maxsldenergy) {
						myHero.chargingsldenergy = (System.currentTimeMillis() - myHero.chargeShottimer) / 9;
					}
				}
			}
		}
	}

	public void update() {
		MainUI.Get(0, 10).energy = myHero.sldenergy / myHero.maxsldenergy * 100;
		MainUI.Get(0, 9).energy = myHero.energy / myHero.maxenergy * 100;
		if (MainUI.Get(0, 2).click == ButtonType.STATE_CLK_BUTTON) {

			if (!myHero.chargeShotflag) {
				myHero.chargeShottimer = System.currentTimeMillis();
				myHero.motion = 1;
				myHero.frame = 0;
				myHero.chargeShotflag = true;
				ChargeShot();
				MainUI.Get(0, 2).ResetButton();
			}
		}
		if (MainUI.Get(0, 8).click == ButtonType.STATE_CLK_BUTTON) {
			MainUI.Get(0, 8).move = MainUI.Get(0, 8).move >= 0 ? -1 : +1;
			MainUI.Get(0, 8).ResetButton();
			for (int i = 5; i <= 22; i++) {
				MainUI.Get(0, i).y += 100 * MainUI.Get(0, 8).move;
			}
		}

		myHero.scollspeed = myHero.getScollspeed();
		for (int i = 0; i < Game.size(); i++) // 배경 화면 처리
		{
			Game.get(i).y += 10;
			if (Game.get(i).y1 >=gInfo.ScreenY) // 배경 오브젝트의 최상단이 화면의 맨 아래쪽을
													// 넘어선 경우, 배경 3장 중에 맨
													// 위쪽으로 옮겨준다.
			{
				Game.get(i).y -= 1920;
			}
		}

		for (int i = 0; i < Monster.size(); i++) // 몬스터 처리
		{
			if (Monster.get(i).type == 0) {
				if (Monster.get(i).motion == 0) {
					Monster.get(i).calcxy();
					Monster.get(i).AddFrameLoop(0.4f); // 몬스터 애니메이션
				} else if (Monster.get(i).motion == 1) {

					Monster.get(i).AddFrame(0.3f);
					if (Monster.get(i).frame >= 10) {
						Monster.get(i).motion = 0; // 걷는 모션
						Monster.get(i).frame = 0;
					}
				}
			} else if (Monster.get(i).type == 4) { // 보너스동전
				if (UpGreadeNinJa(Monster.get(i)) != null) {
					Monster.get(i).dead = true;
				}
				Monster.get(i).y += 10;// myHero.speed>20?20:myHero.speed;
				Monster.get(i).AddFrameLoop(0.3f);

			} else if (Monster.get(i).type == 1) { // 보스몬스터일때
				if (Monster.get(i).motion == 0) {
					if (Monster.get(i).y < 300)
						Monster.get(i).y += 2;
					Monster.get(i).AddFrameLoop(0.2f); // 몬스터 애니메이션
				} else if (Monster.get(i).motion == 1) {

					Monster.get(i).AddFrame(0.2f);
					if (Monster.get(i).frame >= 10) {
						Monster.get(i).motion = 0; // 걷는 모션
						Monster.get(i).frame = 0;
					}
				}
			}

			// if (Monster.get(i).y < 0) // 몬스터가 위에있으면 위치를 가르켜준다.
			// {
			// // dustSpr.PutAni(gInfo, (int) Monster.get(i).x, 20, 1, 0, 1.f,
			// // 1.f, 180, 1, 1, false, false);
			// }

			if (Monster.get(i).y >= 800 || Monster.get(i).dead == true) {
				Monster.remove(i--);
			}
			// 체력을 표시한다
		}
		for (int j = 0; j < Crashck.size(); j++) // 충돌처리
		{
			Crashck.get(j).x = Crashck.get(j).target.x;
			Crashck.get(j).y = Crashck.get(j).target.y;
			Crashck.get(j).PutSprite(gInfo);
			GameObject hit = CheckDamage(Crashck.get(j)); // 몬스터와 충돌한 총알이
			// 있는지 검출한다.

			if (hit != null) // 충돌한 총알이 있다면
			{
				myHero.score += myHero.damage; // 점수를 데미지만큼 증가
				Crashck.get(j).target.energy -= myHero.damage; // 에너지를 데미지 만큼씩
				// 감소시킨다.
				MakeCEffect(hit); // 피격이펙트
				if (Crashck.get(j).target.energy <= 0) // 에너지가 다 닳았으면
				{
					MakeEffect(Crashck.get(j)); // 폭발 이펙트 생성
					Crashck.get(j).target.dead = true; // 몬스터를 삭제
					Crashck.get(j).dead = true; // 몬스터를 삭제
				}
				if (hit.type == 0) // 특수탄이 아닐경우
				{
					hit.dead = true; // 총알의 dead 값을 true로 설정해서 삭제되게 한다.
				}

			}
			if (Crashck.get(j).dead == true) {
				Crashck.remove(j--);
			}
		}
		for (int i = 0; i < Bullet.size(); i++) // 총알 처리
		{
			if (Bullet.get(i).type == 0) // 일반 총알
			{
				Bullet.get(i).MovebyAngle(gInfo, Bullet.get(i).direct, 12);

				Bullet.get(i).angle = (Bullet.get(i).angle - 4) % 360;
				Bullet.get(i).AddFrameLoop(0.4f);
			} else // 특수탄일경우
			{
				if (myHero.chargeshottype == 13) {
					Bullet.get(i).y -= 8;
					Bullet.get(i).angle = (Bullet.get(i).angle - 3) % 360;
				}
				if (myHero.chargeshottype == 14) {
					Bullet.get(i).y -= 4;

				}
			}
			// 총알이
			// 줄어들도록
			// 적당한값을
			// 설정

			if (Bullet.get(i).dead || Bullet.get(i).y2 < -250 || Bullet.get(i).energy < 0) // 몬스터와
																							// 충돌해서
			// dead값이 true가
			// 됐거나, 총알이 화면
			// 위쪽으로 벗어났으면
			// 삭제한다. //energy는 사거리로 쓸것임.
			{
				Bullet.remove(i--);
			}
		}
		for (int i = 0; i < EnemyBullet.size(); i++) // 총알 처리
		{
			if (EnemyBullet.get(i).motion != 1) {
				if (EnemyBullet.get(i).type == 0) {
					EnemyBullet.get(i).energy -= 0.01f;

					EnemyBullet.get(i).MovebyAngle(gInfo, EnemyBullet.get(i).vertical, 6.0f);
					EnemyBullet.get(i).angle = (EnemyBullet.get(i).angle + EnemyBullet.get(i).direct) % 360; // direct에는
					// 몬스터의
					// 탄회전값이들어있다
					// 이미지가 뒤집어져있는상태라서 오류가 안생기려면 해줘야됨.

				}
				if (EnemyBullet.get(i).type == 1) // 유도탄
				{
					EnemyBullet.get(i).energy -= 0.01f;
					new GuidedMissile(EnemyBullet.get(i), myHero).guidecalcAngle();
					EnemyBullet.get(i).MovebyAngle(gInfo, EnemyBullet.get(i).vertical, 4.0f);
					EnemyBullet.get(i).angle = EnemyBullet.get(i).vertical;
					// 이미지가 뒤집어져있는상태라서 오류가 안생기려면 해줘야됨.

				}

				EnemyBullet.get(i).AddFrameLoop(0.4f);
				if (EnemyBullet.get(i).dead || EnemyBullet.get(i).y2 > 850 || EnemyBullet.get(i).energy < 0) {
					EnemyBullet.remove(i--);
				}
			} else { // 터진총알

				EnemyBullet.get(i).AddFrame(0.4f);
				// EnemyBullet.get(i).Zoom(gInfo, 0.02f, 0.02f);
				if (EnemyBullet.get(i).EndFrame()) {
					EnemyBullet.remove(i--);
				}
			}

		}
		myHero.my1=(int) myHero.y;
		if (Math.abs(myHero.getGoalX() - myHero.x) > myHero.speed
				|| Math.abs(myHero.getGoalY() - myHero.y) > myHero.speed) // 목표위치에
		// 근접했으면
		// 움직이지않음
		{
			myHero.MovebyAngle(gInfo, myHero.direct = (int) myHero.GetAngle(myHero.getGoalX(), myHero.getGoalY()),
					myHero.getSpeed());
		} else {
			myHero.x = myHero.getGoalX();
			myHero.y = myHero.getGoalY();
		}
		myHero.my2=myHero.my1-(int) myHero.y;
		// 주인공을 그려준다.
		if (!myHero.chargeShotflag) {
			myHero.AddFrameLoop(myHero.speed / 40); // 주인공을 애니메이션 한다.
		} else {
			myHero.AddFrame(0.3f);
		}
		GameObject hit = CheckHDamage(myHero);
		if (hit != null) {

			vibe.playVibrator(100);
			gInfo.DoQuake();

			if (myHero.sldenergy - 30 > 0) {
				myHero.sldenergy -= 30;
			} else {
				myHero.sldenergy = 0;
			}
			if (myHero.sldenergy == 0) {

				if (myHero.energy > 1) {
					myHero.energy -= 10;
					myHero.roundhitteddamage+=10;
				} else if (myHero.energy <= 1) {
					myHero.maxscore = myHero.score;
					UserData.score = myHero.maxscore;
					UserData.gold = myHero.gold;
					((GameActivity) MainContext).handler.sendEmptyMessage(4); // save
																				// 호출

					GameMain.Mainmode = 1;
					GameMain.gamemode = 1;
				}
			}
		}
		for (int i = 0; i < Effect.size(); i++) // 이펙트 처리
		{
			if (Effect.get(i).type == 0) // 폭발 먼지 이펙트
			{
				Effect.get(i).energy -= 1;
				Effect.get(i).Zoom(gInfo, -0.02f, -0.02f);
				Effect.get(i).MovebyAngle(gInfo, Effect.get(i).direct, 1.2f);
				Effect.get(i).AddFrameLoop(0.4f);
				if (Effect.get(i).scalex < 0.1f || Effect.get(i).energy < 10)
					Effect.remove(i--);
			} else if (Effect.get(i).type == 1) // 동전
			{

				if (UpGreadeNinJa(Effect.get(i)) != null) {
					Effect.get(i).dead = true;
				}
				Effect.get(i).y -= Effect.get(i).power / 100;
				Effect.get(i).power -= 30;
				Effect.get(i).AddFrameLoop(0.4f);
				if (Effect.get(i).y > 800 || Effect.get(i).dead == true)
					Effect.remove(i--);
			} else if (Effect.get(i).type == 2) // 업글 포인트
			{

				if (UpGreadeNinJa(Effect.get(i)) != null) {
					Effect.get(i).dead = true;
				}

				Effect.get(i).energy -= 0.05f;
				Effect.get(i).angle = (Effect.get(i).angle + 1) % 360;
				Effect.get(i).MovebyAngle(gInfo, Effect.get(i).angle, 0.9f);

				Effect.get(i).MovebyAngle(gInfo, (float) Effect.get(i).GetAngle(myHero.x, myHero.y), 0.2f);
				if (Effect.get(i).energy <= 0 || Effect.get(i).dead == true)

					Effect.remove(i--);
			} else if (Effect.get(i).type == 3) { // 스테이지업 이펙트

				Effect.get(i).energy -= 0.01f;
				Effect.get(i).Zoom(gInfo, -0.01f, -0.01f);
				if (Effect.get(i).energy <= 0 || Effect.get(i).dead == true)
					Effect.remove(i--);

			}

		}

		if (!myHero.chargeShotflag) // 차지샷 충전중에는 일반총알은 만들지않는다.
		{

			MakeBullet(0);
		} else {
			ChargeShot();
		}

		MakeMonster();

		MakeEBullet();

	}

	public void DogameMethod() { // 우선순위를 고려해서 배치
		synchronized (mGL) {
			int i;
			if (!pauseOn) {
				update();
			}

			/**
			 * 버튼의 index 값과 UIButtonClass에 정의된 고유값을 비교한다. 고유값은
			 * [클래스명].[그룹명].[버튼타입_고유번호] 형태로 구성되어 있다.
			 */

			for (i = 0; i < Game.size(); i++)
				Game.get(i).DrawSprite(gInfo); // 배경

			for (i = 0; i < Monster.size(); i++) {
				Monster.get(i).DrawSprite(gInfo);
				if (Monster.get(i).type != 4) {
					buttonSpr.PutValue(gInfo, (int) Monster.get(i).x, (int) Monster.get(i).y - 130, 21,
							(long) Monster.get(i).energy, true);
				}
			}

			for (i = 0; i < Bullet.size(); i++)
				Bullet.get(i).DrawSprite(gInfo);

			for (i = 0; i < Effect.size(); i++) {
				Effect.get(i).DrawSprite(gInfo); // 동전과 총알 충돌효과
				if (Effect.get(i).type == 3) {

					PutValue(buttonSpr, 320, 400, 17, 10, Effect.get(i).scalex, Effect.get(i).scaley,
							String.format("%d", myHero.stage));
				}
			}

			myHero.DrawSprite(gInfo);

			spatk1.x = myHero.x;
			spatk1.y = myHero.y;
		//	spatk1.DrawSprite(gInfo);
			for (i = 0; i < EnemyBullet.size(); i++) {
				GGumul(EnemyBullet.get(i));
				EnemyBullet.get(i).DrawSprite(gInfo);

			}
			if (!myHero.chargeShotflag && myHero.chargeShotcnt < 10) {
				// buttonSpr.PutAni(gInfo, (int) myHero.x, (int) myHero.y - 60,
				// myHero.chargeshottype, 0);
			} // 특수무기
			/*
			 * score.PutValueRight(gInfo, 450, 50, 1, (long) myHero.getGoalX(),
			 * 20, true); score.PutValueRight(gInfo, 450, 100, 1, (long)
			 * myHero.getGoalY(), 20, true); score.PutValueRight(gInfo, 450,
			 * 150, 1, MainUI.Get(0, 8).click, 20, true);
			 * score.PutValueRight(gInfo, 450, 200, 1, MainUI.Get(0, 2).click,
			 * 20, true); score.PutValueRight(gInfo, 450, 250, 1, MainUI.Get(0,
			 * 0).click, 20, true);
			 * 
			 * score.PutValueRight(gInfo, 350, 50, 1,
			 * ButtonType.STATE_NON_BUTTON, 20, true);
			 * score.PutValueRight(gInfo, 350, 100, 1,
			 * ButtonType.STATE_CLK_BUTTON, 20, true);
			 * score.PutValueRight(gInfo, 350, 150, 1, ButtonType.STATE_CHECKED,
			 * 20, true); score.PutValueRight(gInfo, 350, 200,
			 * 1,ButtonType.STATE_DOWN_BUTTON, 20, true); //
			 */// 값테스트용
			PutValue(buttonSpr, 400, 40, 23, 13, 1f, 1f, String.format("%d", myHero.gold));
			if (MainUI.Get(0, 0).click == ButtonType.STATE_CHECKED) {
				myHero.PutSprite(gInfo);
				myHero.setGoalX(myHero.x);
				myHero.setGoalY(myHero.y);

				if (MainUI.Get(0, 0).frame == 1) {
					pauseOn = true;
				} else {
					pauseOn = false;
				}
			}
			MainUI.Draw(mGL, gInfo, font);
			int Data1[] = new int[Item.size()], Sort1[] = new int[Item.size()];

			for (int j1 = 0; j1 < Item.size(); j1++) {
				Data1[j1] = j1;
				Sort1[j1] = (int)Item.get(j1).frame;
			}
			if(Item.size()>1){
			GameInfo.Sort(Data1, Sort1, 0, Item.size() - 1);
			}for (int j = 11; j <= 22; j++) // 아이템 그리기
			{
				try {
					int i1 = Data1[j - 11];
					Item.get(i1).x = MainUI.Get(0, j).x;
					Item.get(i1).y = MainUI.Get(0, j).y;
					Item.get(i1).DrawSprite(mGL, 0, gInfo, font);

				}

				catch (IndexOutOfBoundsException e) {
					buttonSpr.PutAni(gInfo, (int) MainUI.Get(0, j).x, (int) MainUI.Get(0, j).y, 20, 0, 1f, 1f, 0, 1, 0,
							false, false);
				}
			}
		}

	}

	void GGumul(GameObject target) {
		target.angle += target.direct;
		if (target.move == 0) {
			int rnd = 0;// MyRand.nextInt(3);

			if (rnd == 0) {
				target.power = 200; // 점프할 중력값
				target.move = 1; // move를 1로 셋팅
			}
		} else if (target.move == 1) // 가로를 축소하고, 세로를 길게 늘린다.
		{
			target.Zoom(gInfo, -0.02f, 0.02f);
			target.angle += 1;
			if (++target.delay >= 10) {
				target.delay = 0;
				target.move = 2;
			} // 8회 반복한다.
		} else if (target.move == 2) // 캐릭터가 세로로 길어진 상태이므로, 다시 원래 상태로 돌린다. 가로를
										// 확대하고 세로를 축소한다.
		{
			target.Zoom(gInfo, 0.02f, -0.02f);
			target.angle -= 1;
			if (++target.delay >= 20) {
				target.delay = 0;
				target.move = 3;
			} // 가로를 16회까지 늘려서 옆으로 길쭉해진 느낌을 내게 한다.
		} else if (target.move == 3) // move가 2일때쯤 16회 반복한 탓에 땅에 짜부된 느낌이 나게 된다.
										// 세로로 길쭉하게 8회 반복해서 원상 복구시킨다.
		{
			target.angle += 1;
			target.Zoom(gInfo, -0.02f, 0.02f);

			if (++target.delay >= 10) {
				target.delay = 0;
				target.SetZoom(gInfo, 1f, 1f); // 캐릭터를 다시 원래 비율로 되돌린다.
				target.move = 0;
			}
		}

		if (target.move >= 1) // move가 1 이상이면 점프한 상태이므로 중력값에 따라 점프시킨다. 최초 생성시
								// 위치인 oy보다 커지면 y좌표를 리셋한다.
		{
			// target.y -= target.power / 100;
			// target.power -= 25;
			// if ( target.y >= target.oy ) target.y = target.oy;
		}

	}

	public void PushButton(boolean push, float TouchX, float TouchY) {
		MainUI.Touch(gInfo, (int) TouchX, (int) TouchY, push);
		if (MainUI.Get(0, 2).CheckPos((int) TouchX, (int) TouchY)
				|| MainUI.Get(0, 0).CheckPos((int) TouchX, (int) TouchY)||MainUI.Get(0, 8).CheckPos((int) TouchX, (int) TouchY)) {

		} else {
			if (push) {
				MainUI.Get(0, 1).energy = -1;
				myHero.chargeShotflag = false;
				myHero.motion = 0;
				myHero.setGoalX(TouchX);
				myHero.setGoalY(TouchY);
			}
		}
	}

	public void EndDoGame() {
		this.pauseOn = false;
		this.backSpr = null;
		this.heroSpr = null;
		this.monsSpr = null;
		this.dustSpr = null;
		this.score = null;
		this.explosionSpr1 = null;
		this.bombSpr = null;
		this.buttonSpr = null;
		this.bullet = null;
		Game = null;
		Monster = null;
		Crashck = null;
		Bullet = null;
		EnemyBullet = null;
		Effect = null;
		this.myHero = null;
		this.button = null;
		MonsterTimer = 0;
	}

	public void PutValue(Sprite pattern, int x, int y, int motion, float size, float scalex,

	float scaley, String str) {

		for (int i = 0; i < str.length(); i++) {

			int val = str.charAt(i) - '0';

			if (str.charAt(i) == '-') {

				val = 0;

			} else if (str.charAt(i) < '0' || str.charAt(i) > '9') {

				break;

			}
			pattern.PutAni(gInfo, x, y, motion, val, scalex, scaley, 0f, 1.0f, 0, true, false);
			x += size;
		}
	}

	public void onBackPressed() {

		((GameActivity) MainContext).onDestroy();
	}

}
