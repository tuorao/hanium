package kr.co.makeit.hanium.group;

import java.io.IOException;  
import java.util.ArrayList;
import java.util.List;

import kr.co.makeit.hanium.domain.Account;
import kr.co.makeit.hanium.domain.Facility;
import kr.co.makeit.hanium.main.R;
import kr.co.makeit.hanium.map.MapActivity_;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_group)
public class MakeGroupActivity extends Activity implements OnItemSelectedListener{

	String longitude,latitude;

	@ViewById
	EditText edit1,edit2;
	@ViewById
	Spinner spinner1;
	Account account;
	List<Facility> fac;
	ArrayList<String> groupList;
	ArrayList<GroupList> array = new ArrayList<GroupList>();

	@AfterViews
	void ui(){
		setUi();
	}
	
	private void setUi(){
		
		longitude = getIntent().getExtras().getString("longitude");
		latitude = getIntent().getExtras().getString("latitude");
		account = (Account) getIntent().getExtras().getSerializable("Account");
		fac = getIntent().getParcelableArrayListExtra("fac");
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		String[] option = getResources().getStringArray(R.array.icon_array);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,option);
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(this);
		
	}
	
	@Click({R.id.button1,R.id.button2})
	void click(View v) {
		switch(v.getId()){
		case R.id.button1:
			back();
			
		}
	}
	
	@Background
	void back(){
		String title = String.valueOf(edit1.getText());
		String content = String.valueOf(edit2.getText());
		
		String url = "http://14.63.215.43/groupAdd.do";
		
		try {
			HttpClient http = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("facName",title));
			nameValuePairs.add(new BasicNameValuePair("fAdd","1"));;
			nameValuePairs.add(new BasicNameValuePair("lAdd", "1"));
			nameValuePairs.add(new BasicNameValuePair("lat", latitude));
			nameValuePairs.add(new BasicNameValuePair("lon", longitude));
			nameValuePairs.add(new BasicNameValuePair("phone", "123"));
			nameValuePairs.add(new BasicNameValuePair("facIntro", content));
			nameValuePairs.add(new BasicNameValuePair("facCate", "1313"));
			nameValuePairs.add(new BasicNameValuePair("gpName", "asd"));
			nameValuePairs.add(new BasicNameValuePair("memSrl", String.valueOf(account.getMemSrl())));
			nameValuePairs.add(new BasicNameValuePair("gpCate", "1"));
			nameValuePairs.add(new BasicNameValuePair("runTime", "12"));
			nameValuePairs.add(new BasicNameValuePair("gpIntro", "13"));
			nameValuePairs.add(new BasicNameValuePair("gpProfile", "132"));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
			http.execute(httpPost);
		} catch (IOException ex){
			ex.printStackTrace();
		}
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(spinner1.getSelectedItemPosition()>0){
			array.add(new GroupList((String)spinner1.getAdapter().getItem(spinner1.getSelectedItemPosition())));
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i = new Intent(getApplicationContext(),MapActivity_.class);
			i.putExtra("Account", account);
			i.putExtra("lat", latitude);
			i.putExtra("lon", longitude);
			i.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
			startActivity(i);
			
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
