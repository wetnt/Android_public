package com.boboking.brushos.sqlite;

import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.boboking.brushos.data.MainData;
import com.boboking.brushos.data.MinTenData;
import com.boboking.tools.d;

public class BBKSQLite_Works {

	// http://blog.csdn.net/codeeer/article/details/30237597
	// ----------------------------------------------------------
	private SQLiteDatabase db;
	private String pathName = "";
	// ----------------------------------------------------------
	public BBKSQLite_Table_MinTen dayTab;

	// ----------------------------------------------------------
	public void CreateDB(Context context, String _pathName) {
		this.pathName = _pathName;
		// ----------------------------------------------------------
		db = SQLiteDatabase.openOrCreateDatabase(pathName, null);
		// ----------------------------------------------------------
		CursorFactory factory = null;
		int version = 1;
		// ----------------------------------------------------------
		dayTab = new BBKSQLite_Table_MinTen(context, "day", factory, version);
		dayTab.Create_Table(db);
		// ----------------------------------------------------------
		logDB();
		// ----------------------------------------------------------
	}

	public void ClearDB() {
		if (db == null)
			return;
		dayTab.Clear_Table(db);
		dayTab.Create_Table(db);
	}

	public void CloseDB() {
		if (db != null)
			db.close();
	}

	private void logDB() {
		if (db != null)
			d.s("dbPath = " + db.getPath() + "       dbSzie = " + db.getMaximumSize());
	}

	// ================================================================================
	// ================================================================================
	// ================================================================================
	public void GetTodayData() {
		long nowLong = new Date(System.currentTimeMillis()).getTime();
		Date date_a = new Date(MainData.GetDayStartLong(nowLong));
		Date date_b = new Date(MainData.GetDayEndedLong(nowLong));
		dayTab.Get_Day_Data(db, date_a, date_b);
	}

	public void UpdateDayData(MinTenData tmd) {
		dayTab.Update_Insert_MinTen_Data(tmd, db);
	}
	// ================================================================================
	// ================================================================================
	// ================================================================================

}
