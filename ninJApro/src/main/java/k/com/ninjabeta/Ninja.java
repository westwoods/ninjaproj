package k.com.ninjabeta;

import java.util.ArrayList;

import AssistClass.ItemSet;
import AssistClass.UserData;
import bayaba.engine.lib.GameObject;

public class Ninja extends GameObject { // 우리의 닌자용 GameObject 객체임. 우리가 원하는 변수및
										// 함수들 추가할수있음.

	public float speed = 20.f;
	public float gunspeed = 5f;
	public float damage = 10f;
	public float range = 40f;
	public long chargeShottimer = 0;
	public boolean chargeShotflag = false;
	public boolean jammed=false;
	public int chargeShotcnt = 0;
	public int score = 0;
	public int maxscore = 0;
	public float scoreTimer = 0f;
	public int bulletType = 0;
	public int roundnonhitted = 0;
	public int roundhitteddamage = 0;
	public int chargeshottype = 0;
	public int charactertype = 0;
	public int multibullet = 1;
	public float scollspeed = 0;
	public ArrayList<ItemSet> Item = new ArrayList<ItemSet>();
	public Ninja() {
		super();
	}

	public Ninja(float speed, float gunspeed, float damage, float range, int charactertype, int chargeshottype) {
		super();
		this.speed = speed;
		this.gunspeed = gunspeed;
		this.damage = damage;
		this.range = range;
		this.charactertype = charactertype;
		this.chargeshottype = chargeshottype;
		for(int i=0;i<UserData.Item.size();i++){
		Item.add(UserData.Item.get(i));}
	}
	
	public int gold = 0;
	public  int dia = 0;

	public int stair = 0;

	public int stage = 0;
	private float goalX = 240;
	private float goalY = 700;
	public float maxenergy=100;
	public float maxsldenergy=100;
	public float sldenergy=0;

	public float chargingsldenergy=0;
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public float getGoalX() {
		return goalX;
	}

	public void setGoalX(float goalX) {
		this.goalX = goalX;
	}

	public float getGoalY() {
		return goalY;
	}

	public void setGoalY(float goalY) {
		this.goalY = goalY;
	}

	public float getGunspeed() {
		return gunspeed;
	}

	public void setGunspeed(float gunspeed) {
		this.gunspeed = gunspeed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getScollspeed() {
		if (this.y > 600) {
			if (this.y > 760) {
				return -3;
			}
			if (this.y > 700) {

				return -2;
			}
			if (this.y > 630) {

				return -1;
			}

			if (this.y > 600) {
				return 0;
			}
		} else {

			if (this.y < 150) {

				return 11;
			}
			if (this.y < 300) {

				return 10;
			}
			if (this.y < 400) {

				return 9;
			}
			if (this.y < 480) {

				return 8;
			}
			if (this.y < 520) {

				return 7;
			}
			if (this.y < 540) {

				return 6;
			}
			if (this.y < 560) {

				return 5;
			}
			if (this.y < 580) {

				return 3;
			}
			if (this.y <= 600) {

				return 1;
			}
		}
		return 0;
	}
	
	public String toString(){
		return null;
		
	}

}
