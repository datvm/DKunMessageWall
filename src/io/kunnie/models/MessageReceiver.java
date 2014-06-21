package io.kunnie.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class MessageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		AppSettings settings = AppSettings.getSettings(context);

		Bundle pudsBundle = intent.getExtras();
		Object[] pdus = (Object[]) pudsBundle.get("pdus");
		SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);

		String address = messages.getOriginatingAddress().trim();
		Log.i("DKunMessageWall", "Received from: " + address + " action name: " + intent.getAction());

		boolean matched = false;
		for (PhoneNumber phoneNumber : settings.getNumbers()) {
			if (phoneNumber.matchNumber(address)) {
				matched = true;
				phoneNumber
						.setMessageCounter(phoneNumber.getMessageCounter() + 1);

				break;
			}
		}

		if (matched) {
			this.abortBroadcast();
		}
	}

}
