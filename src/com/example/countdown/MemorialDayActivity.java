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
import android.widget.Toast;

public class MemorialDayActivity extends Activity{

	private static final int MAX_ITEM = 100;
	private static final float TEXTSIZE = 23.0f;
	
	private Button addButton;
	private Button saveButton;
	private TextView[] textView = new TextView[MAX_ITEM];
	private EditText[] editText = new EditText[MAX_ITEM];
	
	private Integer[] year = new Integer[MAX_ITEM];
	private Integer[] month = new Integer[MAX_ITEM];
	private Integer[] day = new Integer[MAX_ITEM];
	//TextViewとEditTextをnewした回数
	private Integer maxId = 0;
	//今使っている配列の添字
	private Integer nowId;
	
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
        textView[maxId].setTextSize(TEXTSIZE);
        
        linearLayout.addView(textView[maxId],createParam(MP, 80));
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
        month[id] = calendar.get(Calendar.MONTH);
        day[id] = calendar.get(Calendar.DATE);
		//Log.d
        Log.d("onCreateDialog-->id", String.valueOf(id));	
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
		
		//Log.d
		Log.d("setDateBase()-->maxId",maxId.toString());
		Integer[] key = new Integer[maxId+1];
		String[] text = new String[maxId+1];
		String[] ymd = new String[maxId+1];
		
		long ret = 0;
		
		for (int i = 0;i < (maxId + 1);i++) {
			SQLiteDatabase sdb = anniDB.getWritableDatabase();
			ContentValues values = new ContentValues();
			key[i] = i + 1;
			text[i] = editText[i].getText().toString();
			ymd[i] = String.valueOf(year[i]) + String.valueOf(month[i]) + String.valueOf(day[i]); 
			//Log.d
			Log.d("setDateBase()-->ymd["+String.valueOf(i)+"]", ymd[i]);
			Log.d("setDateBase()-->text["+String.valueOf(i)+"]", text[i]);
			/*if (ret != -1) { */
				ret = anniDB.insert(sdb, text[i], ymd[i]);
				//Log.d
				Log.d("sdb.update()-->", String.valueOf(anniDB.insert(sdb, text[i], ymd[i])));
			/*} else {
				ret = (long)sdb.update("anniDB", values, "key = " + key[i], null);
				//Log.d
				Log.d("sdb.update()-->", String.valueOf(sdb.update("anniDB", values, "key = " + key[i], null)));
			}*/
			anniDB.close();
		}
		
		if (ret == -1) {
			Toast toast = Toast.makeText(this, "保存できませんでした。", 1000);
			toast.show();
		} else { 
			Toast toast = Toast.makeText(this, "保存されました。", 1000);
			toast.show();
		}
	}
	
	//addButtonを押したときの処理
	/* maxId + 1
	 * new EditText
	 * new TextView*/
	private void newEditText() {
		// TODO 自動生成されたメソッド・スタブ
		maxId++;
	
		textView[maxId] = new TextView(this);
		editText[maxId] = new EditText(this);
		editText[maxId].setId(maxId);
		textView[maxId].setId(maxId);
        textView[maxId].setTextSize(TEXTSIZE);
		
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
		linearLayout.addView(textView[maxId],createParam(MP, 80));
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
