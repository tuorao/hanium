package kr.co.makeit.hanium.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.accounts.Account;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EBean;

@EBean
public class MakeitConn {
	private final String BASE_URL = "http://14.63.215.43/";
	
	public void login(String id, String pw){
		String url = "login.do";
		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
		post.add(new BasicNameValuePair("id", id));
		post.add(new BasicNameValuePair("pw", pw));
		postParse(post, url);
		
	}
//	public void join(Account account){
//		String url = "join.do";
//		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
//		post.add(new BasicNameValuePair("id", account.getId()));
//		post.add(new BasicNameValuePair("pw", account.getPw()));
//		post.add(new BasicNameValuePair("name", account.getName()));
//		
//		postParse(post, url);
//	}
	
	@SuppressWarnings("null")
	@Background
	void postParse(ArrayList<NameValuePair> post, String url){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(BASE_URL + url);
		
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 3000);
		
		HttpResponse httpResponse = null;
		
		try {
			UrlEncodedFormEntity encodedFormEntity;
			encodedFormEntity = new UrlEncodedFormEntity(post, "utf-8");
			httpPost.setEntity(encodedFormEntity);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(),"utf-8"));
			
			String line = null;
			String result = "";
			while((line = bufferedReader.readLine()) != null) {
				result += line;
			}
			
			com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
			@SuppressWarnings("unused")
			List<Account> account = mapper.readValue(result, mapper.getTypeFactory().constructCollectionType(List.class, Account.class));
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (IOException er) {
			// TODO Auto-generated catch block
			er.printStackTrace();
		}
		
		
	}
}
