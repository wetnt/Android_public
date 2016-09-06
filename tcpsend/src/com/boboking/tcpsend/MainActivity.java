package com.boboking.tcpsend;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewLoad();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//====================================================================
	//====================================================================
	//====================================================================
	private 	TextView txtRec;
	private Button btnSend;
	private 	BBKTcpIp tcp = new BBKTcpIp();
	private byte[] data={'a','b'};
	
	private void ViewLoad() {
		txtRec = (TextView) findViewById(R.id.txtRec);
		btnSend = (Button) findViewById(R.id.btnSend);

		btnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tcp.send_Date(data,"");
			}
		});
		
		tcp.set_IP_Port("192.168.10.163",9527);
	}
	//====================================================================
	//====================================================================
	//====================================================================

}
