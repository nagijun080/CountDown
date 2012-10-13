package com.example.countdown;

import java.util.Calendar;

import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;

public class MemorialDayActivity extends Activity{

	private static final int MAX_ITEM = 100;
	private Button addButton;
	private Button saveButton;
	private TextView[] textView = new TextView[MAX_ITEM];
	private EditText[] editText = new EditText[MAX_ITEM];
	
	private Integer[] year = new Integer[MAX_ITEM];
	private Integer[] month = new Integer[MAX_ITEM];
	private Integer[] day = new Integer[MAX_ITEM];
	//TextView配列とEidtText配列の長さを格納
	private Integer maxId = 0;
	private Integer nowId;
	
	
	private String text;
	private String ymdSt;
	
	private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.save_layout);
        textView[maxId] = new TextView(this);
        editText[maxId] = new EditText(this);
        textView[maxId].setId(maxId);
        editText[maxId].setId(maxId);
        
        linearLayout.addView(textView[maxId],createParam(MP, 100));
        linearLayout.addView(editText[maxId], createParam(MP, 100));
        
        
        Calendar calendar = Calendar.getInstance();
        year[maxId] = calendar.get(Calendar.YEAR);
        month[maxId] = calendar.get(Calendar.MONTH) + 1;
        day[maxId] = calendar.get(Calendar.DATE);
        
        updateDisplay(maxId, year[maxId], month[maxId], day[maxId]);
        
        addButton = (Button)findViewById(R.id.button1);
        saveButton = (Button)findViewById(R.id.saveButton);
        
        textView[maxId].setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		nowId = v.getId();
        		showDialog(v.getId());
        	}
        });
        
        //クリックされたらTextViewとEditTextがnewされる
        addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				newEditText();
				showEditText();
			}

        });
        
        saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				setDateBase();
			}
        	
        });
       
    }
    
    
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO 自動生成されたメソッド・スタブ
		Calendar calendar = Calendar.getInstance();
		year[id] = calendar.get(Calendar.YEAR);
        month[id] = calendar.get(Calendar.MONTH) + 1;
        day[id] = calendar.get(Calendar.DATE);
		
        Log.d("onCreateDialog-->", String.valueOf(id));	
		return new DatePickerDialog(this, dateSetListener, year[id], month[id], day[id]);
    }
    
   
	private OnDateSetListener dateSetListener = new OnDateSetListener() {
    	//設定を押したあとの処理
		@Override
    	public void onDateSet(DatePicker view, int selectYear, int monthOfYear, int dayOfMonth) {
    		year[nowId] = selectYear;
    		month[nowId] = monthOfYear + 1;
    		day[nowId] = dayOfMonth;
    		updateDisplay(nowId,year[nowId], month[nowId], day[nowId]);
    	}
    };
    
    private void updateDisplay(Integer id,Integer year, Integer month, Integer day) {
    	textView[id].setText(new StringBuilder().append(year).append("年")
    			.append(month).append("月").append(day).append("日"));
     }


	
	public void setDateBase() {
		AnniverDB anniDB = new AnniverDB(getApplicationContext());
		SQLiteDatabase sdb = anniDB.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		text = editText[maxId].getText().toString();
		ymdSt = String.valueOf(year) + String.valueOf(month) + String.valueOf(day); Log.d("setDateBase()-->ymdSt", ymdSt);
		
		values.put("anniText", text);
		values.put("ymd", ymdSt);
		
		sdb.insert("anniDB", null, values);
		anniDB.close();
	}
	
	//addButtonを押したときの処理 maxId++されてる
	/* new EditText
	 * new TextView*/
	private void newEditText() {
		// TODO 自動生成されたメソッド・スタブ
		maxId++;
	
		textView[maxId] = new TextView(this);
		editText[maxId] = new EditText(this);
		editText[maxId].setId(maxId);
		textView[maxId].setId(maxId);
		
		textView[maxId].setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		nowId = v.getId();
        		showDialog(v.getId());
        	}
        });
		
	}
	
	private void showEditText() {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.save_layout);
		linearLayout.addView(textView[maxId],createParam(MP, 100));
		linearLayout.addView(editText[maxId], createParam(MP, 100));
		
		Calendar calendar = Calendar.getInstance();
        year[maxId] = calendar.get(Calendar.YEAR);
        month[maxId] = calendar.get(Calendar.MONTH) + 1;
        day[maxId] = calendar.get(Calendar.DATE);
        
        updateDisplay(maxId,year[maxId], month[maxId], day[maxId]);
	}
	
	 private LinearLayout.LayoutParams createParam(int w, int h){
	        return new LinearLayout.LayoutParams(w, h);
	 }
	
}
