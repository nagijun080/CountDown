package com.example.countdown;

import java.util.Calendar;
import java.util.Date;

import com.example.countdown.R;



import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

public class Myservice extends Service {
  
	private static String[] weekName = new String[] { "���j��", "���j��", "�Ηj��", "���j��", "�ؗj��", "���j��", "�y�j��", };
	private String getDateSql;
	public Integer yearInt = 0;
	public Integer dateMonth = 0;
	public Integer dateDay = 0;
	
		
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onStart(intent, startId);
		
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.appwidget.action.APPWIDGET_UPDATE");
		registerReceiver(b_Receiver, filter);
		
		RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.widget_main);
		Calendar calendar = Calendar.getInstance();
		
		//�N�̏�����
		yearInt = calendar.get(Calendar.YEAR);
		String year = new String(yearInt + "�N");
		//���t�̏�����
		dateMonth = calendar.get(Calendar.MONTH) + 1;
		dateDay = calendar.get(Calendar.DATE);
		String date = new String(dateMonth.toString() + "��" + dateDay.toString() + "��");
		//�j���̏�����
		Integer week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		//���̏�����
		Integer minute = calendar.get(Calendar.MINUTE);
		Integer minUD = minute % 10;
		Integer minTD = minute / 10;
		
		String minUDSt = minUD.toString();
		String minTDSt = minTD.toString();
		//���Ԃ̏�����
		Integer hour = 0,hrUD = 0,hrTD = 0;
		
		if (Calendar.AM == calendar.get(Calendar.AM_PM)) {
			hour = calendar.get(Calendar.HOUR);
			hrUD = hour % 10;
			hrTD = hour / 10;
		} else {
			hour = calendar.get(Calendar.HOUR) + 12;
			hrUD = hour % 10;
			hrTD = hour / 10;
		}
		String hrUDSt = hrUD.toString();
		String hrTDSt = hrTD.toString();
		//�N��R.id.year�ɕ\��
		remoteViews.setTextViewText(R.id.year, year);
		//���t��R.id.day�ɕ\��
		remoteViews.setTextViewText(R.id.day, date);
		//�j����widget_main.xml��week�ɕ\��
		remoteViews.setTextViewText(R.id.week, weekName[week]);
		
		//�\�̈ʂ̎�����hrTenthsDigit�ɕ\��
		remoteViews.setTextViewText(R.id.hrTenthsDigit, hrTDSt);
		//��̈ʂ̎�����hrUnitDigit�ɕ\��
		remoteViews.setTextViewText(R.id.hrUnitDigit, hrUDSt);
				
		
		//�\�̈ʂ̕�����minTenthsDigit�ɕ\��
		remoteViews.setTextViewText(R.id.minTenthsDigit, minTDSt);
		//��̈ʂ̕�����minUnitDigit�ɕ\��
		remoteViews.setTextViewText(R.id.minUnitDigit, minUDSt);
		
		//remoteViews.setTextViewText(R.id.textAnniversary, ((views.getPackage().R.id.editText)));
		
		ComponentName thisWidget = new ComponentName(this, CountDown.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(thisWidget, remoteViews);
	}

	


	@Override
	public IBinder onBind(Intent arg0) {
	// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}
	
	public static BroadcastReceiver b_Receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			String ac = intent.getAction();
			Log.i(getClass().getSimpleName() , "Action Rrn = " + ac);
			
			if (("android.appwidget.action.APPWIDGET_UPDATE").equals(ac)) {
				 Bundle bundle = intent.getExtras();
				 if (bundle == null) {
					 Log.i(getClass().getSimpleName() , "Bundle = null" );
				 } else {
					 String msg = bundle.getString("text");
					 setTextView(context, msg);
				 }
			}
		}
		
		void setTextView(Context context, String msg_st) {
			RemoteViews remoteViews;
			
			remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_main);
			
			remoteViews.setTextViewText(R.id.textAnniversary, msg_st);
			
			ComponentName thisWidget = new ComponentName(context, CountDown.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			manager.updateAppWidget(thisWidget, remoteViews);
		}
		
	};
	
	/*public void getDataBase(Context context) {
		RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.widget_main);
		
		AnniverDB anniDB = new AnniverDB(context);
		SQLiteDatabase db = anniDB.getWritableDatabase();
		String selection = "year = " + yearInt.toString() + " and month = " + dateMonth.toString() + " and day = " + dateDay.toString();
		Cursor c = db.query("anniDB", new String[] { "anniText" }, selection,null,null,null,null);
		Boolean bool = c.moveToFirst();
		remoteViews.setTextViewText(R.id.textAnniversary, c.getString(0));
		c.close();	
	}*/
	
}
