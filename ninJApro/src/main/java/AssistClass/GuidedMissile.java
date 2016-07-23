package AssistClass;
import bayaba.engine.lib.GameObject;

public class  GuidedMissile {

	
	
	public GuidedMissile(GameObject from, GameObject to) {
		this.from = from;
		this.to = to;
		
	}
	private GameObject from=new GameObject();
	private GameObject to=new GameObject();
	
	public void guidecalcAngle(){
	
		float angle=calcAngle()%360;
		if(angle>from.vertical+2)
		{
		from.vertical=from.vertical+1.4f; //좀더 가까운방향으로 유도가되게 해야함.
		
		}
		else if(angle<=from.vertical+2&&angle>from.vertical-2)
		{ //어느정도 조준이 완료된경우
		}
		else
		{
		from.vertical=from.vertical-1.4f; 
		}
	}
	
	public float calcAngle(){
		float angle=(float) from.GetAngle(to.x, to.y); 
		
		return angle;
	}
	
}
