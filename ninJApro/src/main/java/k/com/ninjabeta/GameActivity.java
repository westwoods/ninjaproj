package k.com.ninjabeta;

/**
 * Created by KIMDONGWOO-LAB on 2016-07-19.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import AssistClass.ConnectDB;
import AssistClass.SQLiteDBManager;
import AssistClass.UserData;
import bayaba.engine.lib.GameInfo;

import static java.lang.Thread.sleep;


@SuppressLint("HandlerLeak")
public class GameActivity extends Activity {
    private MyAsyncTaskLoad myAsyncTaskLoad;
    private MyAsyncTaskSave myAsyncTaskSave;
    private GLView play;
    private GameMain sImg;
    public GameInfo gInfo;
    public ConnectDB conDB;
    public String usr;
    public boolean LoggedIn = false;
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    break;

                case 1:

                    break;
                case 2:
                     break;
                case 3: {

                    myAsyncTaskLoad.execute("LOAD");

                    break;
                }
                case 4: {

                    Toast.makeText(GameActivity.this, "게임오버, 서버에 저장합니다.",Toast.LENGTH_SHORT).show();
                    myAsyncTaskSave = new MyAsyncTaskSave();
                    myAsyncTaskSave.execute("SAVE");
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =getIntent();
        UserData.ID=intent.getExtras().getString("user");
        Log.i("TAG","UserID="+UserData.ID);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // requestWindowFeature(Window.FEATURE_NO_TITLE); 구글연동하기위해 없엠.
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        gInfo = new GameInfo(480, 800, 60);
        gInfo.ScreenXsize = getResources().getDisplayMetrics().widthPixels;
        gInfo.ScreenYsize = getResources().getDisplayMetrics().heightPixels;
        gInfo.SetScale();
        conDB = new ConnectDB(this);
        sImg = new GameMain(this, gInfo);
        play = new GLView(this, sImg);
        play.setRenderer(new SurfaceClass(sImg));
        setContentView(play);
        myAsyncTaskLoad = new MyAsyncTaskLoad();

    }

    @Override
    public void onDestroy() {
        SQLiteDBManager myDB=new SQLiteDBManager(GameActivity.this,"gameDB.db",null,1,UserData.ID);
        if(myDB!=null)
        {
            myDB.updateendtime();
            Log.i( "MyDB",myDB.getResult());
            myDB.close();
        }
        sImg.EndGame();
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK&&GameMain.canback) {
            sImg.onBackPressed();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    public class MyAsyncTaskLoad extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            if  (!isCancelled ())
            {
                if (params[0].equals("LOAD"))
                    conDB.HttpLoadData();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ConnectDB.downflag=true;
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
    public class MyAsyncTaskSave extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            if  (!isCancelled ())
            {
                if (params[0].equals("SAVE"))
                    conDB.HttpSaveData();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ConnectDB.upflag=true;
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }



}
