package io.kunnie.dkunmessagewall;

import io.kunnie.models.AppSettings;
import io.kunnie.models.BackgroundService;
import io.kunnie.models.PhoneNumber;
import io.kunnie.models.utils.Utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class DKunMessageWall extends Activity implements
		OnClickListener, OnItemClickListener {

	private ListView lstNumbers;
	private ArrayAdapter<PhoneNumber> numberAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_dkun_message_wall);

		{ // Get control references
			this.lstNumbers = (ListView) this.findViewById(R.id.lstNumbers);

			this.lstNumbers.setOnItemClickListener(this);
			((Button) this.findViewById(R.id.btnAbout))
					.setOnClickListener(this);
			((Button) this.findViewById(R.id.btnAddNumber))
					.setOnClickListener(this);

			this.numberAdapter = new ArrayAdapter<PhoneNumber>(this,
					android.R.layout.simple_list_item_1,
					new ArrayList<PhoneNumber>());
			this.lstNumbers.setAdapter(this.numberAdapter);
		}

		{ // Start the service in case it didn't start (for example, first run)
			Intent serviceLauncher = new Intent(this, BackgroundService.class);
			this.startService(serviceLauncher);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		AppSettings settings = AppSettings.getSettings(this);

		// Populate the list
		this.numberAdapter.clear();
		
		for (PhoneNumber phoneNumber : settings.getNumbers()) {
			this.numberAdapter.add(phoneNumber);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAbout:
			this.onAboutButtonClick();
			break;
		case R.id.btnAddNumber:
			this.onAddNumberButtonClick();
			break;
		}
	}

	private void onAboutButtonClick() {
		Intent intent = new Intent(this, About.class);
		this.startActivity(intent);
	}

	private void onAddNumberButtonClick() {
		final EditText txtInput = new EditText(this);

		Utils.prompt(this, txtInput, "Add blocked number",
				"Enter the number you want to block:")
				.setPositiveButton("Add",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Editable value = txtInput.getText();

								addNumber(value.toString());
							}

						}).show();
	}

	private void addNumber(String pNumber) {
		// Create object
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setNumber(pNumber);
		phoneNumber.setMessageCounter(0);

		{ // Save to settings
			AppSettings settings = AppSettings.getSettings(this);
			settings.getNumbers().add(phoneNumber);
			settings.save(this);
		}

		{ // Add to list
			this.numberAdapter.add(phoneNumber);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> pAdapter, View pView, int pPosition,
			long pID) {
		// Get item
		final PhoneNumber number = this.numberAdapter.getItem(pPosition);

		// Confirm

		Utils.prompt(this, null, "Remove number",
				"Are you sure to remove this number from blocking list? " + number.getNumber())
				.setPositiveButton("Remove",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								removePhoneNumber(number);
							}
						}).show();
	}

	private void removePhoneNumber(PhoneNumber pNumber) {
		{ // Remove from settings
			AppSettings settings = AppSettings.getSettings(this);
			settings.getNumbers().remove(pNumber);
			settings.save(this);
		}
		
		{ // Remove from list
			this.numberAdapter.remove(pNumber);
		}

	}
	
}
