package com.boboking.brushos.sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.boboking.tools.d;

public abstract class BBKSQLite_Table extends SQLiteOpenHelper {

	// ----------------------------------------------------------
	// http://blog.csdn.net/codeeer/article/details/30237597
	// ----------------------------------------------------------
	// http://www.cnblogs.com/helloandroid/articles/2150272.html
	// SQLite具有以下五种数据类型：
	// 1.NULL：空值。
	// 2.INTEGER：带符号的整型，具体取决有存入数字的范围大小。
	// 3.REAL：浮点数字，存储为8-byte IEEE浮点数。
	// 4.TEXT：字符串文本。
	// 5.BLOB：二进制对象。
	// ----------------------------------------------------------
	protected String TABLE_NAME = "sticker";
	// ----------------------------------------------------------
	protected final String dbIdmType = " INTEGER PRIMARY KEY AUTOINCREMENT";
	protected final String dbTxtType = " TEXT NOT NULL";
	protected final String dbMtxType = " REAL NOT NULL";
	protected final String dbBmpType = " BLOB";
	// ----------------------------------------------------------
	protected String CREATE_TABLE_SQL = "";
	protected String INSERT_TABLE_SQL = "";
	// ----------------------------------------------------------
	protected String[] outStr;

	// ----------------------------------------------------------

	// ================================================================================
	public BBKSQLite_Table(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		TABLE_NAME = name;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int i, int i1) {
		db.execSQL("DROP TABLE IF EXISTS  " + TABLE_NAME);
		onCreate(db);
	}

	// ================================================================================
	public void Create_Table(SQLiteDatabase db) {
		try {
			d.s(CREATE_TABLE_SQL);
			db.execSQL(CREATE_TABLE_SQL);
		} catch (SQLException e) {
			d.s(e.getMessage());
		}
	}

	public void Clear_Table(SQLiteDatabase db) {
		try {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		} catch (SQLException e) {
			d.s(e.getMessage());
		}
	}
	// ================================================================================
}
