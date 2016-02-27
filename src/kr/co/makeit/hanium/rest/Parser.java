package kr.co.makeit.hanium.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class Parser {
	String result, line;
	
	public String PostParse (ArrayList<NameValuePair> post, String url) {
		result = "";
		line = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		/* 타임아웃 설정 */
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 3000);
		
		HttpResponse httpResponse;
		try {
			/* POST 파라미터를 UTF-8로 저장 */
			UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(post, "UTF-8");
			httpPost.setEntity(encodedFormEntity);
			httpResponse = client.execute(httpPost);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
			
			while((line = bufferedReader.readLine()) != null){
				result += line;
			}

			return result;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}