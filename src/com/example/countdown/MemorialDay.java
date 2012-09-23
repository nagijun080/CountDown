package com.example.countdown;

import java.util.Calendar;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MemorialDay extends Activity{

	private TextView textView1;
	private EditText editText;
	private Integer year;
	private Integer month;
	private Integer day;
	
	private static final int DATE_DIALOG_ID = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView1 = (TextView)findViewById(R.id.textView1);
        editText = (EditText)findViewById(R.id.editText);
        
        editText.setText(getBundleText());
        
        textView1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		showDialog(DATE_DIALOG_ID);
        	}
        });
        
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);    
        
        updateDisplay();
    }
    
    @Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
		Log.d("onPause()" ,"onPause()");
		this.widgetSend();
	}

	@Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
		Log.d("onStop()" ,"onStop()");
		this.widgetSend();
	}

	private void updateDisplay() {
    	((TextView)textView1).setText(new StringBuilder().append(year).append("年")
    			.append(month + 1).append("月").append(day).append("日"));
     }

    @Override
	protected Dialog onCreateDialog(int id) {
		// TODO 自動生成されたメソッド・スタブ
		switch(id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, dateSetListener, year, month, day);			
		}
		return null;
    }
    
   
	private OnDateSetListener dateSetListener = new OnDateSetListener() {
    	@Override
    	public void onDateSet(DatePicker view, int selectYear, int monthOfYear, int dayOfMonth) {
    		year = selectYear;
    		month = monthOfYear;
    		day = dayOfMonth;
    		updateDisplay();
    	}
    };
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	/*@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		Intent widgetUpdate = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
		Bundle bundle = new Bundle();
		bundle.putString("text", editText.getText().toString());
		widgetUpdate.putExtras(bundle);
		sendBroadcast(widgetUpdate);
		
		finish();
		
	} */

	/*public void save() {
		EditText et = (EditText)findViewById(R.id.editText);
		String text = et.getText().toString();
		String sql = " insert into anniDB ( id, anniText, year, month, day) " +
				"values ( " + (i++) + "," + text + "," + year + "," + month + "," + day + ");";

		AnniverDB anniDB = new AnniverDB(this);
		SQLiteDatabase db = anniDB.getWritableDatabase();
		Log.d("MemorialDay.class--save()","save()"); 
		try {
            db.execSQL(sql);
        } catch (SQLException e) {
            Log.e("ERROR", e.toString());
        }
	}*/
	
	public void widgetSend() {
		EditText et = (EditText)findViewById(R.id.editText);
		
		Intent widgetUpdate = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
		Bundle bundle = new Bundle();
		bundle.putString("text" ,et.getText().toString());
		widgetUpdate.putExtras(bundle);
		sendBroadcast(widgetUpdate);
		
		setBundleText(et.getText().toString());
		
		finish();
	}
	
	public void setBundleText(String text) {
		SharedPreferences pref = getSharedPreferences("Memo", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("memo" , text);
		editor.commit();
	}
	
	public String getBundleText() {
		SharedPreferences pref = this.getSharedPreferences("Memo", MODE_PRIVATE);
		return pref.getString("memo", "");
	}
	
	public void setDateBase(Context context, String text) {
		AnniverDB anniDB = new AnniverDB(context);
		SQLiteDatabase sdb = anniDB.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("annitext", text);
		values.put("year", year);
		values.put("month", month);
		values.put("day", day);
		long id = sdb.insert("anniDB", null, values);
		
	}
}
