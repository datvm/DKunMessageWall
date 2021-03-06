package io.kunnie.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
	        Intent serviceLauncher = new Intent(context, BackgroundService.class);
	        context.startService(serviceLauncher);
	        
	        Log.v("DkunMessageWall", "Service loaded at start");
	    }
	}
	
}
