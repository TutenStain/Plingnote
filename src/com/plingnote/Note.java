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
	private NoteCategory category;

	/**
	 * 
	 * @param id Id of this Note
	 * @param ti Title of this Note
	 * @param txt Text of this Note
	 * @param l Location of this Note
	 */
	public Note(int id, String ti, String txt, Location l, String path, 
			String alarm, String date, NoteCategory ncat){
		this.id = id;
		this.title = ti;
		this.text = txt;
		this.location = l;
		this.imagePath = path;
		this.alarm = alarm;
		this.date = date;
		this.category = ncat;
	}
	
	/**
	 * 
	 * @return Id of this Note
	 */
	public int getId(){
		return this.id;
	}
	/**
	 * 
	 * @return Title of this Note
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * 
	 * @return Text of this Note
	 */
	public String getText(){
		return this.text;
	}

	/**
	 * 
	 * @return Location of this Note
	 */
	public Location getLocation(){
		return this.location;
	}
	
	/**
	 * 
	 * @return Image path of the image representing this Note
	 */
	public String getImagePath(){
		return this.imagePath;
	}
	
	/**
	 * 
	 * @return Date and time when the alarm of this Note will trigger
	 */
	public String getAlarm(){
		return this.alarm;
	}
	
	/**
	 * 
	 * @return Date and time of which this Note was created
	 */
	public String getDate(){
		return this.date;
	}
	
	/**
	 * 
	 * @return Category of this note as an enum cate
	 */
	public NoteCategory getCategory(){
		return this.category;
	}
}
