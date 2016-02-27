package kr.co.makeit.hanium.board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommentView extends Fragment implements OnClickListener{

	View view;
	Button com_btn;
	ListView list_comment;
	TextView mem_nick_view;
	EditText com_edit;
	kr.co.makeit.hanium.domain.Account account;
	List<Facility> fac, sel_fac;
	int num, gooboon;
	String lat, lon;
	
	CommentAdapter adapter;
	List<Comment> com;
	Board_domain board;
	Thread thread;
	Thread thread1 = new CommentPost();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try{
		view = inflater.inflate(R.layout.commentview_, container, false);
		} catch(InflateException e){
			Log.i("몰라시발", "ASDASDASD");
		}
		com_btn = (Button) view.findViewById(R.id.com_btn);
		list_comment = (ListView) view.findViewById(R.id.list_comment);
		mem_nick_view = (TextView) view.findViewById(R.id.mem_nick_view);
		com_edit = (EditText) view.findViewById(R.id.com_edit);
		
		com_btn.setOnClickListener(this);

		account = (kr.co.makeit.hanium.domain.Account) getActivity()
				.getIntent().getExtras().getSerializable("Account");
		fac = getActivity().getIntent().getParcelableArrayListExtra("fac");
		sel_fac = getActivity().getIntent().getParcelableArrayListExtra("sel_fac");
		num = getActivity().getIntent().getExtras().getInt("num");
		lat = getActivity().getIntent().getExtras().getString("lat");
		lon = getActivity().getIntent().getExtras().getString("lon");
		gooboon = getActivity().getIntent().getExtras().getInt("gooboon");
		board = (Board_domain) getActivity().getIntent().getExtras().getSerializable("board");
		
		thread = new ViewComment(String.valueOf(board.getbSrl()));
		thread.start();
		if(thread != null && thread.isAlive())
			thread.isInterrupted();
		
		Log.i("찍어", board.getbSrl()+"");

		return view;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if(view != null){
			ViewGroup parent = (ViewGroup) view.getParent();
			if(parent != null){
				parent.removeView(view);
			}
		}
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
			getActivity().runOnUiThread(setCom);
		}
	}

	private Runnable setCom = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			adapter = new CommentAdapter(getActivity(),
					R.layout.comment, com);
			list_comment.setAdapter(adapter);
		}
	};
	
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
			getActivity().runOnUiThread(new Runnable() {
				
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
				mem_view.setText("작성자");
				title_view.setText(com.get(position).getcBody());

			}

			return v;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.com_btn:
			thread1.start();

			
			Intent i2 = new Intent(getActivity(), WriteView_.class);
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
			getActivity().finish();
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(thread1 != null && thread1.isAlive())
			thread1.interrupt();
	}
	
}
