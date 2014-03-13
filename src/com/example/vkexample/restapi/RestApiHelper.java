package com.example.vkexample.restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.example.vkexample.vk.api.helper.VKHTTPConstants;

import android.util.Log;

public class RestApiHelper {
    protected static final String TAG = RestApiHelper.class.getSimpleName();
    private static final int CONN_MGR_TIMEOUT = 10000;
    private static final int TIME_OUT_TIMEOUT = 5000;
    private static final int SO_TIMEOUT = 50000;

    protected RestApiHelper() {
	;
    }

    protected HttpClient initHttpClient() {
	HttpParams httpParameters = new BasicHttpParams();
	ConnManagerParams.setTimeout(httpParameters, CONN_MGR_TIMEOUT);
	HttpConnectionParams.setConnectionTimeout(httpParameters, TIME_OUT_TIMEOUT);
	HttpConnectionParams.setSoTimeout(httpParameters, SO_TIMEOUT);
	return new DefaultHttpClient(httpParameters);
    }

    public String getServerURL() {
	return "";
    }

    public ServerResponse doGet(String request) {
	HttpGet httpget = new HttpGet(getServerURL() + request);
	return sendRequest(httpget);
    }

    public ServerResponse sendRequest(HttpUriRequest request) {
	try {
	    HttpClient httpClient = initHttpClient();
	    HttpResponse response = httpClient.execute(request);
	    return responseParse(response);
	} catch (UnknownHostException e) {
	    Log.e(TAG, "UnknownHostException");
	} catch (ConnectTimeoutException e) {
	    Log.e(TAG, "Connetction timeout exception");
	} catch (Exception e) {
	    Log.e(TAG, "HTTP request exception");
	}
	return new ServerResponse(VKHTTPConstants.NETWORK_PROBLEM, null);
    }

    private String convertStreamToString(InputStream instream) throws IOException {
	BufferedReader r = new BufferedReader(new InputStreamReader(instream));
	StringBuilder total = new StringBuilder("");
	String line = "";
	while ((line = r.readLine()) != null) {
	    total.append(line);
	}
	return total.toString();
    }

    protected ServerResponse responseParse(HttpResponse response) {
	String result = null;
	try {
	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		InputStream instream = entity.getContent();
		result = convertStreamToString(instream);
		instream.close();
	    }
	    switch (response.getStatusLine().getStatusCode()) {
	    case 200:
		return new ServerResponse(VKHTTPConstants.HTTP_OK, result);
	    }
	} catch (ClientProtocolException e) {
	    Log.e(TAG, "Client protocol exception");
	} catch (IOException e) {
	    Log.e(TAG, "IO Exception");
	} catch (Exception e) {
	    Log.e(TAG, "Exception");
	}
	return new ServerResponse(VKHTTPConstants.NETWORK_PROBLEM, result);
    }
}
