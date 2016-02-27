package kr.co.makeit.hanium.login;

import java.io.BufferedReader;  
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.co.makeit.hanium.join.JoinPage_;
import kr.co.makeit.hanium.main.MainViewActivity_;
import kr.co.makeit.hanium.main.R;
import kr.co.makeit.hanium.rest.MakeitConn;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.login_page_il)
public class LoginPage_normal extends Activity {

	@ViewById
	Button okbtn;
	@ViewById
	EditText il_login_id, il_login_pass;
	@ViewById
	TextView findbtn, find2btn, joinbtn;
	@Bean
	MakeitConn con;
	ArrayList<NameValuePair> post;
	boolean asd = false;

	@Click({ R.id.okbtn, R.id.joinbtn, R.id.findbtn, R.id.find2btn })
	void click(View v) {
		switch (v.getId()) {
		case R.id.okbtn:
			String IdValue = il_login_id.getText().toString();
			String passValue = il_login_pass.getText().toString();
			post = new ArrayList<NameValuePair>();
			post.add(new BasicNameValuePair("memId", IdValue));
			post.add(new BasicNameValuePair("pass", passValue));

			postParse(post);

			break;
		case R.id.joinbtn:
			Intent intent = new Intent(getApplicationContext(), JoinPage_.class);
			startActivity(intent);
			finish();
			break;
		case R.id.findbtn:
			Intent intent1 = new Intent(getApplicationContext(),
					EditPass_.class);
			startActivity(intent1);
			finish();
			break;
		case R.id.find2btn:
			// Intent intent2 = new Intent(getApplicationContext(),
			// FindId_.class);
			// startActivity(intent2);
			// finish();
			break;

		}
	}

	@Background
	void postParse(ArrayList<NameValuePair> post) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://14.63.215.43/loginMember.do");

		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 3000);

		HttpResponse httpResponse;

		try {
			UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(
					post, "utf-8");
			httpPost.setEntity(encodedFormEntity);
			httpResponse = client.execute(httpPost);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(
							httpResponse.getEntity().getContent(), "utf-8"));

			String line = null;
			String result = "";
			while ((line = bufferedReader.readLine()) != null) {
				result += line;
			}

			ObjectMapper mapper = new ObjectMapper();
			kr.co.makeit.hanium.domain.Account account = mapper.readValue(
					result, kr.co.makeit.hanium.domain.Account.class);

			setUi(account);
		} catch (IOException er) {
			// TODO Auto-generated catch block
			asd = true;
			error(asd);
		}

	}

	@UiThread
	void setUi(kr.co.makeit.hanium.domain.Account account) {

			Intent i = new Intent(getApplicationContext(),
					MainViewActivity_.class);
			i.putExtra("Account", account);
			startActivity(i);
			finish();
		
	}
	
	@UiThread
	void error(boolean asd){
		if(asd == true) {
			Toast.makeText(getApplicationContext(), "로그인정보가 불일치합니다",
					Toast.LENGTH_SHORT).show();
			
		}
	}

}
