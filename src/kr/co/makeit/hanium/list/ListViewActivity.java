package kr.co.makeit.hanium.list;

import java.text.Collator; 
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import kr.co.makeit.hanium.board.BoardActivity_;
import kr.co.makeit.hanium.domain.Account;
import kr.co.makeit.hanium.domain.Facility;
import kr.co.makeit.hanium.group.GroupList;
import kr.co.makeit.hanium.main.MainViewActivity_;
import kr.co.makeit.hanium.main.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_list)
public class ListViewActivity extends Activity implements OnItemClickListener,
		OnItemSelectedListener {

	List<Facility> fac = new ArrayList<Facility>();
	List<Facility> save_fac = new ArrayList<Facility>();
	List<Facility> sel_fac = new ArrayList<Facility>();
	List<Facility> save2_fac = new ArrayList<Facility>();
	Distance dist = new Distance();
	Account account;
	FacAdapter adapter, adapter2;
	double mylon;
	double mylat;
	TextView group_name, category, distance_view;
	GroupList gl = new GroupList();
	String[] option;
	ArrayAdapter<String> arrayAdapter;

	@ViewById
	ListView list_group;
	@ViewById
	Spinner spinner_list;

	@AfterViews
	void ui() {
		list();
	}

	private void list() {
		Intent i = getIntent();
		mylon = Double.parseDouble(i.getExtras().getString("mylon"));
		mylat = Double.parseDouble(i.getExtras().getString("mylat"));
		fac = i.getParcelableArrayListExtra("fac");
		account = (Account) i.getExtras().getSerializable("Account");

		option = getResources().getStringArray(R.array.icon_array);
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, option);
		spinner_list.setAdapter(arrayAdapter);
		spinner_list.setOnItemSelectedListener(this);

	}

	public Comparator<Facility> myComparator = new Comparator<Facility>() {

		public Collator collator = Collator.getInstance();

		@Override
		public int compare(Facility lhs, Facility rhs) {
			// TODO Auto-generated method stub

			int go = (int) dist.cal(mylat, mylon, lhs.getLat(), lhs.getLon());
			int away = (int) dist.cal(mylat, mylon, rhs.getLat(), rhs.getLon());
			String str_go = String.valueOf(go);
			String str_away = String.valueOf(away);

			int dis_go = str_go.getBytes().length;
			int dis_away = str_away.getBytes().length;

			switch (dis_go) {
			case 1:
				str_go = "00000" + str_go;
				break;
			case 2:
				str_go = "0000" + str_go;
				break;
			case 3:
				str_go = "000" + str_go;
				break;
			case 4:
				str_go = "00" + str_go;
				break;
			case 5:
				str_go = "0" + str_go;
				break;
			case 6:
				break;
			}

			switch (dis_away) {
			case 1:
				str_away = "00000" + str_away;
				break;
			case 2:
				str_away = "0000" + str_away;
				break;
			case 3:
				str_away = "000" + str_away;
				break;
			case 4:
				str_away = "00" + str_away;
				break;
			case 5:
				str_away = "0" + str_away;
				break;
			case 6:
				break;
			}

			return collator.compare(str_go, str_away);
		}
	};

	private class FacAdapter extends ArrayAdapter<Facility> {

		private Facility u;
		private List<Facility> ul;

		public FacAdapter(Context context, int resource, List<Facility> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			this.ul = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			u = ul.get(position);

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.listview_row, null);
			}

			if (ul != null) {
				group_name = (TextView) convertView
						.findViewById(R.id.group_name);
				category = (TextView) convertView
						.findViewById(R.id.distance_view);
				distance_view = (TextView) convertView
						.findViewById(R.id.distance_view);

				group_name.setText(u.getFacName()); // 그룹명
				category.setText(u.getFaccate() + ""); // 카테고리
				int row = (int) dist.cal(mylat, mylon, u.getLat(), u.getLon());
				double changed_row = (double) row / 1000;
				DecimalFormat df = new DecimalFormat("##.#");

				distance_view.setText(df.format(changed_row) + "km");
			}
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(),
				BoardActivity_.class);
		intent.putParcelableArrayListExtra("fac",
				(ArrayList<? extends Parcelable>) fac);
		intent.putParcelableArrayListExtra("sel_fac", (ArrayList<? extends Parcelable>) sel_fac);
		intent.putExtra("lon", String.valueOf(mylon));
		intent.putExtra("lat", String.valueOf(mylat));
		intent.putExtra("Account", account);
		intent.putExtra("gooboon", 0);
		intent.putExtra("num", position);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent(getApplicationContext(),
					MainViewActivity_.class);
			i.putExtra("Account", account);
			startActivity(i);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (sel_fac != null) {
			sel_fac.clear();
		}

		String a = (String) spinner_list.getAdapter().getItem(
				spinner_list.getSelectedItemPosition());
		gl.setNum(a);
		int num = gl.getNum();

		if (num != 0) {
			for (int i = 0; i < fac.size(); i++) {
				if (fac.get(i).getFaccate() == num) {
					sel_fac.add(fac.get(i));
				}
			}
		} else if (num ==0){
			for(int j = 0; j < fac.size(); j++){
				sel_fac.add(fac.get(j));
			}
		}
		
		Collections.sort(sel_fac, myComparator);

		adapter = new FacAdapter(getApplicationContext(),
				R.layout.listview_row, sel_fac);
		list_group.setAdapter(adapter);
		list_group.setOnItemClickListener(this);


	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
