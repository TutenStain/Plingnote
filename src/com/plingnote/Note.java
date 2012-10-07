/**
* This file is part of Plingnote.
* Copyright (C) 2012 David Grankvist
*
* Plingnote is free software: you can redistribute it and/or modify it under
* the terms of the GNU General Public License as published by the Free Software
* Foundation, either version 3 of the License, or any later version.
*
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
* details.
*
* You should have received a copy of the GNU General Public License along with
* this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.plingnote;

/**
 * This class represents a note in the application
 * 
 * @author David Grankvist
 *
 */
public class Note {
	private int id;
	private String title;
	private String text;
	private Location location;
	private String imagePath;
	private String alarm;
	private String date;

	/**
	 * 
	 * @param id id of this Note
	 * @param ti title of this Note
	 * @param txt text of this Note
	 * @param l location of this Note
	 */
	public Note(int id, String ti, String txt, Location l, String path, String alarm, String date){
		this.id = id;
		this.title = ti;
		this.text = txt;
		this.location = l;
		this.imagePath = path;
		this.alarm = alarm;
		this.date = date;
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
	
	/**
	 * 
	 * @return date and time of which this Note was created
	 */
	public String getDate(){
		return this.date;
	}
}
