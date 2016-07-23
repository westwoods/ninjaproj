package AssistClass;

import java.util.ArrayList;

public class UserData {
	// 세이브와로드 로컬데이터를 저장하는 클래스임.
	public static int NO;
	public static String ID;
	public static String IP;
	public static String downLoadTime;
	public static String endTime;
	public static String accessTime;
	public static String notice="왜안되는거지";
	public static int gold;
	public static int dia;
	public static int score;
	public static String itembag;
	public static String ninja1item;

	public static int[] openIndex={0,0,0};
	public static String ninja2item;
	public static String ninja3item;
	public static ArrayList<ItemSet> Item ;
	public static ArrayList<ItemSet> Item1 ;
	
	public static ArrayList<ItemSet> Item2 ;
	public static ArrayList<ItemSet> Item3;
//	private static Sprite itemSpr;
//	private static Context context;
//	private static GameInfo info;
//	private static GL10 mGL;
//	public static void SetcontextUserData(Context context, GameInfo info, GL10 mGL) {
//		UserData.context=context;
//		UserData.info=info;
//		UserData.mGL=mGL;
//		itemSpr=new Sprite();
//		itemSpr.LoadSprite(mGL, context, "playui.spr");

//	}
	public static void DelUserData() {

	}

	public static void SetUserData(int nO, String iD, String iP, String accesstime, String notice, int gold, int dia,
			int score, String itembag, String ninja1item, String ninja2item, String ninja3item) {
		Item = new ArrayList<ItemSet>();
		Item1 = new ArrayList<ItemSet>();
		Item2 = new ArrayList<ItemSet>();
		Item3 = new ArrayList<ItemSet>();
		NO = nO;
		ID = iD;
		IP = iP;
		if(UserData.accessTime ==null)
			UserData.accessTime=accesstime;
		UserData.notice =notice;
		UserData.gold = gold;
		UserData.dia = dia;
		UserData.score = score;
		UserData.itembag = itembag;
		UserData.ninja1item = ninja1item;
		UserData.ninja2item = ninja2item;
		UserData.ninja3item = ninja3item;
		ItemSet itemSet = null;

		for (int i = 0; i < 33; i++) {
			if (i == 0)
				itemSet = new ItemSet(i, 0, "11");
			if (i == 1)
				itemSet = new ItemSet(i, 0, "22");
			if (i == 2)
				itemSet = new ItemSet(i, 0, "33");
			if (i == 3)
				itemSet = new ItemSet(i, 0, "44");
			if (i == 4)
				itemSet = new ItemSet(i, 0, "22");
			if (i == 5)
				itemSet = new ItemSet(i, 0, "22");
			if (i == 6)
				itemSet = new ItemSet(i, 0, "22");
			if (i == 7)
				itemSet = new ItemSet(i, 0, "22");
			if (i == 8)
				itemSet = new ItemSet(i, 0, "22");
			if (i == 9)
				itemSet = new ItemSet(i, 0, "22");
			if (i == 10)
				itemSet = new ItemSet(i, 0, "22");
			if (i == 11)
				itemSet = new ItemSet(i, 0, "22");
			if (i >= 12)
				itemSet = new ItemSet(i, 0, "22");

			Item.add(itemSet);
		}
		for (int i = 0; i < 3; i++) {
			if (i == 0)
				itemSet = new ItemSet(4, 0, "11");
			if (i == 1)
				itemSet = new ItemSet(5, 0, "22");
			if (i == 2)
				itemSet = new ItemSet(6, 0, "33");
		
			//itemSet.SetButton(itemSpr, ButtonType.TYPE_ONE_CLICK, 0, 0.5f,0.5f, 10);
		//	itemSet.frame=itemSet.itemcode;
			//itemSet.SetZoom(info, 0.5f, 0.5f);
			
			Item1.add(itemSet);
			openIndex[0]=i;
		}
		for (int i = 0; i < 3; i++) {
			if (i == 0)
				itemSet = new ItemSet(4, 0, "11");
			if (i == 1)
				itemSet = new ItemSet(5, 0, "22");
			if (i == 2)
				itemSet = new ItemSet(6, 0, "33");
		
		//	itemSet.SetButton(itemSpr, ButtonType.TYPE_ONE_CLICK, 0, 0.5f,0.5f, 10);
		//	itemSet.frame=itemSet.itemcode;
		//	itemSet.SetZoom(info, 0.5f, 0.5f);
			
			Item2.add(itemSet);
			openIndex[1]=i;
		}
		for (int i = 0; i < 3; i++) {
			if (i == 0)
				itemSet = new ItemSet(4, 0, "11");
			if (i == 1)
				itemSet = new ItemSet(5, 0, "22");
			if (i == 2)
				itemSet = new ItemSet(6, 0, "33");
		
		//	itemSet.SetButton(itemSpr, ButtonType.TYPE_ONE_CLICK, 0, 0.5f,0.5f, 10);
		//	itemSet.frame=itemSet.itemcode;
		//	itemSet.SetZoom(info, 0.5f, 0.5f);
			
			Item3.add(itemSet);
			openIndex[2]=i;
		}
	}
}
