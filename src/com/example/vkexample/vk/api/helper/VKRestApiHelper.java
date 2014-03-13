package com.example.vkexample;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class VKRestApiHelper extends RestApiHelper {
    protected static final String TAG = VKRestApiHelper.class.getSimpleName();
    public static final String EXTRA_ACCESS_TOKEN = "vk_access_token";
    public static final String EXTRA_RESULT_RECEIVER = "result_receiver";

    public static final int RESULT_NETWORK_PROBLEM = 1;
    public static final int RESULT_OK = 0;
    private static final String VK_SERVER_URL = "https://api.vk.com/method/";

    public String getServerURL() {
	return VK_SERVER_URL;
    }

    protected void sendResult(ServerResponse response, Intent intent) {
	ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
	if (receiver != null) {
	    if (!response.response.contains("error")) {
		parceResponse(response);
	    } else {
		response = parceError(response.response);
	    }
	    receiver.send(response.httpCode, getBundleParams());
	}
    }

    private ServerResponse parceError(String response) {
	try {
	    JSONObject error = new JSONObject(response);
	    error = error.getJSONObject("error");
	    return new ServerResponse(error.getInt("error_code"), error.getString("error_msg"));
	} catch (JSONException e) {
	    Log.e(TAG, "Can't get json from " + response);
	}
	return new ServerResponse(VKHTTPConstants.VK_UNKNOWN_ERROR, null);
    }

    protected void parceResponse(ServerResponse response) {
    }

    protected Bundle getBundleParams() {
	return null;
    }
}
