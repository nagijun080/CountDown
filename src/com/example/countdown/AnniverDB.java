package com.example.countdown;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

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
		String sql = "CREATE TABLE anniDB(KEY INTEGER PRIMARY KEY AUTOINCREMENT, key INTEGER, anniText TEXT, " +
				"ymd TEXT);";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

}
