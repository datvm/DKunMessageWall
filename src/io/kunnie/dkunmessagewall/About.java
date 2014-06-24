package io.kunnie.dkunmessagewall;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class About extends Activity implements OnClickListener {

	private static final String donateLink = "https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=datvm%40outlook%2ecom&lc=VN&item_name=DKun&item_number=DKunDonation&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHosted";
	private static final String githubLink = "https://github.com/datvm/DKunMessageWall";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_about);

		((Button) this.findViewById(R.id.btnDonate)).setOnClickListener(this);
		((Button) this.findViewById(R.id.btnGitHubRep))
				.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDonate:
			this.onDonateButtonClick();
			break;
		case R.id.btnGitHubRep:
			this.onGitHubRepButtonClick();
			break;
		}
	}

	private void onDonateButtonClick() {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse(donateLink));
		this.startActivity(browserIntent);
	}

	private void onGitHubRepButtonClick() {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse(githubLink));
		this.startActivity(browserIntent);
	}

}
