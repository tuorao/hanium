package kr.co.makeit.hanium.join;

import java.util.Calendar;
import java.util.HashMap;

import kr.co.makeit.hanium.main.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;


@EActivity(R.layout.joinpage2)
public class JoinPage2 extends Activity implements OnDateSetListener,
		OnTouchListener {

	@ViewById
	EditText il_memBirth, il_memName, il_memNickName, il_memProfile;
	@ViewById
	Button okbtn2, canbtn2;
	@ViewById
	Spinner gender;

	String memId, pass, memNickName;
	DatePickerDialog dateDialog;
	
	@AfterViews
	void ui(){
		Intent i = getIntent();
		memId = i.getExtras().getString("Id_value");
		pass = i.getExtras().getString("pass_value");
		memNickName = i.getExtras().getString("NickName");
		
		il_memBirth.setOnTouchListener(this);
	}

	public HashMap<String, String> il_getProperties() {

		final String memIdValue = memId;
		final String passValue = pass;
		final String memNameValue = memNickName;
		final String memNickNameValue = String.valueOf(il_memNickName);
		final String memBirthValue = String.valueOf(il_memBirth);
		final String genderValue = String.valueOf(gender.getSelectedItem());
		final String memProfileValue = String.valueOf(il_memProfile);

		HashMap<String, String> il_properties = new HashMap<String, String>();

		if (memIdValue != null)
			il_properties.put("memId", memIdValue);
		if (passValue != null)
			il_properties.put("pass", passValue);
		if (memNameValue != null)
			il_properties.put("il_memName", memNameValue);
		if (memNickNameValue != null)
			il_properties.put("il_memNickName", memNickNameValue);
		if (memBirthValue != null)
			il_properties.put("il_memBirth", memBirthValue);
		if (genderValue != null)
			il_properties.put("gender", genderValue);
		if (memProfileValue != null)
			il_properties.put("il_memProfile", memProfileValue);

		return il_properties;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Calendar c = Calendar.getInstance();
			dateDialog = new DatePickerDialog(this, this, c.get(Calendar.YEAR),
					c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
			dateDialog.show();
		}
		return false;

	}

	public void btn1Event(View v) {
		dateDialog.show();
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		String str = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
		il_memBirth.setText(str);

	}

	@Click({ R.id.okbtn2, R.id.canbtn2 })
	void click(View v) {
		switch (v.getId()) {
		case R.id.okbtn2:
			final String Id_Value = memId;
			final String pass_Value = pass;
			final String memBirth_Value = il_memBirth.getText().toString();
			final String memName_Value = il_memName.getText().toString();
			final String memNickName_Value = memNickName;
			final String gender_Value = String
					.valueOf(gender.getSelectedItem());
			final String memProfile_Value = il_memProfile.getText().toString();

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("가입 정보")
					.setMessage(
							"아이디 : " + Id_Value + "\n" + "이   름 : "
									+ memName_Value + "\n" + "닉네임 : "
									+ memNickName_Value + "\n" + "생년월일 : "
									+ memBirth_Value + "\n" + "성   별 : "
									+ gender_Value)
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// TODO Auto-generated method stub

									Intent intent = new Intent(JoinPage2.this,
											JoinConfirm_.class);
									intent.putExtra("memId", Id_Value);
									intent.putExtra("pass", pass_Value);
									intent.putExtra("memBirth", memBirth_Value);
									intent.putExtra("memName", memName_Value);
									intent.putExtra("memNickName",
											memNickName_Value);
									intent.putExtra("gender", gender_Value);
									intent.putExtra("memProfile",
											memProfile_Value);

									startActivity(intent);
									System.exit(0);

								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
			builder.create();
			builder.show();
			break;
			
		case R.id.canbtn2:
			Intent intent2 = new Intent(getApplicationContext(),
					JoinPage_.class);
			startActivity(intent2);
			finish();
			break;
		}

	}

}

