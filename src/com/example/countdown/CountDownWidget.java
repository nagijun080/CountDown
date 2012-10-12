package com.example.countdown;


import java.util.Calendar;

import com.example.countdown.R;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

public class CountDownWidget extends AppWidgetProvider {
	
	
	private static final String ACTION_START_MY_ALARM =
			"com.example.android.appwidget.countdown.ACTION_START_MY_ALARM";
	private final long interval = 1000;
	
	@Override
	public void onEnabled(Context context) {
		// TODO 自動生成されたメソッド・スタブ
		super.onEnabled(context);
		//setAlarm(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO 自動生成されたメソッド・スタブ
		super.onReceive(context, intent);
		//Log.d("onReceive","superのあと");
		if (ACTION_START_MY_ALARM.equals(intent.getAction())) {
			Intent serviceIntent = new Intent(context, Myservice.class);
			context.startService(serviceIntent);
		}
		setAlarm(context);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO 自動生成されたメソッド・スタブ
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		Intent in = new Intent(context, Myservice.class);
		context.startService(in);
		
		Log.d("update","Updateの中");
		
		//呼び出したいActivityをセット
		Intent intent = new Intent(context,MemorialDayActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_main);
		remoteViews.setOnClickPendingIntent(R.id.getUrl, pendingIntent);
		
		ComponentName widget = new ComponentName(context, CountDownWidget.class);
		appWidgetManager.updateAppWidget(widget, remoteViews);
		
		setAlarm(context);
	}

	private void setAlarm(Context context) {
		Intent alarmIntent = new Intent(context, CountDownWidget.class);
		alarmIntent.setAction(ACTION_START_MY_ALARM);
		PendingIntent operation = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		long now = System.currentTimeMillis() + 1;// + 1は確実に未来時刻になるようにする
		long oneSecondAfter = now + interval - now % (interval);
		am.set(AlarmManager.RTC, oneSecondAfter, operation);
		
		getDataBase(context);
	}
	
	public void getDataBase(Context context) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_main);
		
		Calendar calendar = Calendar.getInstance();
		Integer yearInt = calendar.get(Calendar.YEAR);
		Integer dateMonth = calendar.get(Calendar.MONTH) + 1; //Log.d("dateMonth",dateMonth.toString());
		Integer dateDay = calendar.get(Calendar.DATE);
		
		AnniverDB anniDB = new AnniverDB(context);
		SQLiteDatabase db = anniDB.getWritableDatabase();
		
		//今日の日付を文字列に置き換えて結合する
		String ymdSt = yearInt.toString() + dateMonth.toString() + dateDay.toString(); Log.d("ymdSt",ymdSt);
		String selection = "ymd = " + ymdSt;
		
		Cursor c = db.query("anniDB", new String[] { "anniText", "ymd" }, selection,null,null,null,null);
		Boolean bool = c.moveToFirst();
		
		Integer culmn = c.getColumnIndex("anniText");
		
		//log.d
		Log.d("c.moveToFirst", bool.toString());
		Log.d("c.getString(0)", c.getString(culmn));
		
		while (bool) {
			remoteViews.setTextViewText(R.id.textAnniversary, c.getString(culmn));
		}
		anniDB.close();
		c.close();
	}
}
