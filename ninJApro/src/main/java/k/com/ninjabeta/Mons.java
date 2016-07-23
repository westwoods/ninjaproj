package k.com.ninjabeta;

import java.util.Random;

import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;

public class Mons extends GameObject { // 우리의 닌자용 GameObject 객체임. 우리가 원하는 변수및
										// 함수들 추가할수있음.

	public float speed = 20.f;
	public float gunspeed = 5f;
	public float damage = 10f;
	public float range = 40f;
	public long chargeShottimer = 0;
	public boolean chargeShotflag = false;

	public int chargeShotcnt = 0;
	public float score = 0;
	public float maxscore = 0;
	public float scoreTimer = 0f;
	public int bulletType = 0;

	public int startangle = 0;
	public int goalangle = 0;

	public int bulletroll ;
	public int dst = 0;

	public static boolean fullflag=true;

	public Random MyRand = new Random(); // 랜덤 발생기

	public int chargeshottype = 0;
	public int charactertype = 0;
	public int multibullet = 1;
	public float scollspeed = 0;

	public Mons() {
		super();
		this.bulletroll=(MyRand.nextInt(3)+4)*(MyRand.nextInt(2)%2==0?-1:+1);
	}

	public Mons(float speed, float gunspeed, float damage, float range, int charactertype, int chargeshottype) {
		super();
		this.speed = speed;
		this.gunspeed = gunspeed;
		this.damage = damage;
		this.range = range;
		this.charactertype = charactertype;
		this.chargeshottype = chargeshottype;
		this.bulletroll=(MyRand.nextInt(3)+4)*(MyRand.nextInt(2)%2==0?-1:+1);
	}

	
	public void SetMon(int type) {
		if (type == 0) {
			this.ox = 360;
			this.oy = 120;
			this.startangle = 2900;
			this.goalangle = 1700; // 120도 이동
			this.dst = 240;
			this.speed = 12;
		}

		if (type ==1) {
			this.ox = 360;
			this.oy = 60;

			this.startangle = 3000;
			this.goalangle = 2600; // 80도 이동
			this.dst = 215;
			this.speed = 7;
		}

		if (type == 2) {
			this.ox = 120;
			this.oy = 120;

			this.startangle =700;
			this.goalangle = 1900; // 120도이동
			this.dst = 240;
			this.speed = 12;
		}
		if (type == 3) {
			this.ox = 120;
			this.oy = 60;

			this.startangle = 600;
			this.goalangle =1000; // 80도이동
			this.dst = 215;
			this.speed = 7;
		}

	
		this.direct = this.startangle;
	} /*
	ox, oy 는 회전 중심축임. 이것을 적절히 조절하여 최종 위치를 조절할수있다.
	speed는 1초에 변화시켜줄 각도 값임. 각도는 4자리 3600이 360도이다.
	*/

	public void calcxy() {
		if (this.goalangle - this.direct > speed) {
			this.direct+=this.speed;
		} else if (this.goalangle - this.direct < -speed) {
			this.direct-=this.speed;
		} else{
			return;
		}

		this.x = this.ox + (GameInfo.SinTBL2[this.direct] * this.dst);
		this.y =this.oy - (GameInfo.CosTBL2[this.direct] * this.dst);

	}
	public int returnItem() {
		int itemnum;
		return bulletType;
		
	}
}
