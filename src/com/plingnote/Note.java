package com.plingnote;

public class Note {
	private int id;
	private String title;
	private String text;
	private Location location;
	private String imagePath;
	private String alarm;

	/**
	 * 
	 * @param id id of this Note
	 * @param ti title of this Note
	 * @param txt text of this Note
	 * @param l location of this Note
	 */
	public Note(int id, String ti, String txt, Location l, String path, String alarm){
		this.id = id;
		this.title = ti;
		this.text = txt;
		this.location = l;
		this.imagePath = path;
		this.alarm = alarm;
	}
	
	/**
	 * 
	 * @return id of this Note
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * 
	 * @return title of this Note
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * 
	 * @return text of this Note
	 */
	public String getText(){
		return this.text;
	}

	/**
	 * 
	 * @return location of this Note
	 */
	public Location getLocation(){
		return this.location;
	}
	
	/**
	 * 
	 * @return image path of the image representing this Note
	 */
	public String getImagePath(){
		return this.imagePath;
	}
	
	/**
	 * 
	 * @return date and time when the alarm of this Note will trigger
	 */
	public String getAlarm(){
		return this.alarm;
	}
	

}
