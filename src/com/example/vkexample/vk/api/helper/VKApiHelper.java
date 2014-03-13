package com.example.vkexample;

import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;

public class VKApiHelper {
    private static final String EXTRA_SERVICE_RESULT_RECEIVER = "service_result_receiver";
    private Context mContext;

    public VKApiHelper(Context context) {
	mContext = context;
    }
    
    public void wallGet(ResultReceiver receiver, int offset, int count){
	Intent intent = getServiceIntent();
	intent.putExtra(EXTRA_SERVICE_RESULT_RECEIVER, receiver);
	intent.putExtra(WallGet.EXTRA_OFFSET, offset);
	intent.putExtra(WallGet.EXTRA_COUNT, offset);
	intent.setAction(WallGet.class.getName());
	mContext.startService(intent);
    }

    private Intent getServiceIntent() {
	return new Intent(mContext, ExecutorService.class);
    }
}
