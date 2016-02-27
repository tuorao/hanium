package kr.co.makeit.hanium.login;

import kr.co.makeit.hanium.main.R;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.editpasspage)
public class EditPass extends Activity {

	@ViewById
	EditText curpass, newpass, newpass2;
	@ViewById
	Button edit_canbtn, edit_okbtn;
	
	String curpass_value, newpass_value, newpass2_value;
	
	@Click({R.id.edit_canbtn,R.id.edit_okbtn})
	void click(View v){
		switch(v.getId()){
		case R.id.edit_canbtn:
			Intent i = new Intent(getApplicationContext(), LoginPage_normal_.class);
			startActivity(i);
			finish();
			break;
		case R.id.edit_okbtn:
			if(newpass_value.equals(newpass2_value)) {
//				Intent i2 = new Intent(getApplicationContext(),EditPassPost_.class);
//				i2.putExtra("curpass", curpass_value);
//				i2.putExtra("newpass", newpass_value);
//				startActivity(i2);
				finish();
			} else Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
