package kr.co.makeit.hanium.board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kr.co.makeit.hanium.domain.Account;
import kr.co.makeit.hanium.domain.Board_domain;
import kr.co.makeit.hanium.domain.Comment;
import kr.co.makeit.hanium.domain.Facility;
import kr.co.makeit.hanium.main.R;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_writeview_)
public class WriteView extends Activity{

	@ViewById
	TextView title_view, content_view, mem_nick_view;
	@ViewById
	Button view_list_btn, change_btn;
	@ViewById
	ListView list_comment;
	@ViewById
	EditText com_edit;
	List<Comment> com;

	Board_domain board;
	Account account;
	List<Facility> sel_fac,fac;
	int num, gooboon;
	String lat, lon;
	
	CommentAdapter adapter;
	Thread thread;
	
	@AfterViews
	void ui(){
		setui();
	}
	
	private void setui(){
		board = (Board_domain) getIntent().getExtras().getSerializable("board");
		account = (Account) getIntent().getExtras().getSerializable("Account");
		fac = getIntent().getParcelableArrayListExtra("fac");
		sel_fac = getIntent().getParcelableArrayListExtra("sel_fac");
		num = getIntent().getExtras().getInt("num");
		lat = getIntent().getExtras().getString("lat");
		lon = getIntent().getExtras().getString("lon");
		gooboon = getIntent().getExtras().getInt("gooboon");
		
		if(account.getMemSrl() != board.getMemSrl()){
			change_btn.setVisibility(View.INVISIBLE);
		}
		
		mem_nick_view.setText(account.getMemName());
		
		title_view.setText(board.getbName());
		content_view.setText(board.getbBody());
		
		thread = new ViewComment(String.valueOf(board.getbSrl()));
		thread.start();

		if(thread != null && thread.isAlive())
			thread.isInterrupted();
		
		Log.i("찍어", board.getbSrl()+"");

	}
	
	class ViewComment extends Thread {

		String str = "";

		public ViewComment(String str) {
			// TODO Auto-generated constructor stub
			this.str = str;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();

			BufferedReader reader;
			String strUrl = "http://14.63.215.43/listComment.do?bSrl=" + str;
			URL url;

			String str1 = "", result = "";

			try {
				url = new URL(strUrl);
				reader = new BufferedReader(new InputStreamReader(
						url.openStream()));

				while ((str1 = reader.readLine()) != null) {
					result += str1;
				}
				
				Log.i("코멘트리스트", result);

				ObjectMapper mapper = new ObjectMapper();
				com = mapper.readValue(result, mapper.getTypeFactory()
						.constructCollectionType(List.class, Comment.class));

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					adapter = new CommentAdapter(getApplicationContext(),
							R.layout.comment, com);
					list_comment.setAdapter(adapter);
				}
			});
		}
	}


	
	class CommentPost extends Thread {
		String body = String.valueOf(com_edit.getText());
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			String url = "http://14.63.215.43/wirteComment.do";


			try {
				HttpClient http = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);

				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("bSrl", String.valueOf(board.getbSrl())));
				nameValuePairs.add(new BasicNameValuePair("cBody", body));
				nameValuePairs.add(new BasicNameValuePair("memSrl", String.valueOf(account.getMemSrl())));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
				http.execute(httpPost);
			} catch(IOException e){
				e.printStackTrace();
			}
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Log.i("찎혀라", board.getbSrl()+"    "+ body+ "    " + account.getMemSrl());
				}
			});
			
		}
	}
	
	class CommentAdapter extends ArrayAdapter<Comment> {

		private List<Comment> com;

		public CommentAdapter(Context context, int resource,
				List<Comment> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			com = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = convertView;
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.row, null);
			}

			if (com.get(position) != null) {
				TextView num_view = (TextView) v.findViewById(R.id.num_view);
				TextView mem_view = (TextView) v.findViewById(R.id.mem_view);
				TextView title_view = (TextView) v
						.findViewById(R.id.title_view);
				Button del_btn = (Button) v.findViewById(R.id.del_btn);
				if (account.getMemSrl() != com.get(position).getMemSrl())
					del_btn.setVisibility(View.INVISIBLE);
				num_view.setText(com.get(position).getbSrl() + "");
//				mem_view.setText("작성자");
				title_view.setText(com.get(position).getcBody());

			}

			return v;
		}

	}
	
	@Click({R.id.view_list_btn, R.id.change_btn, R.id.com_btn})
	void click(View v){
		switch(v.getId()){
		case R.id.view_list_btn:
			Intent i = new Intent(getApplicationContext(), BoardActivity_.class);
			i.putExtra("go", 1);
			i.putParcelableArrayListExtra("sel_fac", (ArrayList<? extends Parcelable>) sel_fac);
			i.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
			i.putExtra("Account", account);
			i.putExtra("num", num);
			i.putExtra("lat", lat);
			i.putExtra("lon", lon);
			i.putExtra("gooboon", gooboon);
			startActivity(i);
			finish();
			break;
		case R.id.com_btn:
			Thread thread1 = new CommentPost();

			thread1.start();
			
			Intent i2 = new Intent(getApplicationContext(), WriteView_.class);
			i2.putExtra("go", 1);
			i2.putExtra("num", num);
			i2.putExtra("lat", lat);
			i2.putExtra("lon", lon);
			i2.putExtra("gooboon", gooboon);
			i2.putExtra("Account", account);
			i2.putExtra("board", board);
			i2.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
			i2.putParcelableArrayListExtra("sel_fac", (ArrayList<? extends Parcelable>) sel_fac);
			startActivity(i2);
			finish();
		case R.id.change_btn:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode== KeyEvent.KEYCODE_BACK){
			Intent i = new Intent(getApplicationContext(), BoardActivity_.class);
			i.putExtra("go", 1);
			i.putParcelableArrayListExtra("sel_fac", (ArrayList<? extends Parcelable>) sel_fac);
			i.putParcelableArrayListExtra("fac", (ArrayList<? extends Parcelable>) fac);
			i.putExtra("Account", account);
			i.putExtra("num", num);
			i.putExtra("lat", lat);
			i.putExtra("lon", lon);
			i.putExtra("gooboon", gooboon);
			startActivity(i);
			finish();
		}
		return false;
	}
	

	

}
