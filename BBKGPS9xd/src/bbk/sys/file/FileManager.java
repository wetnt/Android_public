package bbk.sys.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.example.bbkgps9xd.R;

import bbk.bbk.box.BBKSoft;
import bbk.map.dat.BBKFile;
import bbk.zzz.debug.bd;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileManager extends ListActivity {
	// ======================================================
	private List<String> items = null;
	private List<String> paths = null;
	private String rootPath = getSDDir();
	private TextView mPath;
	public String fileType = "*.*";// Extension

	// ======================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_select);
		// --------------------------------------------
		mPath = (TextView) findViewById(R.id.mPath);
		// --------------------------------------------
		rootPath = BBKSoft.PathSD;
		getFileDir(rootPath, fileType);
		// --------------------------------------------
	}

	private void getFileDir(String filePath, String extype) {
		// --------------------------------------------
		mPath.setText(filePath);
		// --------------------------------------------
		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		File f = new File(filePath);
		File[] files = f.listFiles();
		// --------------------------------------------
		Arrays.sort(files);// ≈≈–Ú¥Û–°–¥√Ù∏–
		// --------------------------------------------
		if (!filePath.equals(rootPath)) {
			items.add("b1");
			paths.add(rootPath);
			items.add("b2");
			paths.add(f.getParent());
		}
		// --------------------------------------------
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (checkShapeFile(file, extype)) {
				items.add(file.getName());
				paths.add(file.getPath());
			}
		}
		// --------------------------------------------
		setListAdapter(new FileAdapter(this, items, paths));
		// --------------------------------------------
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// --------------------------------------------
		File file = new File(paths.get(position));
		// --------------------------------------------
		if (file.isDirectory()) {
			// curPath = paths.get(position);
			getFileDir(paths.get(position), fileType);
		} else {
			// --------------------------------------------
			Intent data = new Intent(FileManager.this, FileSdcardfile.class);
			Bundle bundle = new Bundle();
			bundle.putString("file", file.getPath());
			data.putExtras(bundle);
			setResult(2, data);
			// --------------------------------------------
			bd.d(file.getPath(), true, false);
			BBKFile.BBKFileAdd(file);
			// --------------------------------------------
			finish();
			// --------------------------------------------
		}
	}

	@SuppressLint("DefaultLocale")
	public boolean checkShapeFile(File file, String extype) {
		// --------------------------------------------
		String fileName = file.getName();
		String endName = FileGetExte(fileName);
		// -------------------------------------------
		if (fileName.lastIndexOf(".") == -1) {
			return true;
		}
		// -------------------------------------------
		if (extype.equals("*.*"))
			return true;
		// -------------------------------------------
		if (extype.equals(endName)) {
			return true;
		} else {
			return false;
		}
		// -------------------------------------------
	}

	public static String FileGetExte(String fpn) {
		// -----------------------------------------------------------------------------
		int start = fpn.lastIndexOf(".") + 1;
		int end = fpn.length();
		String endName = fpn.substring(start, end);
		endName = endName.toLowerCase(Locale.US);
		return endName;
		// -----------------------------------------------------------------------------
	}

	protected final String getSDDir() {
		// -------------------------------------------
		if (!checkSDcard()) {
			Toast.makeText(this, "no sdcard", Toast.LENGTH_SHORT).show();
			return "";
		}
		// -------------------------------------------
		try {
			String SD_DIR = Environment.getExternalStorageDirectory().toString();
			return SD_DIR;
		} catch (Exception e) {
			return "";
		}
		// -------------------------------------------
	}

	public boolean checkSDcard() {
		// -------------------------------------------
		String sdStutusString = Environment.getExternalStorageState();
		if (sdStutusString.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
		// -------------------------------------------
	}

}
