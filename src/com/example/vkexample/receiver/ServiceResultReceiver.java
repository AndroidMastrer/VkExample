package com.example.vkexample.receiver;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class ServiceResultReceiver extends ResultReceiver {

	public ServiceResultReceiver(Handler handler) {
		super(handler);
	}

	public interface Receiver {
		public void onReceiveResult(int resultCode, Bundle data);
	}

	private Receiver mReceiver;

	public void setReceiver(Receiver receiver) {
		mReceiver = receiver;
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if (mReceiver != null) {
			mReceiver.onReceiveResult(resultCode, resultData);
		}
	}
}
