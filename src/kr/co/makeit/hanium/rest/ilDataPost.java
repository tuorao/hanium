package kr.co.makeit.hanium.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class ilDataPost extends AsyncTask<Void, Void, String> {

	HashMap<String, String> map;
	
	public ilDataPost(HashMap<String, String> map) {
		this.map = map;
	}

	protected String doInBackground(Void... unused) {
		String content = executeClient();
		return content;
	}

	protected void onPostExecute(String result) {
		// 모두 작업을 마치고 실행할 일 (메소드 등등)
	}

	// 실제 전송하는 부분
	public String executeClient() {
		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();

		
		String memId = map.get("memId");
		String pass = map.get("pass");
		String memName = map.get("memName");
		String memNickName = map.get("memNickName");
		String memBirth = map.get("memBirth");
		String gender = map.get("gender");
		String memProfile = map.get("memProfile");

		String memLat = String.valueOf(23.0);
		String memLon = String.valueOf(24.0);

		post.add(new BasicNameValuePair("memId", memId));
		post.add(new BasicNameValuePair("pass", pass));
		post.add(new BasicNameValuePair("memBirth", memBirth));
		post.add(new BasicNameValuePair("gender", gender));
		post.add(new BasicNameValuePair("memName", memName));
		post.add(new BasicNameValuePair("memNickname", memNickName));
		post.add(new BasicNameValuePair("memProfile", memProfile));
		post.add(new BasicNameValuePair("memPic", "memPic"));

		post.add(new BasicNameValuePair("memLat", memLat));
		post.add(new BasicNameValuePair("memLon", memLon));

		// 연결 HttpClient 객체 생성
		HttpClient client = new DefaultHttpClient();

		// 객체 연결 설정 부분, 연결 최대시간 등등
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);

		// Post객체 생성
		HttpPost httpPost = new HttpPost(
				"http://14.63.215.43/joinMember.do");

		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post,
					"UTF-8");
			httpPost.setEntity(entity);
			client.execute(httpPost);
			return EntityUtils.getContentCharSet(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}