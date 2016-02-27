package kr.co.makeit.hanium.board;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.co.makeit.hanium.main.R;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Gall extends Fragment implements OnClickListener {

	private FileInputStream mFileInputStream = null;
	private URL connectUrl = null;

	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary = "*****";
	ProgressDialog dialog = null;
	String upLoadServerUri = "http://14.63.215.43/test.jin";
	HttpURLConnection conn = null;
	DataOutputStream dos = null;
	int serverResponseCode = 0;

	Button btn_find, btn_send;
	TextView txt;
	ListView list_photo;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.gall, container, false);
		btn_find = (Button) view.findViewById(R.id.btn_find);
		txt = (TextView) view.findViewById(R.id.view_route);
		btn_send = (Button) view.findViewById(R.id.btn_send);
		txt.setText("sibal");
//		list_photo = (ListView) view.findViewById(R.id.list_photo);

		btn_find.setOnClickListener(this);
		btn_send.setOnClickListener(this);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		return view;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_find:
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, 1);
			break;
		case R.id.btn_send:
			Intent i = new Intent(getActivity(), BoardActivity_.class);
			i.putExtra("go", 2);

			dialog = ProgressDialog.show(getActivity(), "", "업로드 중..", true);

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// uploadFile(absolutePath);
				}
			});
			startActivity(i);
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		if (requestCode == 1) {
			Uri selPhotoUri = intent.getData();
			Log.i("uri", selPhotoUri.toString());
			Log.i("qwe", Environment.getExternalStorageDirectory()
					.getAbsolutePath());
			String urlString = "http://14.63.215.43/test.jin";

			Cursor c = getActivity().getContentResolver().query(
					Uri.parse(selPhotoUri.toString()), null, null, null, null);
			c.moveToNext();
			String absolutePath = c.getString(c
					.getColumnIndex("_data"));
			c.close();

			txt.setText(c.toString());

			//
			// new Thread(new Runnable() {
			//
			// @Override
			// public void run() {
			// // TODO Auto-generated method stub
			// uploadFile(absolutePath);
			// }
			// }).start();
		}

	}

	public int uploadFile(String sourceFileUri) {

		String fileName = sourceFileUri;

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {

			dialog.dismiss();

			Log.e("uploadFile", "Source File not exist :" + sourceFileUri);

			return 0;

		} else {
			try {

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(upLoadServerUri);

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("file", fileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();

				Log.i("uploadFile", "HTTP Response is : "
						+ serverResponseMessage + ": " + serverResponseCode);

				if (serverResponseCode == 200) {

					getActivity().runOnUiThread(new Runnable() {
						public void run() {

							String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
									+ " http://www.androidexample.com/media/uploads/"
									+ "ASD";

							Toast.makeText(
									getActivity().getApplicationContext(),
									"File Upload Complete.", Toast.LENGTH_SHORT)
									.show();
						}
					});
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (Exception e) {

				dialog.dismiss();
				e.printStackTrace();

				getActivity().runOnUiThread(new Runnable() {
					public void run() {

						Toast.makeText(getActivity().getApplicationContext(),
								"Got Exception : see logcat ",
								Toast.LENGTH_SHORT).show();
					}
				});
				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);
			}
			dialog.dismiss();
			return serverResponseCode;

		} // End else block
	}
	
	

}
