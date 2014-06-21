package io.kunnie.models;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class BackgroundService extends Service {

	private MessageReceiver receiver;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		this.receiver = new MessageReceiver();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		this.registerReceiver(receiver, filter);
		
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		this.unregisterReceiver(this.receiver);
		AppSettings.getSettings(this).save(this);
		
		super.onDestroy();
	}

}
