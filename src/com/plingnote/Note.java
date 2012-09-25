package com.plingnote;


public class Note {
	private Integer rowId;
	private String title;
	private String text;
	private Location location;
	private String imagePath;

	/**
	 * 
	 * @param id row id of this Note
	 * @param ti title of this Note
	 * @param txt text of this Note
	 * @param l location of this Note
	 */
	public Note(Integer id, String ti, String txt, Location l, String path){
		this.rowId = id;
		this.title = ti;
		this.text = txt;
		this.location = l;
		this.imagePath = path;
	}
	
	/**
	 * 
	 * @return row id of this Note
	 */
	public Integer getRowId(){
		return this.rowId;
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
	
}
