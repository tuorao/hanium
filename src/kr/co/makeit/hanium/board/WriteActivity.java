package kr.co.makeit.hanium.board;

import java.io.IOException;
import java.util.ArrayList;

import kr.co.makeit.hanium.main.R;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_write)
public class WriteActivity extends Activity {
	
	@ViewById
	EditText title, content;
	@ViewById
	Button btn_write, btn_cancel;

	Thread thread = new Go();
	
	@AfterViews
	void ui(){
		setUi();
	}
	
	private void setUi(){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
	}
	
	@Click({R.id.btn_write,R.id.btn_cancel})
	void click(View v){
		switch(v.getId()){
		case R.id.btn_write:
			thread.start();
			if(thread != null && thread.isAlive()){
				thread.interrupt();
			}
			
			Intent i = new Intent(getApplicationContext(),BoardActivity_.class);
			i.putExtra("go", 1);
			startActivity(i);
			break;
		case R.id.btn_cancel:
			break;
		}
	}

	public class Go extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			String url = "http://14.63.215.43/writeBoard.do";

			try {
				HttpClient http = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("bName", title
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("bBody", content
						.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("memSrl", "1"));
				nameValuePairs.add(new BasicNameValuePair("bAllow", "1"));
				nameValuePairs.add(new BasicNameValuePair("agree", "1"));
				nameValuePairs.add(new BasicNameValuePair("bDel", "1"));
				nameValuePairs.add(new BasicNameValuePair("groupSrl", "1"));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
						"utf-8"));
				http.execute(httpPost);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
