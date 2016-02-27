package kr.co.makeit.hanium.board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.makeit.hanium.domain.Account;
import kr.co.makeit.hanium.domain.Board_domain;
import kr.co.makeit.hanium.domain.Facility;
import kr.co.makeit.hanium.main.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

public class Board extends Fragment implements OnClickListener,
		OnItemClickListener {
	Button btn_write;
	View view;
	ListView listview;
	Handler handler, handler1;
	List<Board_domain> board;
	Thread thread = new BoardTest();
	PullToRefreshListView mPullToRefreshListView;
	Account account;
	List<Facility> fac, sel_fac;
	int num, gooboon;
	String lat, lon;

	BoardAdapter adapter;
	ArrayList<String> array = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.board, container, false);
		btn_write = (Button) view.findViewById(R.id.button_write);
		btn_write.setOnClickListener(this);
		listview = (ListView) view.findViewById(R.id.listView1);
		listview.setOnItemClickListener(this);

		account = (Account) getActivity().getIntent().getExtras()
				.getSerializable("Account");
		fac = getActivity().getIntent().getParcelableArrayListExtra("fac");
		sel_fac = getActivity().getIntent().getParcelableArrayListExtra(
				"sel_fac");
		num = getActivity().getIntent().getExtras().getInt("num");
		lat = getActivity().getIntent().getExtras().getString("lat");
		lon = getActivity().getIntent().getExtras().getString("lon");
		gooboon = getActivity().getIntent().getExtras().getInt("gooboon");

		if (!thread.isAlive()) {
			thread.start();
		}
		mPullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(getActivity(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						new GetDataTask().execute();

					}
				});

		listview = mPullToRefreshListView.getRefreshableView();
		registerForContextMenu(listview);

		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(
				getActivity());
		soundListener
				.addSoundEvent(
						com.handmark.pulltorefresh.library.PullToRefreshBase.State.PULL_TO_REFRESH,
						R.raw.pull_event);
		soundListener
				.addSoundEvent(
						com.handmark.pulltorefresh.library.PullToRefreshBase.State.RESET,
						R.raw.reset_sound);
		soundListener
				.addSoundEvent(
						com.handmark.pulltorefresh.library.PullToRefreshBase.State.REFRESHING,
						R.raw.refreshing_sound);
		mPullToRefreshListView.setOnPullEventListener(soundListener);

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);

				@SuppressWarnings({ "unchecked" })
				final List<Board_domain> board = (List<Board_domain>) msg.obj;

				adapter = new BoardAdapter(getActivity(), R.layout.row, board);
				listview.setAdapter(adapter);
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Log.i("siba;", "qweqwe");
						Intent intent = new Intent(getActivity(),
								WriteView.class);
						intent.putExtra("board", board.get(position));
						intent.putExtra("Account", account);
						intent.putParcelableArrayListExtra("sel_fac",
								(ArrayList<? extends Parcelable>) sel_fac);
						intent.putParcelableArrayListExtra("fac",
								(ArrayList<? extends Parcelable>) fac);
						intent.putExtra("num", num);
						intent.putExtra("lat", lat);
						intent.putExtra("lon", lon);
						intent.putExtra("gooboon", gooboon);
						startActivity(intent);
						getActivity().finish();
					}

				});

			}

		};

		return view;

	}

	private class GetDataTask extends AsyncTask<Void, View, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			if (!thread.isAlive())
				thread.interrupt();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}

			String url = "http://14.63.215.43/groupBoardList.do";
			InputStream inputStream = null;
			BufferedReader reader;
			String str = "", result = "";

			try {
				HttpClient http = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);

				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("groupSrl", "0"));
				nameValuePairs.add(new BasicNameValuePair("pageing", "1"));

				HttpResponse httpResponse;
				UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(
						nameValuePairs, "utf-8");
				httpPost.setEntity(encodedFormEntity);

				httpResponse = http.execute(httpPost);
				HttpEntity entity = httpResponse.getEntity();
				inputStream = entity.getContent();

				reader = new BufferedReader(new InputStreamReader(inputStream,
						"utf-8"), 8);

				while ((str = reader.readLine()) != null) {
					result += str;
				}
				com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
				board = mapper.readValue(
						result,
						mapper.getTypeFactory().constructCollectionType(
								List.class, Board_domain.class));

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			adapter = new BoardAdapter(getActivity(), R.layout.row, board);
			listview.setAdapter(adapter);
			adapter.notifyDataSetChanged();

			mPullToRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_write:
			Intent intent = new Intent(getActivity(), WriteActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (thread != null && thread.isAlive()) {
			thread.interrupt();
		}
	}

	class BoardAdapter extends ArrayAdapter<Board_domain> {

		public BoardAdapter(Context context, int resource,
				List<Board_domain> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			objects = board;
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

			if (board.get(position) != null) {
				TextView num_view = (TextView) v.findViewById(R.id.num_view);
				TextView title_view = (TextView) v
						.findViewById(R.id.title_view);
				TextView writer_view = (TextView) v
						.findViewById(R.id.writer_view);

				num_view.setText(board.get(position).getbSrl() + "");

				title_view.setText(board.get(position).getbName());

				writer_view.setText(board.get(position).getMemSrl() + "");

			}
			return v;
		}

	}

	class BoardTest extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String url = "http://14.63.215.43/groupBoardList.do";
			InputStream inputStream = null;
			BufferedReader reader;
			String str = "", result = "";

			try {
				HttpClient http = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);

				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("groupSrl", "0"));
				nameValuePairs.add(new BasicNameValuePair("pageing", "1"));

				HttpResponse httpResponse;
				UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(
						nameValuePairs, "utf-8");
				httpPost.setEntity(encodedFormEntity);

				httpResponse = http.execute(httpPost);
				HttpEntity entity = httpResponse.getEntity();
				inputStream = entity.getContent();

				reader = new BufferedReader(new InputStreamReader(inputStream,
						"utf-8"), 8);

				while ((str = reader.readLine()) != null) {
					result += str;
				}
				com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
				board = mapper.readValue(
						result,
						mapper.getTypeFactory().constructCollectionType(
								List.class, Board_domain.class));

				Looper.prepare();
				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						Message msg = handler.obtainMessage();
						msg.obj = board;
						handler.sendMessage(msg);

					}
				});

				Looper.loop();

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();

			}

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

}
