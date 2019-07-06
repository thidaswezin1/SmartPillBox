package com.example.smartpillbox27;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class About extends Activity {
	//protected static String CLOG;
	TextView tv1;Typeface face;int cl;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		tv1=(TextView)findViewById(R.id.textView1);
		Intent in1=getIntent();
		face = Typeface.createFromAsset(getAssets(),
				"fonts/zawgyi-one.ttf");
		
		if(in1.getStringExtra("CLOG")!=null){
			cl=Integer.parseInt(in1.getStringExtra("CLOG"));
			Log.d("Main cl=",String.valueOf(cl));
			if(cl==1){
				tv1.setTypeface(face);
				tv1.setText("ျ္ုျ်ု");
			}
			else{
				tv1.append("DD");
			}
		}
				
		
		Button back=(Button)findViewById(R.id.back);
		if(cl==1){
			back.setTypeface(face);
		    back.setText("ေနာက္သို့");
		    back.setTextSize(12);
		}
		else{
			back.setText("Back");
			back.setTextSize(12);
		}
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(),MainActivity.class);
				Log.d("about cl", String.valueOf(cl));
				intent.putExtra("CLOG", String.valueOf(cl));
				startActivity(intent);
				
			}
		});
}
	public void onBackPressed(){
		new AlertDialog.Builder(this)
		.setMessage("Do you want to exit?")
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface arg0, int arg1) {
				
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
