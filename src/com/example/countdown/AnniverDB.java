package com.example.countdown;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AnniverDB extends SQLiteOpenHelper {
	
	public static final String name = "anniver.db";
	public static final CursorFactory factory = null;
	public static final Integer version = 1;
	

	public AnniverDB(Context context) {
		super(context, name, factory, version);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		String sql = "CREATE TABLE anniDB(key INTEGER PRIMARY KEY AUTOINCREMENT, anniText TEXT NOT NULL, " +
				"ymd TEXT UNIQUE);";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
	
	public long insert(SQLiteDatabase db, String text, String ymd) {
		ContentValues cv = new ContentValues();
		cv.put("anniText", text);
		cv.put("ymd", ymd);
		return db.insert("anniDB", null, cv);
		
	}

}
