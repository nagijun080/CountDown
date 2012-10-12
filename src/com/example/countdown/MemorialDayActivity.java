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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MemorialDayActivity extends Activity{

	private TextView textView1;
	private EditText editText;
	private Button addButton;
	private Button saveButton;
	
	private Integer year;
	private Integer month;
	private Integer day;
	private String text;
	private String ymdSt;
	
	private static final int DATE_DIALOG_ID = 0;
	private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView1 = (TextView)findViewById(R.id.textView1);
        editText = (EditText)findViewById(R.id.editText);
        addButton = (Button)findViewById(R.id.button1);
        saveButton = (Button)findViewById(R.id.saveButton);
        
        textView1.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		showDialog(DATE_DIALOG_ID);
        	}
        });
        
        addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				setShowDiaEdText();
			}

        });
        
        saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				setDateBase();
			}
        	
        });
        
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer day = calendar.get(Calendar.DATE);
        
        updateDisplay(year, month, day);
    }
    

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO 自動生成されたメソッド・スタブ
		Calendar calendar = Calendar.getInstance();
		Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer day = calendar.get(Calendar.DATE);
		
		switch(id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, dateSetListener, year, month, day);			
		}
		return null;
    }
    
   
	private OnDateSetListener dateSetListener = new OnDateSetListener() {
    	@Override
    	public void onDateSet(DatePicker view, int selectYear, int monthOfYear, int dayOfMonth) {
    		Integer year = selectYear;
    		Integer month = monthOfYear + 1;
    		Integer day = dayOfMonth;
    		updateDisplay(year, month, day);
    	}
    };
    
    private void updateDisplay(Integer year, Integer month, Integer day) {
    	TextView textView = new TextView(this);
    	textView.setText(new StringBuilder().append(year).append("年")
    			.append(month).append("月").append(day).append("日"));
     }


	
	public void setDateBase() {
		AnniverDB anniDB = new AnniverDB(getApplicationContext());
		SQLiteDatabase sdb = anniDB.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		text = editText.getText().toString();
		ymdSt = String.valueOf(year) + String.valueOf(month) + String.valueOf(day); Log.d("setDateBase()-->ymdSt", ymdSt);
		
		values.put("anniText", text);
		values.put("ymd", ymdSt);
		
		sdb.insert("anniDB", null, values);
		anniDB.close();
	}
	

	private void setShowDiaEdText() {
		// TODO 自動生成されたメソッド・スタブ
		EditText edit = new EditText(this);
		TextView text = new TextView(this);
		text.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		showDialog(DATE_DIALOG_ID);
        	}
        });
		
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_main);
		linearLayout.addView(text,createParam(MP, 100));
		linearLayout.addView(edit, createParam(MP, 100));
	}
	
	 private LinearLayout.LayoutParams createParam(int w, int h){
	        return new LinearLayout.LayoutParams(w, h);
	 }
	
}
