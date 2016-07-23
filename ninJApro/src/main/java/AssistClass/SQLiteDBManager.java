package AssistClass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KIMDONGWOO on 2016-07-21.
 */

public class SQLiteDBManager extends SQLiteOpenHelper {
    String id;
    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public SQLiteDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,String Userid) {
        super(context, name, factory, version);
        id=Userid;
    }
//
    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE UserInfo (id TEXT PRIMARY KEY, setuptime TEXT, accessTime TEXT, endTime TEXT);");
        db.execSQL("INSERT INTO UserInfo (id, setuptime) VALUES('" + id + "', +"+" datetime('now', 'localtime') );");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String create_at, String item, int price) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
      //  db.execSQL("INSERT INTO UserInfo (item, price, create_at) VALUES('" + item + "', " + price + ", '" + create_at + "');");
        db.close();
    }

    public void updateaccesstime() {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE UserInfo SET accessTime=datetime('now', 'localtime') " + " WHERE id='" + id + "';");
        db.close();
    }
    public void updateendtime() {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE UserInfo SET endTime=datetime('now', 'localtime') " + " WHERE id='" + id + "';");
        db.close();
    }
    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM UserInfo WHERE id='" + id + "';");
        db.close();
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM UserInfo WHERE id="+ id, null);
        while (cursor.moveToNext()) {
            result += " ID: "
                    + cursor.getString(0)
                    + " setuptime: "
                    + (UserData.downLoadTime=cursor.getString(1))
                    + " accesstime: "
                    + (UserData.accessTime=cursor.getString(2))
                    + " endtime: "
                    + (UserData.endTime=cursor.getString(3))
                    + "\n";
        }

        return result;
    }
}

