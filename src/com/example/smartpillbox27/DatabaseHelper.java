package com.example.smartpillbox27;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.*;
public class DatabaseHelper extends SQLiteOpenHelper{
	private static final String LOG = "DatabaseHelper";
	 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "SCHEDULE";
 
    // Table Names
    private static final String TABLE_NAME= "alarm";
    
    //False_Answer Table column names
    private static final String COLUMN_ID = "aid";
    private static final String COLUMN_FREQ = "frequency";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_TIME= "time";
    private static final String COLUMN_DATE= "date";
    private static final String COLUMN_DUR = "duration";
    
   //Category table create statement
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
    String sql="CREATE TABLE "
           + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FREQ
           + " INTEGER," + COLUMN_DAY + " INTEGER," + COLUMN_DATE + " VARCHAR," +COLUMN_TIME 
           +" VARCHAR," + COLUMN_DUR+ " VARCHAR);";
    db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // create new tables
        onCreate(db);
    }
   public boolean addAlarm(int f,int d,String t,String dur){
	   SQLiteDatabase db=this.getWritableDatabase();
	   ContentValues contentValues=new ContentValues();
	   //DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
	   Date date=new Date();
	   String dateStr=DateFormat.getDateInstance().format(date);
	   //dateFormat.format(date);
	   contentValues.put(COLUMN_FREQ,f);
	   contentValues.put(COLUMN_DAY,d);
	   contentValues.put(COLUMN_DATE,dateStr);
	   contentValues.put(COLUMN_TIME,t);
	   contentValues.put(COLUMN_DUR,dur);
	   db.insert(TABLE_NAME, null,contentValues);
	   db.close();
	   return true;
   }
   
  /* public Cursor getSchedule(){
	   SQLiteDatabase db=this.getReadableDatabase();
	   String sql="SELECT * FROM alarm;";
	   Cursor c=db.rawQuery(sql,null);
	   Log.e("I am here","I am in DbHelper");
	   return c;
   }*/
   
// Getting All Contacts
   public List<Schedule> getAllSchedules() {
       List<Schedule> scheduleList = new ArrayList<Schedule>();
       // Select All Query
       String selectQuery = "SELECT  * FROM " + TABLE_NAME;

       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);

       // looping through all rows and adding to list
       if (cursor.moveToFirst()) {
           do {
        	   Schedule ss= new Schedule();
               ss.setID(Integer.parseInt(cursor.getString(0)));
               ss.setFrequency(Integer.parseInt(cursor.getString(1)));
               ss.setDay(Integer.parseInt(cursor.getString(2)));
               ss.setStartDate(cursor.getString(3));
               ss.setStartTime(cursor.getString(4));
               ss.setDuration(cursor.getString(5));
               // Adding contact to list
               scheduleList.add(ss);
           } while (cursor.moveToNext());
       }

       // return contact list
       return scheduleList;
   }

   
}
