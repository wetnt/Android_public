package com.boboking.brushos.sqlite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.boboking.brushos.data.MinTenData;
import com.boboking.tools.d;

public class BBKSQLite_Table_MinTen extends BBKSQLite_Table {
	// ----------------------------------------------------------
	private final String IDID = "idid"; // 0
	// ----------------------------------------------------------
	private final String DATE = "date";// 1
	private final String INDX = "indx";// 2
	private final String FUDU = "fudu";// 3
	// ----------------------------------------------------------
	private String UPDATE_TABLE_SQL = "";
	// ----------------------------------------------------------
	public List<MinTenData> dayList = new ArrayList<MinTenData>();

	// ----------------------------------------------------------

	public BBKSQLite_Table_MinTen(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// ----------------------------------------------------------
		CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + //
				" (" + //
				// -----------------------------------
				IDID + dbIdmType + ", " + // 0
				// -----------------------------------
				DATE + dbMtxType + ", " + // 1
				INDX + dbMtxType + ", " + // 2
				FUDU + dbMtxType + " " + // 3
				// -----------------------------------
				");";
		// ----------------------------------------------------------
		INSERT_TABLE_SQL = "INSERT INTO " + TABLE_NAME + " VALUES(NULL,?,?,?);";
		// ---------------------------------------------------------(0,1,2,3,4);";
		UPDATE_TABLE_SQL = "UPDATE " + TABLE_NAME + " SET " + DATE + "=? ," + " FUDU=? " + " WHERE " + " INDX=? ;";
		outStr = new String[] { DATE, INDX, FUDU };
		// ----------------------------------------------------------
	}

	public void Get_Day_Data(SQLiteDatabase db, Date date_a, Date date_b) {
		// ----------------------------------------------------------
		if (db == null || date_a == null || date_b == null)
			return;
		// ----------------------------------------------------------
		Cursor mCursor = db.query(TABLE_NAME, //
				outStr,//
				DATE + ">=? AND " + DATE + "<=?", new String[] { String.valueOf(date_a.getTime()), String.valueOf(date_b.getTime()) },//
				null, null, null, null);
		// ----------------------------------------------------------
		int dbCount = mCursor.getCount();
		if (mCursor.moveToFirst()) {
			// ----------------------------------------------------------
			dayList = new ArrayList<MinTenData>();
			// d.s("Get_Day_Data dbCount=" + dbCount);
			// ----------------------------------------------------------
			for (int i = 0; i < dbCount; i++) {
				mCursor.moveToPosition(i);
				try {
					MinTenData a = Get_MinTen_Data(mCursor);
					// d.s("dbCount index=" + i + ",\t" + a.i + ",\t" + a.a +
					// ",\t" + a.d);
					dayList.add(a);
				} catch (Exception e) {
					d.s("dbCount index=" + i + " err");
					e.printStackTrace();
				}
			}
		}
		// ----------------------------------------------------------
		if (mCursor != null)
			mCursor.close();
		// ----------------------------------------------------------
	}

	private MinTenData Get_MinTen_Data(Cursor mCursor) {
		MinTenData t = new MinTenData();
		// ----------------------------------------------------------
		if (mCursor == null)
			return t;
		// ----------------------------------------------------------
		t.d = mCursor.getLong(mCursor.getColumnIndex(DATE));
		t.i = mCursor.getInt(mCursor.getColumnIndex(INDX));
		t.a = mCursor.getInt(mCursor.getColumnIndex(FUDU));
		// ----------------------------------------------------------
		return t;
		// ----------------------------------------------------------
	}

	public void Insert_MinTen_Data(MinTenData s, SQLiteDatabase db) {
		// ----------------------------------------------------------
		if (s == null || db == null)
			return;
		d.s(INSERT_TABLE_SQL);
		try {
			// ----------------------------------------------------------
			db.execSQL(INSERT_TABLE_SQL, new Object[] { s.d, String.valueOf(s.i), String.valueOf(s.a) });
			// ----------------------------------------------------------
		} catch (SQLException e) {
			d.s(e.getMessage());
			e.printStackTrace();
		}
		// ----------------------------------------------------------
	}

	public void Update_Insert_MinTen_Data(MinTenData s, SQLiteDatabase db) {
		// ----------------------------------------------------------
		if (s == null || db == null)
			return;
		// ----------------------------------------------------------
		Cursor mCursor = db.query(TABLE_NAME, //
				outStr,//
				INDX + "=?", new String[] { String.valueOf(s.i) },//
				null, null, null, null);
		// ----------------------------------------------------------
		if (mCursor.moveToFirst()) {
			db.execSQL(UPDATE_TABLE_SQL, new Object[] { s.d, String.valueOf(s.a), String.valueOf(s.i) });
		} else {
			Insert_MinTen_Data(s, db);
		}
		// ----------------------------------------------------------
	}

	// ================================================================================
	// ================================================================================
	// ================================================================================
}
