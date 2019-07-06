package com.example.smartpillbox27;

public class Schedule {
	
	    //private variables
	    int aid;
	    int f1,d1;
	    String da1,t1,du1;

	    // Empty constructor
	    public Schedule(){

	    }
	    // constructor
	    public Schedule(int aid, int f1, int d1, String da1, String t1, String du1){
	        this.aid = aid;
	        this.f1=f1;
	        this.d1=d1;
	        this.da1=da1;
	        this.du1=du1;
	    }

	    // constructor
	    
	    // getting ID
	    public int getID(){
	        return this.aid;
	    }

	    // setting id
	    public void setID(int aid){
	        this.aid = aid;
	    }

	   
	    public int getFrequency(){
	        return this.f1;
	    }

	   
	    public void setFrequency(int f1){
	        this.f1 = f1;
	    }

	   
	    public int getDay(){
	        return this.d1;
	    }

	   
	    public void setDay(int d1){
	        this.d1 = d1;
	    }
	    
	    public String getStartTime(){
	        return this.t1;
	    }

	   
	    public void setStartTime(String t1){
	        this.t1 = t1;
	    }
	    public String getStartDate(){
	        return this.da1;
	    }

	   
	    public void setStartDate(String da1){
	        this.da1 = da1;
	    }
	    public String getDuration(){
	        return this.du1;
	    }

	   
	    public void setDuration(String du1){
	        this.du1 = du1;
	    }

}
