 package com.example.smartpillbox27;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class Alarm extends Activity {

	private final String DEVICE_ADDRESS="00:21:13:04:3D:94";
	private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
	private BluetoothDevice device;
	private BluetoothSocket socket;
	private OutputStream outputStream;
	private InputStream inputStream;
	Button startButton;Button btnSubmit;
	Button btnCancel;
	EditText freq1;
	EditText day1;
	Button btntime;Typeface face;
	EditText durHr,durMin;DatabaseHelper db;
	TextView tv1,tv2,tv3,tv4,tv5;
	boolean deviceConnected=false;
	Thread thread;
	byte buffer[];
	int bufferPosition;TextView time;
	boolean stopThread;int ff,dd,total,cl;
	Calendar dateAndTime=Calendar.getInstance();
	TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener(){
		public void onTimeSet(TimePicker view, int hourOfDay, int minute){
			
			dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			dateAndTime.set(Calendar.MINUTE, minute);
			updateTime();
			}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.alarm);
	startButton = (Button) findViewById(R.id.button1);
	db=new DatabaseHelper(getApplicationContext());
	btnSubmit= (Button) findViewById(R.id.submit);
	btnCancel = (Button) findViewById(R.id.cancel);
	tv1=(TextView)findViewById(R.id.textView1);
	tv2=(TextView)findViewById(R.id.textView2);
	tv3=(TextView)findViewById(R.id.textView3);
	tv4=(TextView)findViewById(R.id.textView4);
	tv5=(TextView)findViewById(R.id.textView5);
	freq1 = (EditText) findViewById(R.id.freq);
	day1= (EditText) findViewById(R.id.day);
	time=(TextView)findViewById(R.id.timeTv);
	durHr= (EditText) findViewById(R.id.editHr);
	durMin= (EditText) findViewById(R.id.editMin);

	btntime = (Button) findViewById(R.id.time);
	Intent in1=getIntent();
	face = Typeface.createFromAsset(getAssets(),
			"fonts/zawgyi-one.ttf");
	
	if(in1.getStringExtra("CLOG")!=null){
		cl=Integer.parseInt(in1.getStringExtra("CLOG"));
		if(cl==1){
			startButton.setTypeface(face);
			startButton.setText("ဖြင့္");
			startButton.setTextSize(9);
			tv1.setTypeface(face);
			tv1.setText("ရက္ေပါင္း");
			tv1.setTextSize(9);
			tv2.setTypeface(face);
			tv2.setText("တစ္ေနံလွ်င္ေသာက္ရမည့္\n" +
					"အၾကိမ္");
			tv2.setTextSize(9);
			tv3.setTypeface(face);
			tv3.setText("ျခားမည့္အခ်ိန္");
			tv3.setTextSize(9);
			tv4.setTypeface(face);
			tv4.setText("နာရီ");
			tv4.setTextSize(9);
			tv5.setTypeface(face);
			tv5.setText("မိနစ္");
			tv5.setTextSize(9);
			btntime.setTypeface(face);
			btntime.setText("စတင္ေဆးေသာက္မည့္အခ်ိန္");
			btntime.setTextSize(9);
			btnSubmit.setTypeface(face);
			btnSubmit.setText("လုပ္မည္");
			btnSubmit.setTextSize(9);
			btnCancel.setTypeface(face);
			btnCancel.setText("မလုပ္ပါ");
			btnCancel.setTextSize(9);
		}
		else{
			startButton.setText("On");
			startButton.setTextSize(18);
			tv1.setText("Total Day");
			tv1.setTextSize(18);
			tv2.setText("Frequency by Day");
			tv2.setTextSize(18);
			tv3.setText("Duration");
			tv3.setTextSize(18);
			tv4.setText("Hr");
			tv4.setTextSize(18);
			tv5.setText("Min");
			tv5.setTextSize(18);
			btntime.setText("Start Time");
			btntime.setTextSize(18);
			btnSubmit.setText("Submit");
			btnSubmit.setTextSize(18);
			btnCancel.setText("Cancel");
			btnCancel.setTextSize(18);
		}
	}
	
	btnCancel.setOnClickListener(new View.OnClickListener() {

		public void onClick(View arg0) {

			durHr.setText("");
			durMin.setText("");
			//date.setText("");
			time.setText("");
			freq1.setText("");
			day1.setText("");

		}
	});

	
	btntime.setOnClickListener(new View.OnClickListener() {

		public void onClick(View v) {
			
			new TimePickerDialog(Alarm.this,t,
					dateAndTime.get(Calendar.HOUR_OF_DAY),
					dateAndTime.get(Calendar.MINUTE),true).show();
			time=(TextView)findViewById(R.id.timeTv);
			updateTime();
			}
	});
	setUiEnabled(false);
	}
	public void setUiEnabled(boolean bool)
	{
	startButton.setEnabled(!bool);
	btnSubmit.setEnabled(bool);
	//btnCancel.setEnabled(bool);
	
	}
	public boolean BTinit()
	{
	boolean found=false;
	BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
	if (bluetoothAdapter == null) {
		if(cl==1){
			Toast.makeText(getApplicationContext(),"Device သည္ Bluetooth ကိုsupportမ ေပးပါ",Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
		}
	}
	if(!bluetoothAdapter.isEnabled())
	{
	Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	startActivityForResult(enableAdapter, 0);
	try {
	Thread.sleep(1000);
	} catch (InterruptedException e) {
	e.printStackTrace();
	}
	}
	Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
	if(bondedDevices.isEmpty())
	{
		if(cl==1){
			Toast.makeText(getApplicationContext(),"ပထမဆံုးDeviceကိုခ်ိတ္ပါ",Toast.LENGTH_SHORT).show();
			
		}
		else{
			Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
		}
	}
	else
	{
	for (BluetoothDevice iterator : bondedDevices)
	{
	if(iterator.getAddress().equals(DEVICE_ADDRESS))
	{
	device=iterator;
	found=true;
	break;
	}
	}
	}
	return found;
	}
	public boolean BTconnect()
	{
	boolean connected=true;
	try {
	socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
	socket.connect();
	} catch (IOException e) {
	e.printStackTrace();
	connected=false;
	}
	if(connected)
	{
	try {
	outputStream=socket.getOutputStream();
	} catch (IOException e) {
	e.printStackTrace();
	}
	try {
	inputStream=socket.getInputStream();
	} catch (IOException e) {
	e.printStackTrace();
	}
	}
	return connected;
	}
	public void onClickStart(View view) {
	if(BTinit())
	{
	if(BTconnect())
	{
	setUiEnabled(true);
	deviceConnected=true;
	beginListenForData();
	if(cl==1){
		Toast.makeText(getApplicationContext(),"\nConnection ရပါျပီ\n",Toast.LENGTH_LONG);
		
	}
	else{
		Toast.makeText(getApplicationContext(),"\nConnection Opened!\n",Toast.LENGTH_LONG);
		
	}
	}
	}
	}
	void beginListenForData()
	{
	final Handler handler = new Handler();
	stopThread = false;
	buffer = new byte[1024];
	Thread thread = new Thread(new Runnable()
	{
	public void run()
	{
	while(!Thread.currentThread().isInterrupted() && !stopThread)
	{
	try
	{
	int byteCount = inputStream.available();
	if(byteCount > 0)
	{
	byte[] rawBytes = new byte[byteCount];
	inputStream.read(rawBytes);
	final String string=new String(rawBytes,"UTF-8");
	handler.post(new Runnable() {
	public void run()
	{
	Toast.makeText(getApplicationContext(),string,Toast.LENGTH_LONG);
	}
	});
	}
	}
	catch (IOException ex)
	{
	stopThread = true;
	}
	}
	}
	});
	thread.start();
	}
	public void onClickSend(View view) throws IOException {
	/*String string = editText.getText().toString();
	string.concat("\n");
	try {
	outputStream.write(string.getBytes());
	} catch (IOException e) {
	e.printStackTrace();
	}
	textView.append("\nSent Data:"+string+"\n");
	}
	public void onClickStop(View view) throws IOException {
	stopThread = true;
	outputStream.close();
	inputStream.close();
	socket.close();
	setUiEnabled(false);
	deviceConnected=false;
	textView.append("\nConnection Closed!\n");*/
		
		
		if(freq1.getText().toString().equals("") || day1.getText().toString().equals("") || 
				time.getText().toString().equals("") || durMin.getText().toString().equals("")){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					Alarm.this);
			alertDialogBuilder.setTitle("Invalid Input!");
			alertDialogBuilder
					.setMessage("Please fill completely.")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialog, int id) {
									// Intent intent=new
									// Intent(Prediction.this,Prediction.class);
									// startActivity(intent);
								}
							});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			
			
		}
		else{
		ff = Integer.parseInt(freq1.getText().toString());
		dd = Integer.parseInt(day1.getText().toString());
		total=ff*dd;
		int durmin=Integer.parseInt(durMin.getText().toString());
		
		if(durmin>60){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					Alarm.this);
			alertDialogBuilder
					.setMessage("Please fill duration minute correctly.")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialog, int id) {
									// Intent intent=new
									// Intent(Prediction.this,Prediction.class);
									// startActivity(intent);
								}
							});

			AlertDialog alertDialog1 = alertDialogBuilder.create();
			alertDialog1.show();
		}
			if(durHr.getText().toString().equals("")){
				durHr.setText("0");
			}
		
		if(total>7){
			AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(
					Alarm.this);
			alertDialogBuilder.setTitle("Invalid Input!");
			alertDialogBuilder
					.setMessage("Please enter again because pill box has 7 cups to keep medicines.")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialog, int id) {
									// Intent intent=new
									// Intent(Prediction.this,Prediction.class);
									// startActivity(intent);
								}
							});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}
		else{
		String[] newTime=(time.getText().toString()).split(":");
		String dur=durHr.getText().toString()+":"+durMin.getText().toString();
		
		int newDurMin=(Integer.parseInt(durHr.getText().toString())*60)+Integer.parseInt(durMin.getText().toString());
		String allData=dd+","+newTime[0]+","+newTime[1]+","+newDurMin+","+ff;
		Toast.makeText(getApplicationContext(), allData,Toast.LENGTH_LONG).show();
		
		try {
		outputStream.write(allData.getBytes());
		} catch (IOException e) {
		e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), allData,Toast.LENGTH_LONG).show();
		
		boolean check=db.addAlarm(ff,dd,time.getText().toString(),dur);
		if(check==true){ 
			
			Toast.makeText(getApplicationContext(), allData+"Alarm Setting Done!",Toast.LENGTH_LONG).show();
			stopThread = true;
			outputStream.close();
			inputStream.close();
			socket.close();
			setUiEnabled(false);
			deviceConnected=false;
			Toast.makeText(getApplicationContext(),"\nConnection Closed!",Toast.LENGTH_LONG).show();
			Intent intent=new Intent(getApplicationContext(),MainActivity.class);
			startActivity(intent);
			}
		}
		}
		
		}
		
	
	public void onClickClear(View view) {
	//textView.setText("");
	}
	private void updateTime(){
		time.setText(
				new StringBuilder()
				.append(dateAndTime.get(Calendar.HOUR_OF_DAY)).append(":")
				.append(dateAndTime.get(Calendar.MINUTE)).append(""));
		
	}
	
}
