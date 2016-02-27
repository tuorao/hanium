package kr.co.makeit.hanium.board;

import kr.co.makeit.hanium.main.R;
import android.app.Activity;
import android.text.InputFilter;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_infochange)
public class InfoChangeActivity extends Activity{

	@ViewById
	EditText notice, group_intro, join_condition;
	int maxLength = 15;
	
	void setUi(){

		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(maxLength);
		
		notice = new EditText(this);
		notice.setSingleLine();
		notice.setFilters(filters);
		
		group_intro = new EditText(this);
		group_intro.setSingleLine();
		group_intro.setFilters(filters);
		
		join_condition = new EditText(this);
		join_condition.setSingleLine();
		join_condition.setFilters(filters);
	}
}
