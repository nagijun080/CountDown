package com.example.countdown;

import java.util.Calendar;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MemorialDay extends Activity implements OnClickListener{

	private TextView textView1;
	private EditText editText;
	private Button button;
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
        button = (Button)findViewById(R.id.button);
        
        textView1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		showDialog(DATE_DIALOG_ID);
        	}
        });
        
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        
        button.setOnClickListener(this);
        
        
        
        updateDisplay();
    }
    
    @Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
		this.save();
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

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		Intent widgetUpdate = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
		Bundle bundle = new Bundle();
		bundle.putString("text", editText.getText().toString());
		widgetUpdate.putExtras(bundle);
		sendBroadcast(widgetUpdate);
		
		finish();
		
	}

	public void save() {
		EditText et = (EditText)findViewById(R.id.editText);
		String text = et.getText().toString();
		String sql = " insert into anniDB ( " + android.provider.BaseColumns._ID + " , anniText, year, month, day) " +
				"values (" + 1 + "," + text + ", null,null,null);";

		AnniverDB anniDB = new AnniverDB(this);
		SQLiteDatabase db = anniDB.getWritableDatabase();
		db.execSQL(sql);
		
	}
}
