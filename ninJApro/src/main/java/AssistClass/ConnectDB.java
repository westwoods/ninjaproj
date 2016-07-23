package AssistClass;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ConnectDB {

	public static CharSequence string;
	private Context context;
	static int NO = 0;
	static String ID = null;
	static	String IP = null;
	static String accesstime = null;
	static String notice = null;
	static int gold=0;
	static int dia= 0;
	static int score= 0;
	static String itembag= null;
	static String ninja1item= null;
	static String ninja2item= null;
	static String ninja3item= null;
	public ConnectDB(Context context) {
		this.context = context;
		this.android_id = UserData.ID;
		data="서버에 접속중입니다.";
		downflag=false;
		upflag=false;
	}


	String android_id;
	public static String data="서버에 접속중입니다.";
	public static boolean downflag=false;
	public static boolean upflag=false;
	public void DisConnectDB() {
		this.context = null;
		this.android_id = null;
		data=null;
	}
	public void HttpSaveData() // 데이터 베이스에다 값을 입력함
	{
		// String SAVE_URL = "http://www.test.com/test.php";
		String SAVE_URL = "http://kmcom.dothome.co.kr/testup.php";
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter("http.protocol.content-charset", "utf-8");
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 15000);
		HttpResponse response;
		HttpEntity entity;
		try {
			HttpPost post = new HttpPost(SAVE_URL);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			nvps.add(new BasicNameValuePair("ID", android_id));

			nvps.add(new BasicNameValuePair("accessTime",UserData.accessTime));
			nvps.add(new BasicNameValuePair("downLoadTime",UserData.downLoadTime));
			nvps.add(new BasicNameValuePair("gold", Integer.toString(UserData.gold)));
			nvps.add(new BasicNameValuePair("dia", Integer.toString(UserData.dia)));
			nvps.add(new BasicNameValuePair("score", Integer.toString(UserData.score)));

			post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

			response = client.execute(post);
			entity = response.getEntity();
			InputStream is = entity.getContent();
			is.close();
			if (entity != null)
				entity.consumeContent();
		} catch (Exception e) {
			string = e.toString();
		}
	
	}

	public String HttpLoadData() // 데이터 베이스에다 값받아
	{

		String SAVE_URL = "http://kmcom.dothome.co.kr/testdown.php";

		String Json = DownloadHtml(SAVE_URL);
		Log.i("Json : ", Json);
		try {

			JSONArray ja = new JSONArray(Json);
			for (int j = 0; j < ja.length(); j++) {
				JSONObject order = ja.getJSONObject(j);
				NO = order.getInt("NO");
				ID = order.getString("ID");
				IP = order.getString("IP");
				accesstime = order.getString("accessTime");
				notice = order.getString("notice");
				gold = order.getInt("gold");
				dia = order.getInt("dia");
				score = order.getInt("score");
				itembag = order.getString("itembag");
				ninja1item = order.getString("ninja1item");
				ninja2item = order.getString("ninja2item");
				ninja3item = order.getString("ninja3item");

			}

			UserData.SetUserData(NO, ID, IP,accesstime, notice,  gold, dia, score, itembag,
					ninja1item, ninja2item,  ninja3item) ;
			return notice;
		} catch (JSONException e) {

			string ="자료가 누락되었습니다.";
			Log.i("Json : ","자료가 누락되었습니다.");
			return null;
		}
	}

	String DownloadHtml(String addr) {
		StringBuilder jsonHtml = new StringBuilder();
		try {
			// 연결 url 설정
			URL url = new URL(addr);
			// 커넥션 객체 생성
			URLConnection urlconn = url.openConnection();
			HttpURLConnection conn = (HttpURLConnection) urlconn;
			// 연결되었으면.
			if (conn != null) {
				data="연결되었음.";
				conn.setConnectTimeout(10000);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				String andId = "ID=" + android_id;
				// 연결되었음 코드가 리턴되면.

				data="로그인중";
				OutputStream opstrm = conn.getOutputStream();
				opstrm.write(andId.getBytes());
				opstrm.flush();
				opstrm.close();
				conn.getResponseCode();
				data="값 받아오는중";
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				for (;;) {
					// 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
					String line = br.readLine();
					if (line == null)
						break;
					// 저장된 텍스트 라인을 jsonHtml에 붙여넣음
					jsonHtml.append(line + "\n");
				}
				br.close();
			}
			conn.disconnect();
			data="접속종료";
			return jsonHtml.toString();
		} catch (Exception ex) {
			string = ex.toString();
			return null;
		}

	}

	
}
