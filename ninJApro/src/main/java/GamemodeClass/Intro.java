package GamemodeClass;

import android.content.Context;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import AssistClass.ConnectDB;
import AssistClass.UserData;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;
import k.com.ninjabeta.FaceBookLoginActivity;
import k.com.ninjabeta.GameActivity;
import k.com.ninjabeta.GameMain;

public class Intro {

	public Intro() {
	}

	public Intro(Context context, GameInfo info, GL10 mGL) { // 로드게임에서 생성하면됨.
		this.MainContext = context;
		this.gInfo = info;
		this.mGL = mGL;
		LoadingSprite.LoadSprite(mGL, MainContext, "Intro Pig.spr");
		Loading.SetObject(LoadingSprite, 0, 0, 240, 400, 0, 0);
		Loading.SetZoom(gInfo, 2, 2);
		Loading.trans = 1.0f;
		((GameActivity) MainContext).handler.sendEmptyMessage(3);
	}

	String notice = " "; // TODO
	public GL10 mGL = null; // OpenGL 객체
	public Context MainContext;
	public Random MyRand = new Random(); // 랜덤 발생기
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
	public static boolean loadflag = false;
	Sprite LoadingSprite = new Sprite();
	GameObject Loading = new GameObject();

	float angle = 0, xpos = 460;

	public boolean DoNotice() // 1/60초에 한번씩 SurfaceClass에서 호출된다. 게임의 코어 부분을 넣는다.
	{
		GameInfo.font.SetArea(30, 720, 480, 800); // 폰트가 출력될 영역을 임의로 지정한다.
		GameInfo.font.DrawFont(mGL, xpos-=7, 720, 30.0f, UserData.notice);
		if (xpos < -UserData.notice.length() * 8)
		{

			xpos = 460;
			return true;
		}
		return false;
	}

	public void EndIntro() {

		LoadingSprite = null;
		Loading = null;
	}

	public void DoIntroMethod() { 
// DoGame에서 호출하면됨.
		Loading.DrawSprite(gInfo);
		Loading.AddFrame(0.181f);
		if(ConnectDB.downflag){
			if (Loading.EndFrame()&&DoNotice()) {
				GameMain.gamemode = 1;
			}
		}

		FaceBookLoginActivity.faceBookLoginActivity.finish();
		}
	


}
