package com.example.countdown;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

public class MemorialDay extends Activity {

	private TextView textView1,textView2;
	private Integer year;
	private Integer month;
	private Integer day;
	
	private static final int DATE_DIALOG_ID = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        
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
    
    private void updateDisplay() {
    	textView1.setText(new StringBuilder().append(year).append("年")
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
}
