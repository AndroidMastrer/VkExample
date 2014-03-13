package com.example.vkexample.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.vkexample.R;
import com.example.vkexample.vk.Constants;

import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

public class AuthActivity extends Activity {
	private static final String TAG = AuthActivity.class.getSimpleName();
	
	public static final String OUT_EXTRA_TOKEN = "token";
	public static final String OUT_EXTRA_USER_ID = "user_id";
	WebView webview;

	public static String getUrl() {
		String url = null;
		try {
			url = "https://oauth.vk.com/authorize?client_id="
					+ Constants.APP_ID + "&display=mobile&redirect_uri="
					+ URLEncoder.encode(Constants.REDIRECT_URL, "UTF-8")
					+ "&response_type=token" + "&v="
					+ URLEncoder.encode(Constants.API_VERSION, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "Couldn't encode strings!");
		}
		return url;
	}

	public static String[] parseRedirectUrl(String url) throws Exception {
		String access_token = extractPattern(url, "access_token=(.*?)&");
		String user_id = extractPattern(url, "user_id=(\\d*)");
		if (user_id == null || user_id.length() == 0 || access_token == null
				|| access_token.length() == 0)
			throw new Exception("Failed to parse redirect url " + url);
		return new String[] { access_token, user_id };
	}

	public static String extractPattern(String string, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(string);
		if (!m.find())
			return null;
		return m.toMatchResult().group(1);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_auth_layout);

		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.clearCache(true);
		webview.setWebViewClient(new VkWebViewClient());

		CookieSyncManager.createInstance(this);

		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();

		webview.loadUrl(getUrl());
	}

	class VkWebViewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			parseUrl(url);
		}
	}

	private void parseUrl(String url) {
		try {
			if (url == null)
				return;
			Log.i(TAG, "url=" + url);
			if (url.startsWith(Constants.REDIRECT_URL)) {
				if (!url.contains("error=")) {
					String[] auth = parseRedirectUrl(url);
					Intent intent = new Intent();
					intent.putExtra(OUT_EXTRA_TOKEN, auth[0]);
					intent.putExtra(OUT_EXTRA_USER_ID, Long.parseLong(auth[1]));
					setResult(Activity.RESULT_OK, intent);
				}
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
