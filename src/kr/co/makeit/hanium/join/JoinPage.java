package kr.co.makeit.hanium.join;

import java.io.IOException;  
import java.util.ArrayList;
import java.util.HashMap;

import kr.co.makeit.hanium.login.LoginPage_normal_;
import kr.co.makeit.hanium.main.R;
import kr.co.makeit.hanium.rest.Parser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.joinpage)
public class JoinPage extends Activity{
	
	@ViewById
	EditText il_memId, il_pass, il_pass2, il_memNickName;
	@ViewById
	Button okbtn, canbtn, idcheck, nickcheck;
	Handler handler = new Handler();
	ArrayList<NameValuePair> list;
	Parser parser;
	
	String checkString;
	ArrayList<HashMap<String, Object>> ar;
	JSONObject json_main;
	HashMap<String, Object> map;
	String result;
	boolean CHECK_ID = false,CHECK_NICK;
	
	@Click({R.id.okbtn, R.id.canbtn,R.id.idcheck,R.id.nickcheck})
	void click(View v) {
		String pass_Value = il_pass.getText().toString();
		String pass2_Value = il_pass2.getText().toString();
		String Id_Value = il_memId.getText().toString();
		String NickName_Value = il_memNickName.getText().toString();
	
		switch(v.getId()){
		case R.id.okbtn:
			
			if(Id_Value.contains("@") != true ) {
				Toast.makeText(getApplicationContext(), "ID 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
				break;
			}
			
			else if(Id_Value.equals("")||pass_Value.equals("")||pass2_Value.equals("")) {
				Toast.makeText(getApplicationContext(), "빠짐없이 기재하여 주세요.", Toast.LENGTH_SHORT).show();
			break;	
			} else if(pass_Value.equals(pass2_Value)) {
				Intent i = new Intent(JoinPage.this, JoinPage2_.class);
				i.putExtra("Id_value", Id_Value);
				i.putExtra("pass_value", pass_Value);
				i.putExtra("NickName", NickName_Value);
				startActivity(i);
				finish();
				break;
			}else{
				Toast.makeText(getApplicationContext(), "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
				break;
				}
		case R.id.canbtn:
			Intent i2 = new Intent(getApplicationContext(), LoginPage_normal_.class);
			startActivity(i2);
			finish();
			break;
		case R.id.idcheck:
			list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("check_Id", Id_Value));
			ParThread par = new ParThread();
			par.start();
			
			handler = new Handler() {
				 @Override
		            public void handleMessage(Message msg) {
		               // TODO Auto-generated method stub
		               super.handleMessage(msg);
		               if (msg.what == 1) {
		                  Toast.makeText(getApplicationContext(), "사용가능한 ID입니다.",
		                        Toast.LENGTH_SHORT).show();
		                  CHECK_ID = true;
		               } else if (msg.arg1 == 2) {

		                  Toast.makeText(getApplicationContext(),
		                        "이미 사용중인 ID입니다.", Toast.LENGTH_SHORT).show();
		                  CHECK_ID = false;
		               } else {
		                  Toast.makeText(getApplicationContext(),
		                        "이미 사용중인 ID입니다.", Toast.LENGTH_SHORT).show();
		                  CHECK_ID = false;
		               }

		            }
			};
			break;
		case R.id.nickcheck:
	         ParThread2 par2 = new ParThread2();
	         par2.start();

	         handler = new Handler() {
	            @Override
	            public void handleMessage(Message msg) {
	               // TODO Auto-generated method stub
	               super.handleMessage(msg);
	               if (msg.what == 1) {
	                  Toast.makeText(getApplicationContext(), "사용가능한 NickName 입니다.",
	                        Toast.LENGTH_SHORT).show();
	                  CHECK_NICK = true;
	               } else if (msg.arg1 == 2) {

	                  Toast.makeText(getApplicationContext(),
	                        "이미 사용중인 NickName 입니다.", Toast.LENGTH_SHORT).show();
	                  CHECK_NICK = false;
	               } else {
	                  Toast.makeText(getApplicationContext(),
	                        "이미 사용중인 NickName 입니다.", Toast.LENGTH_SHORT).show();
	                  CHECK_NICK = false;
	               }
	            }
	         };
	         break;
			
		}
	}
	
	public class ParThread extends Thread {
	      @Override
	      public void run() {
	         // TODO Auto-generated method stub
	         super.run();
	         parser = new Parser();
	         result = parser.PostParse(list, "http://14.63.215.43/dupCheck.do");
	         
	         Looper.prepare();
	         if (result.equals("0")) {
	            handler.post(new Runnable() {

	               @Override
	               public void run() {
	                  // TODO Auto-generated method stub
	                  Message msg = handler.obtainMessage();
	                  msg.what = 1;
	                  handler.sendMessage(msg);
	               }
	            });
	            Looper.loop();
	         } else {
	            handler.post(new Runnable() {

	               @Override
	               public void run() {
	                  // TODO Auto-generated method stub
	                  Message msg = handler.obtainMessage();
	                  msg.arg1 = 2;
	                  handler.sendMessage(msg);
	               }
	            });
	         }
	      }
	   }
	   
	   //닉네임 중복체크할때 이용되는 쓰레드
	   public class ParThread2 extends Thread {
	      
	      @Override
	      public void run() {
	         // TODO Auto-generated method stub
	         super.run();

	         String NickName_Value = il_memNickName.getText().toString();
	         HttpClient client = new DefaultHttpClient();
	         String url = "http://14.63.215.43/nickNameCheck.do?memNickName="+NickName_Value;
	         HttpGet get = new HttpGet(url);
	         HttpResponse response = null;
	           try {
	               response = client.execute(get);
	           } catch (ClientProtocolException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	           } catch (IOException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	           }
	         HttpEntity resEntity = response.getEntity();
	         String sRes = "";
	         
	         
	         if(resEntity != null){
	              try {
	              sRes = EntityUtils.toString(resEntity);  
	              //sRes = URLDecoder.decode(sRes);
	              Log.w("SNSApp", "response: " + sRes);  
	               } catch (ParseException e) {
	                   // TODO Auto-generated catch block
	                   e.printStackTrace();
	               } catch (IOException e) {
	                   // TODO Auto-generated catch block
	                   e.printStackTrace();
	               }       
	         }
	         
	         Looper.prepare();
	         if (sRes.equals("0")) {
	            handler.post(new Runnable() {

	               @Override
	               public void run() {
	                  // TODO Auto-generated method stub
	                  Message msg = handler.obtainMessage();
	                  msg.what = 1;
	                  handler.sendMessage(msg);
	               }
	            });
	            Looper.loop();
	         } else {
	            handler.post(new Runnable() {

	               @Override
	               public void run() {
	                  // TODO Auto-generated method stub
	                  Message msg = handler.obtainMessage();
	                  msg.arg1 = 2;
	                  handler.sendMessage(msg);
	               }
	            });
	         }
	      }
	   }

	   public ArrayList<HashMap<String, Object>> setDataBody(
	         JSONObject mainJsonObject) throws JSONException {
	      ar = new ArrayList<HashMap<String, Object>>();
	      map = new HashMap<String, Object>();

	      JSONObject j = mainJsonObject;

	      String check_Id = j.getString("check_Id");
	      map.put("check_Id", check_Id);

	      Log.v("check", check_Id);
	      checkString = check_Id;

	      ar.add(map);

	      return ar;
	   }

	
	//백버튼 눌럿을시 LoginPage로 가기
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0) {
			Intent intent = new Intent(JoinPage.this, LoginPage_normal_.class);
			startActivity(intent);
			finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
