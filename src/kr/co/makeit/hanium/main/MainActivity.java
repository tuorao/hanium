package kr.co.makeit.hanium.main;

import kr.co.makeit.hanium.login.LoginPage_normal_;
import android.app.Activity;
import android.content.Intent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;


@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {
	
	@AfterViews
	void ui() {
		try {
			Thread.sleep(1000);
			Intent intent = new Intent(getApplicationContext(), LoginPage_normal_.class);
			startActivity(intent);
			finish();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
