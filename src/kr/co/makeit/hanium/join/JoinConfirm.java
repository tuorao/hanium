package kr.co.makeit.hanium.join;


import java.util.HashMap;  

import kr.co.makeit.hanium.login.LoginPage_normal_;
import kr.co.makeit.hanium.main.R;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.il_join_confirm)
public class JoinConfirm extends Activity{

	@ViewById
	TextView il_userid, joinment;
	@ViewById
	Button join_login_btn;
	
	String memId, memBirth, pass, memName, memNickName, gender, memProfile;
	HashMap<String, String> il_properties;
	
	@AfterViews
	void ui(){
		Intent i = getIntent();
		memId = i.getExtras().getString("memId");
		pass = i.getExtras().getString("pass");
		memBirth = i.getExtras().getString("memBirth");
		memName = i.getExtras().getString("memNickName");
		gender = i.getExtras().getString("gender");
		memProfile = i.getExtras().getString("memProfile");
		
		getProperties();
		
		
	}
	
	public HashMap<String, String> getProperties() {


		final String memIdValue = memId;
		final String passValue = pass;
		final String memNameValue = memName;
		final String memNickNameValue = memNickName;
		final String memBirthValue = memBirth;
		final String genderValue = gender;
		final String memProfileValue = memProfile;

		il_properties = new HashMap<String, String>();

		if (memIdValue != null)
			il_properties.put("memId", memIdValue);
		if (passValue != null)
			il_properties.put("pass", passValue);
		if (memNameValue != null)
			il_properties.put("memName", memNameValue);
		if (memNickNameValue != null)
			il_properties.put("memNickName", memNickNameValue);
		if (memBirthValue != null)
			il_properties.put("memBirth", memBirthValue);
		if (genderValue != null)
			il_properties.put("gender", genderValue);
		if (memProfileValue != null)
			il_properties.put("memProfile", memProfileValue);

		return il_properties;
	}
	
	@Click(R.id.join_login_btn)
	void click(View v){
		switch(v.getId()){
		case R.id.join_login_btn:
			Intent i2 = new Intent(getApplicationContext(), LoginPage_normal_.class);
			startActivity(i2);
			finish();
		}
	}
}
