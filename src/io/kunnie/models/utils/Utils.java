package io.kunnie.models.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public final class Utils {

	private Utils() {
		
	}
	
	public static AlertDialog.Builder prompt(Context pContext, EditText pInput, String pTitle, String pMessage) {		
		return new AlertDialog.Builder(pContext)
				.setTitle(pTitle)
				.setMessage(pMessage)
				.setView(pInput)
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Do nothing.
							}
						});
	}

}
