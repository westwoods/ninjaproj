package AssistClass;

import bayaba.engine.lib.GameObject;

public class Vector2D { //유도탄 어시스트 클래스

	 public float x;
     public float y;
    
	public Vector2D(GameObject x)
	{
		this.x=x.x;
		this.y=x.y;
	}
	public Vector2D()
	{
		//기본 생성자.
	}
	//벡터크기 구하기. Magnitude of Vector
	public float Length()
	{
		float len=0;
		len=(float) Math.sqrt(x*x+y*y);
		return len;
	}
	//벡터간의 거리를 구한다.
	public float Distance(Vector2D value)
    {
        return (float) Math.sqrt(Math.pow(Math.abs(value.x - x), 2) + Math.pow(Math.abs(value.y - y), 2));

    }
	//벡터를 구한다.
	public static Vector2D DirectionVector(GameObject from,GameObject to)
    {
        Vector2D dirVector=new Vector2D();
        dirVector.x = to.x - from.x;
        dirVector.y = to.y - from.y;
      
        return dirVector;

    }
	//벡터 정규화(unit vector)
	public void Normalize()
    {
        float Len = Length();
        if (Len == 0) Len = 1;
       
        x /= Len;
        y /= Len;
    }
	
}
