package kr.co.makeit.hanium.map;

import java.util.ArrayList;  
import java.util.List;

import kr.co.makeit.hanium.board.BoardActivity_;
import kr.co.makeit.hanium.domain.Account;
import kr.co.makeit.hanium.domain.Facility;
import kr.co.makeit.hanium.group.GroupList;
import kr.co.makeit.hanium.group.MakeGroupActivity_;
import kr.co.makeit.hanium.main.MainViewActivity_;
import kr.co.makeit.hanium.main.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_map)
public class MapActivity extends FragmentActivity implements
		OnMapLongClickListener, OnMarkerClickListener,
		OnInfoWindowClickListener, OnItemSelectedListener {
	GoogleMap map;
	MarkerOptions marker = new MarkerOptions();
	List<Facility> fac = new ArrayList<Facility>(); 
	List<Facility> sel_fac = new ArrayList<Facility>();
	Account account;
	double lat, lon;
	GroupList gl = new GroupList();
	@ViewById
	Spinner spinner_list;
	String[] option;
	ArrayAdapter<String> arrayAdapter;

	@AfterViews
	void ui() {
		mapView();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	private void mapView() {
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapexam)).getMap();
		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.zoomTo(17));
		map.setOnMapLongClickListener(this);
		map.setOnMarkerClickListener(this);
		map.setOnInfoWindowClickListener(this);

		Intent intent = getIntent();
		lat = Double.valueOf(intent.getExtras().getString("lat"));
		lon = Double.valueOf(intent.getExtras().getString("lon"));
		account = (Account) intent.getExtras().getSerializable("Account");
		fac = intent.getParcelableArrayListExtra("fac");
		
		option = getResources().getStringArray(R.array.icon_array);
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, option);
		spinner_list.setAdapter(arrayAdapter);
		spinner_list.setOnItemSelectedListener(this);
		
		LatLng myLatLng = new LatLng(lat, lon);
		CameraPosition cp = CameraPosition.builder().target(myLatLng).zoom(15).build();
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
		
	}




	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		int i = 0;

		for (int n = 0; n < fac.size(); n++) {
			if (marker.getTitle().equals(fac.get(n).getFacName())) {
				i = n;
				break;
			}
		}
		final int a = i;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(fac.get(a).getFacName())
				.setMessage(fac.get(a).getFacintro())
				.setCancelable(false)
				.setPositiveButton("모임 페이지 보기",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(
										getApplicationContext(),
										BoardActivity_.class);
								intent.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
								intent.putParcelableArrayListExtra("sel_fac", (ArrayList<? extends Parcelable>) sel_fac);
								intent.putExtra("Account", account);
								intent.putExtra("num", a);
								intent.putExtra("gooboon", 1);
								intent.putExtra("lat", String.valueOf(lat));
								intent.putExtra("lon", String.valueOf(lon));
								startActivity(intent);
								finish();

							}
						})
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onMapLongClick(LatLng lt) {
		// TODO Auto-generated method stub
		final String lon = String.valueOf(lt.longitude);
		final String lat = String.valueOf(lt.latitude);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("그룹 만들기")
				.setCancelable(false)
				.setPositiveButton("네",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(
										getApplicationContext(),
										MakeGroupActivity_.class);
								intent.putExtra("Account", account);
								intent.putExtra("longitude", lon);
								intent.putExtra("latitude", lat);
								intent.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
								startActivity(intent);
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i = new Intent(getApplicationContext(), MainViewActivity_.class);
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
		if(map != null){
			map.clear();
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
		
		Log.i("왜안돼", sel_fac.size()+"");
		
		for (int n = 0; n < sel_fac.size(); n++) {
			LatLng latlng = new LatLng(sel_fac.get(n).getLat(), sel_fac.get(n)
					.getLon());
			marker.position(latlng);
			marker.title(sel_fac.get(n).getFacName());
			gl.setIcon(sel_fac.get(n).getFaccate());
			marker.icon(BitmapDescriptorFactory
					.fromResource(gl.getIcon()));
			marker.snippet("대화창 클릭");
			map.addMarker(marker);
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
