package kr.co.makeit.hanium.board;

import java.util.ArrayList; 
import java.util.List;

import kr.co.makeit.hanium.domain.Account;
import kr.co.makeit.hanium.domain.Facility;
import kr.co.makeit.hanium.list.ListViewActivity_;
import kr.co.makeit.hanium.main.MainViewActivity_;
import kr.co.makeit.hanium.main.R;
import kr.co.makeit.hanium.map.MapActivity_;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_board)
public class BoardActivity extends FragmentActivity {

	@ViewById
	Button btn_join, btn_board, btn_gall, btn_mem, back_btn, home_btn;
	@ViewById
	ViewPager pager;
	@ViewById
	TextView title;
	
	String lat,lon;
	int num, gooboon;
	

	FragmentPagerAdapter adapter;
	List<Facility> fac = new ArrayList<Facility>();
	List<Facility> sel_fac = new ArrayList<Facility>();

	Account account;
	@AfterViews
	void ui() {
		setUi();
	}

	private void setUi() {

		account = (Account) getIntent().getExtras().getSerializable("Account");
		fac = getIntent().getParcelableArrayListExtra("fac");
		sel_fac = getIntent().getParcelableArrayListExtra("sel_fac");
		num = getIntent().getExtras().getInt("num");
		lat = getIntent().getExtras().getString("lat");
		lon = getIntent().getExtras().getString("lon");
		
		gooboon = getIntent().getExtras().getInt("gooboon");

		adapter = new PageAdapt(getSupportFragmentManager());
		pager.setAdapter(adapter);
		switch (getIntent().getExtras().getInt("go")) {
		case 0:
			 title.setText(sel_fac.get(num).getFacName());
			break;
		case 1:
//			Board board = new Board(); // 게시판에서 글쓰기를 받아서 처리
//			board.onCreateView(getLayoutInflater(), pager, null);
			setCurrentInflateItem(1);
			break;
		case 2:
			Gall gall = new Gall();
			gall.onCreateView(getLayoutInflater(), pager, null);
			setCurrentInflateItem(2);
			break;
		}
	}

	public void setCurrentInflateItem(int type) {
		if (type == 0) {
			pager.setCurrentItem(0);
		} else if (type == 1) {
			pager.setCurrentItem(1);
		} else if (type == 2) {
			pager.setCurrentItem(2);
		} else {
			pager.setCurrentItem(3);
		}
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	//
	// Log.i("aaa","requ = " + requestCode +",reslult" + resultCode);
	// // TODO Auto-generated method stub
	// super.onActivityResult(requestCode, resultCode, data);
	// if(resultCode==18){
	// Log.i("aa","18");
	// Board board = new Board();
	// board.onStart();
	// setCurrentInflateItem(1);
	// }
	// }

	@Click({ R.id.btn_join, R.id.btn_board, R.id.btn_gall, R.id.btn_mem, R.id.back_btn, R.id.home_btn })
	void click(View v) {
		switch (v.getId()) {
		case R.id.btn_join:
			setCurrentInflateItem(0);
			break;
		case R.id.btn_board:
			setCurrentInflateItem(1);
			break;
		case R.id.btn_gall:
			setCurrentInflateItem(2);
			break;
		case R.id.btn_mem:
			setCurrentInflateItem(3);
			break;
		case R.id.back_btn:
			if(gooboon==1){
				Intent i = new Intent(getApplicationContext(), MapActivity_.class);
				i.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
				i.putExtra("Account", account);
				i.putExtra("num", num);
				i.putExtra("lat", lat);
				i.putExtra("lon", lon);
				startActivity(i);
				finish();
			} else if (gooboon==0){
				Intent i2 = new Intent(getApplicationContext(), ListViewActivity_.class);
				i2.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
				i2.putExtra("Account", account);
				i2.putExtra("num", num);
				i2.putExtra("mylat", lat);
				i2.putExtra("mylon", lon);
				startActivity(i2);
				finish();
			}
			break;
		case R.id.home_btn:
			Intent i3 = new Intent(getApplicationContext(), MainViewActivity_.class);
			i3.putExtra("Account", account);
			startActivity(i3);
			finish();
			break;
		}
	}

	public class PageAdapt extends FragmentPagerAdapter {


		public PageAdapt(FragmentManager fragmentManager) {
			super(fragmentManager);
			// TODO Auto-generated constructor stub

		}

		@Override
		public Fragment getItem(int a) {
			// TODO Auto-generated method stub
			switch (a) {
			case 0:
				return new Info();
			case 1:
				return new Board();
			case 2:
				return new Gall();
			case 3:
				return new Mem();
			default:
				return null;
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(gooboon==1){
				Intent i = new Intent(getApplicationContext(), MapActivity_.class);
				i.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
				i.putExtra("Account", account);
				i.putExtra("num", num);
				i.putExtra("lat", lat);
				i.putExtra("lon", lon);
				startActivity(i);
				finish();
			} else if (gooboon==0){
				Intent i2 = new Intent(getApplicationContext(), ListViewActivity_.class);
				i2.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
				i2.putExtra("Account", account);
				i2.putExtra("num", num);
				i2.putExtra("mylat", lat);
				i2.putExtra("mylon", lon);
				startActivity(i2);
				finish();
			}
		}
		return false;
	}
}
