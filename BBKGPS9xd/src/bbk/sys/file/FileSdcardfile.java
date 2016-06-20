package bbk.sys.file;

import com.example.bbkgps9xd.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FileSdcardfile extends Activity {
	/** Called when the activity is first created. */
	public static final int FILE_RESULT_CODE = 1;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_main);
		Button button = (Button) findViewById(R.id.button);
		textView = (TextView) findViewById(R.id.fileText);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(FileSdcardfile.this, FileManager.class);
				startActivityForResult(intent, FILE_RESULT_CODE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (FILE_RESULT_CODE == requestCode) {
			Bundle bundle = null;
			if (data != null && (bundle = data.getExtras()) != null) {
				textView.setText("选择文件夹为：\r\n" + bundle.getString("file"));
			}
		}
	}
}