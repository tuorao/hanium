package kr.co.makeit.hanium.main;

import java.io.BufferedReader;  
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import kr.co.makeit.hanium.domain.Account;
import kr.co.makeit.hanium.domain.Facility;
import kr.co.makeit.hanium.gps.GPSTracker;
import kr.co.makeit.hanium.list.ListViewActivity_;
import kr.co.makeit.hanium.map.MapActivity_;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_mainview)
public class MainViewActivity extends Activity {
	Account account;
	@ViewById
	TextView textView1;

	GPSTracker gps;
	ArrayList<? extends Parcelable> fac = new ArrayList<Facility>();

	@AfterViews
	void ui() {
		setUi();

	}

	private void setUi() {

		Intent i = getIntent();
		account = (Account) i.getExtras().getSerializable("Account");

		textView1.setText(account.getMemName() + "이 로그인함");

		gps = new GPSTracker(MainViewActivity.this);
		if (!gps.canGetLocation()) {
			gps.showSettingsAlert();
		}

		back();
	}

	@Background
	void back() {

		String strUrl = "http://14.63.215.43/facList.do";
		String str = "", result = "";
		BufferedReader reader;
		InputStream inputStream = null;

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(strUrl);

			HttpResponse response;
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();

			reader = new BufferedReader(new InputStreamReader(inputStream,
					"utf-8"), 8);

			while ((str = reader.readLine()) != null) {
				result += str;
			}
			Log.i("로그", result);
			ObjectMapper mapper = new ObjectMapper();
			fac = mapper.readValue(result, mapper.getTypeFactory()
					.constructCollectionType(List.class, Facility.class));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Click({ R.id.btn_map, R.id.btn_board })
	void click(View v) {
		switch (v.getId()) {
		case R.id.btn_map:

			Intent intent = new Intent(getApplicationContext(),
					MapActivity_.class);
			intent.putExtra("lat", String.valueOf(gps.getLatitude()));
			intent.putExtra("lon", String.valueOf(gps.getLongitude()));
			intent.putExtra("Account", account);
			intent.putParcelableArrayListExtra("fac", fac);
			startActivity(intent);
			break;
		case R.id.btn_board:
			Intent intent2 = new Intent(getApplicationContext(),
					ListViewActivity_.class);
			intent2.putExtra("mylat", String.valueOf(gps.getLatitude()));
			intent2.putExtra("mylon", String.valueOf(gps.getLongitude()));
			intent2.putExtra("Account", account);
			intent2.putParcelableArrayListExtra("fac", fac);
			startActivity(intent2);
			break;

		}
	}
	
	// 추가자 : 이강산
	// 현우형꺼랑 꼬일까봐 위에 추가안하고 xml에 클릭 달아서 넣었어 참고 
	public void onInfoClick(View v){
		Intent intent = new Intent(getApplicationContext(), HowToActivity.class);
		startActivity(intent);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("앱 종료 메시지")
			.setMessage("앱을 종료하시겠습니까?")
			.setPositiveButton("예", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			})
			.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
		return super.onKeyDown(keyCode, event);
	}

}
