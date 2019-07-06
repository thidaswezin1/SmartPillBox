package com.example.smartpillbox27;

import java.util.Date;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {
	
	//public static String CLOG;
	Button alarm,schedule,info;
	private DatabaseHelper db;
	TextView alarmText;
	Typeface face;SharedPreferences sharedPref;
	Button language1;public static int log=0;int conlan=0;
	//private static String KEY;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		face = Typeface.createFromAsset(getAssets(),
				"fonts/zawgyi-one.ttf");
		language1=(Button)findViewById(R.id.mm);
		alarm=(Button)findViewById(R.id.button2);
		schedule=(Button)findViewById(R.id.button3);
		info=(Button)findViewById(R.id.button4);
		sharedPref=getSharedPreferences("mypref",0);
		Intent intent=getIntent();
		if(intent.getStringExtra("CLOG")!=null){
			conlan=Integer.parseInt(intent.getStringExtra("CLOG"));
			Log.d("conlan1=",String.valueOf(conlan));
			if(conlan==1){
				log=2;
				alarm.setTypeface(face);
				alarm.setText("alarm ေပးရန္");
				alarm.setTextSize(12);
				//alarm.setWidth(rl.getWidth()/3);
				schedule.setTypeface(face);
				schedule.setText("အခ်ိန္ဇယား");
				schedule.setTextSize(12);
				//schedule.setWidth(rl.getWidth()/3);
				info.setTypeface(face);
				info.setText("အက်ဥ္းခ်ဳပ္");
				info.setTextSize(12);
				//info.setWidth(rl.getWidth()/3);
				language1.setText("English");
			}
			else{
				log=1;
				alarm.setText("Alarm");
				alarm.setTextSize(12);
				schedule.setText("Schedule");
				schedule.setTextSize(12);
				info.setText("Info");
				info.setTextSize(12);
				language1.setTypeface(face);
				language1.setText("ျမန္မာ");
			}
		}
		
		else if(!(sharedPref.getString("KEY","").equals(""))){
			conlan=Integer.parseInt(sharedPref.getString("KEY",""));
			//conlan=1;
			if(conlan==1){
				log=2;
				alarm.setTypeface(face);
				alarm.setText("alarm ေပးရန္");
				alarm.setTextSize(12);
				//alarm.setWidth(rl.getWidth()/3);
				schedule.setTypeface(face);
				schedule.setText("အခ်ိန္ဇယား");
				schedule.setTextSize(12);
				//schedule.setWidth(rl.getWidth()/3);
				info.setTypeface(face);
				info.setText("အက်ဥ္းခ်ဳပ္");
				info.setTextSize(12);
				//info.setWidth(rl.getWidth()/3);
				language1.setText("English");
			}
			else{
				log=1;
				alarm.setText("Alarm");
				alarm.setTextSize(12);
				schedule.setText("Schedule");
				schedule.setTextSize(12);
				info.setText("Info");
				info.setTextSize(12);
				language1.setTypeface(face);
				language1.setText("ျမန္မာ");
			}
			
		}
		else{
			conlan=1;
			log=2;
			alarm.setTypeface(face);
			alarm.setText("alarm ေပးရန္");
			alarm.setTextSize(12);
			//alarm.setWidth(rl.getWidth()/3);
			schedule.setTypeface(face);
			schedule.setText("အခ်ိန္ဇယား");
			schedule.setTextSize(12);
			//schedule.setWidth(rl.getWidth()/3);
			info.setTypeface(face);
			info.setText("အက်ဥ္းခ်ဳပ္");
			info.setTextSize(12);
			//info.setWidth(rl.getWidth()/3);
			language1.setText("English");
		}
		
		/*if(log==0 || log==2){
		language1.setTypeface(face);
		language1.setText("ျမန္မာ");
		log=1;
		}
		else{
		language1.setText("English");
		log=2;
		}*/
		
		language1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(log==1){
					//RelativeLayout rl=(RelativeLayout)findViewById(R.id.mainpg);
					//LayoutParams lp=(LayoutParams) rl.getLayoutParams();
					alarm.setTypeface(face);
					alarm.setText("alarm ေပးရန္");
					alarm.setTextSize(12);
					//alarm.setWidth(rl.getWidth()/3);
					schedule.setTypeface(face);
					schedule.setText("အခ်ိန္ဇယား");
					schedule.setTextSize(12);
					//schedule.setWidth(rl.getWidth()/3);
					info.setTypeface(face);
					info.setText("လမ္းညႊမ္");
					info.setTextSize(12);
					//info.setWidth(rl.getWidth()/3);
					conlan=1;
					log=2;
					language1.setText("English");
				}
				else{
					alarm.setText("Alarm");
					alarm.setTextSize(12);
					schedule.setText("Schedule");
					schedule.setTextSize(12);
					info.setText("Info");
					info.setTextSize(12);
					language1.setTypeface(face);
					language1.setText("ျမန္မာ");
					log=1;
					conlan=0;
				}
				
			}
		});
		
		alarm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(),Alarm.class);
				intent.putExtra("CLOG", String.valueOf(conlan));
				startActivity(intent);
				
			}
		});
		
		schedule.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*Intent intent=new Intent(getApplicationContext(),ViewSchedule.class);
				startActivity(intent);*/
				Date d=new Date();
				int i=0;
				Log.d("Date",d.toString());
				setContentView(R.layout.view_schedule);
				db=new DatabaseHelper(getApplicationContext());
				List<Schedule> schedules = db.getAllSchedules();       
				alarmText= (TextView) findViewById(R.id.alarmList);
				alarmText.setText("");
				RelativeLayout layout = (RelativeLayout)findViewById(R.id.sche);
		        if(conlan==1){
				for (Schedule sc : schedules) {
		            //String log = "Id: "+sc.getID()+" ,Frequency: " + sc.getFrequency() + " ,Day: " +    sc.getDay();
		                // Writing Contacts to log
		        //Log.d("Name: ", log);
		        /*ImageButton ibtn=new ImageButton(getApplicationContext());
		        ibtn.setId(i);
		        ibtn.setLayoutParams(new android.view.ViewGroup.LayoutParams(30,30));
		        ibtn.setBackgroundResource(R.drawable.pic2);
		        layout.addView(ibtn);*/
		        
		        //RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		        //lp.addRule(RelativeLayout.RIGHT_OF,alarmText.getId());
		        //ibtn.setLayoutParams(lp);
		        //addView(ibtn);
				alarmText.setTypeface(face);
				alarmText.append("အခ်ိန္ဇယားအမွတ္စဥ္: "+sc.getID());
			    alarmText.append("\nအၾကိမ္အေရအတြက္: "+sc.getFrequency());
			    alarmText.append("\nေန့အေရအတြက္: "+sc.getDay());
			    alarmText.append("\nစတင္မည့္ရက္ : "+sc.getStartDate());
			    alarmText.append("\nစတင္မည့္အခ်ိန္: "+sc.getStartTime());
			    alarmText.append("\nျခားမည့္အခ်ိန္ : "+sc.getDuration());
			    alarmText.append("\n\n");
				}
		        }
				else{
					for (Schedule sc : schedules) {
		        alarmText.append("Schedule Id: "+sc.getID());
		        alarmText.append("\nFrequency: "+sc.getFrequency());
		        alarmText.append("\nDay: "+sc.getDay());
		        alarmText.append("\nStart Date : "+sc.getStartDate());
		        alarmText.append("\nStart Time : "+sc.getStartTime());
		        alarmText.append("\nDuration : "+sc.getDuration());
		        alarmText.append("\n\n");
				}
		       // i++;
		        }
		        RelativeLayout row2=(RelativeLayout)findViewById(R.id.sche);
		        Button backBtn=new Button(getApplicationContext());
		        if(conlan==1){
		        	 backBtn.setTypeface(face);
		        	 backBtn.setText("ေနာက္သို့");
		        	 backBtn.setTextSize(12);
		        }
		        else{
		        	 backBtn.setText("Back");
		        	 backBtn.setTextSize(12);
		        }
		        Log.d("conlan2=",String.valueOf(conlan));
		        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				//Log.d("bottom",String.valueOf(alarmText.getBottom()));
		       // layoutParams.setMargins(5,alarmText.getBottom()+200,0,0);
				layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
				layoutParams.addRule(RelativeLayout.BELOW,alarmText.getId());
				//layoutParams.setMargins(left, top, right, bottom);
				backBtn.setLayoutParams(layoutParams);
				row2.addView(backBtn);
				
				backBtn.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(getApplicationContext(),MainActivity.class);
						intent.putExtra("CLOG", String.valueOf(conlan));
						startActivity(intent);
					}
				});
			}
		});
		
		info.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(),About.class);
				intent.putExtra("CLOG", String.valueOf(conlan));
				startActivity(intent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onBackPressed(){
		if(conlan==1){
			AlertDialog dialog=new AlertDialog.Builder(this)
			.setMessage("ထြက္လိုပါသလား")
			.setPositiveButton("ထြက္မည္",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface arg0, int arg1) {
					SharedPreferences sharePref=getSharedPreferences("mypref",0);
					SharedPreferences.Editor editor=sharePref.edit();
					editor.putString("KEY", String.valueOf(conlan));
					editor.commit();
					Intent intent=new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
			    	finish();
					System.exit(0);
					
				}
			})
			.setNegativeButton("မထြက္ပါ",new DialogInterface.OnClickListener() {
				
				
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			}).show();
			TextView tv=(TextView)dialog.findViewById(android.R.id.message);
			tv.setTypeface(face);
			dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTypeface(face);
			dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTypeface(face);
		}
		else{
			AlertDialog dialog=new AlertDialog.Builder(this)
			.setMessage("Do you want to exit?")
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface arg0, int arg1) {
					SharedPreferences sharePref=getSharedPreferences("mypref",0);
					SharedPreferences.Editor editor=sharePref.edit();
					editor.putString("KEY", String.valueOf(conlan));
					editor.commit();
					Intent intent=new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
			    	finish();
					System.exit(0);
					
				}
			})
			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				
				
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			}).show();
			
		}
		
	}
	
	private void deleteAppData(){
		try{
			String pro =getApplicationContext().getPackageName();
			Runtime runtime=Runtime.getRuntime();
			runtime.exec("pm clear"+pro);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
