package com.kanzelmeyer.json; // <--- Update as necessary

public class Entry {
	public String date;
	public String time;
	public String description;
	
	/**************** SET METHONDS ****************/
	
	public void setDate(String str) {
		this.date = str;
	}
	public void setTime(String str) {
		this.time = str;
	}
	public void setDescription(String str) {
		this.description = str;
	}
	
	/**************** GET METHONDS ****************/
	
	public String getDate() {
		return this.date;
	}
	public String getTime() {
		return this.time;
	}
	public String getDescription() {
		return this.description;
	}
	
}
