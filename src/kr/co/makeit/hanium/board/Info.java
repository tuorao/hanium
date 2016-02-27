package kr.co.makeit.hanium.board;

import java.util.ArrayList;
import java.util.List;

import kr.co.makeit.hanium.domain.Account;
import kr.co.makeit.hanium.domain.Facility;
import kr.co.makeit.hanium.group.GroupJoinActivity_;
import kr.co.makeit.hanium.main.R;
import kr.co.makeit.hanium.map.MapActivity_;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class Info extends Fragment implements OnClickListener{
	Account account;
	TextView open_group_date, mem_count, notice, like_num, group_intro, push_txt, join_condition;
	Button go_map_btn, join_group_btn , input_info_btn;
	Switch switch1;
	List<Facility> fac;
	List<Facility> sel_fac;
	String lat,lon;
	int num;
	
	// 아이콘 받아오기, 푸쉬 알림

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.info, container,false);
		getIntents();
		setFindViewId(view);

		go_map_btn.setOnClickListener(this);
		join_group_btn.setOnClickListener(this);
		input_info_btn.setOnClickListener(this);
		
		if(account.getMemSrl() != fac.get(num).getMemSrl()){
			input_info_btn.setVisibility(View.INVISIBLE);
		}else {
			switch1.setVisibility(View.INVISIBLE);
			push_txt.setVisibility(View.INVISIBLE);
		}
		

		return view;
	}
	
	void getIntents(){
		account = (Account) getActivity().getIntent().getExtras().getSerializable("Account");
		fac = getActivity().getIntent().getParcelableArrayListExtra("fac");
		sel_fac = getActivity().getIntent().getParcelableArrayListExtra("sel_fac");
		num = getActivity().getIntent().getExtras().getInt("num");
		lat = getActivity().getIntent().getExtras().getString("lat");
		lon = getActivity().getIntent().getExtras().getString("lon");
	}
	
	void setFindViewId(View view){
		push_txt = (TextView) view.findViewById(R.id.push_txt);
		open_group_date = (TextView) view.findViewById(R.id.open_group_date);
		mem_count = (TextView) view.findViewById(R.id.mem_count);
		notice = (TextView) view.findViewById(R.id.notice);
		like_num = (TextView) view.findViewById(R.id.like_num);
		group_intro = (TextView) view.findViewById(R.id.group_intro);
		join_condition = (TextView) view.findViewById(R.id.join_condition);
		switch1 = (Switch) view.findViewById(R.id.switch1);
		go_map_btn = (Button) view.findViewById(R.id.go_map_btn);
		join_group_btn = (Button) view.findViewById(R.id.join_group_btn);
		input_info_btn = (Button) view.findViewById(R.id.input_info_btn);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.go_map_btn:
			Intent i = new Intent(getActivity(), MapActivity_.class);
			i.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
			i.putExtra("Account", account);
			i.putExtra("num", num);
			i.putExtra("lat", lat);
			i.putExtra("lon", lon);
			startActivity(i);
			break;


		case R.id.join_group_btn: //그룹 가입은 어떻게 처리할까??
			Intent i2 = new Intent(getActivity(), GroupJoinActivity_.class);
			i2.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
			i2.putExtra("Account", account);
			i2.putExtra("num", num);
			i2.putExtra("lat", lat);
			i2.putExtra("lon", lon);
			startActivity(i2);
			break;
		case R.id.input_info_btn: //info 변경 권한은 모임 생성한놈이랑 로그인정보가 일치하면 버튼 보이게해
			
		}
		
	}
	

}

