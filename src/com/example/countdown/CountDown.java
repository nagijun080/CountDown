package com.example.countdown;


import com.example.countdown.R;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

public class CountDown extends AppWidgetProvider {
	
	
	private static final String ACTION_START_MY_ALARM =
			"com.example.android.appwidget.countdown.ACTION_START_MY_ALARM";
	private final long interval = 1000;
	
	@Override
	public void onEnabled(Context context) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onEnabled(context);
		setAlarm(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onReceive(context, intent);
		//Log.d("onReceive","super�̂���");
			if (ACTION_START_MY_ALARM.equals(intent.getAction())) {
				Intent serviceIntent = new Intent(context, Myservice.class);
				context.startService(serviceIntent);
				//Log.d("onReceive","if���̒�");
			}
			//Log.d("onReceive�̒�","setAlarm()�̑O");
			setAlarm(context);
		}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
			Log.d("update","Update�̒�");
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_main);
			
			//�Ăяo������Activity���Z�b�g
			Intent intent = new Intent(context,MemorialDay.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			
			//widget�̃{�^���N���b�N�C�x���g�ɌĂяo������Intent��ݒ肷��
			remoteViews.setOnClickPendingIntent(R.id.getUrl, pendingIntent);
			Log.d("pendingIntent","pendingIntent");
			appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
			setAlarm(context);
		}

	private void setAlarm(Context context) {
		Intent alarmIntent = new Intent(context, CountDown.class);
		alarmIntent.setAction(ACTION_START_MY_ALARM);
		PendingIntent operation = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		long now = System.currentTimeMillis() + 1;// + 1�͊m���ɖ��������ɂȂ�悤�ɂ���
		long oneSecondAfter = now + interval - now % (interval);
		am.set(AlarmManager.RTC, oneSecondAfter, operation);
	}
	
	

}
