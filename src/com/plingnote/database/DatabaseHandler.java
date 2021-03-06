/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 David Grankvist, Barnabas Sapan
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

package com.plingnote.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import com.plingnote.snotebar.NoteCategory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class is used for creating the database and storing data in it.
 * It also includes full text search.
 * 
 * @author David Grankvist
 * @Modifiedby Barnabas Sapan
 *
 */
public class DatabaseHandler extends Observable{
	// Name of database
	private static final String DATABASE_NAME = "plingnotedb";
	
	//Database version, change this before upgrading the database
	private static final int DATABASE_VERSION = 2;
	
	// Table
	private static final String TABLE_NOTE = "Note";
	
	// Columns
	private static final String ID = "docid"; //Automagical id. Automatically increments for each new entry
	private static final String KEY_TEXT = "Text";
	private static final String KEY_TITLE = "Title";
	private static final String KEY_LONGITUDE = "Longitude";
	private static final String KEY_LATITUDE = "Latitude";
	private static final String KEY_IMAGEPATH = "ImagePath";
	private static final String KEY_ALARM = "Alarm";
	private static final String KEY_DATE = "Date";
	private static final String KEY_CATEGORY = "Category";
	private static final String KEY_ADDRESS = "Address";
	private static final String KEY_REQUEST_CODE = "RequestCode";
	
	// SQL statement to create Note table using fts4
	private static final String CREATE_FTS_TABLE = "create virtual table " 
			+ TABLE_NOTE + " using fts4("+ KEY_TITLE + " String, " 
			+ KEY_TEXT + " String, " + KEY_LONGITUDE +" Double not null, "
			+ KEY_LATITUDE +" Double not null, " + KEY_IMAGEPATH + " String, " 
			+ KEY_ALARM + " String, " + KEY_DATE + " String, " 
			+ KEY_CATEGORY + " int, " + KEY_ADDRESS + " String, " 
			+ KEY_REQUEST_CODE + " int);";
	
	private Context context;
	private DBHelper dbHelp;
	private SQLiteDatabase db;
	private static DatabaseHandler instance = null;

	/**
	 * @param con The context
	 * @return The singleton instance
	 */
	public static DatabaseHandler getInstance(Context context){
		if(instance == null)
			instance = new DatabaseHandler(context);
		return instance;
	}

	private DatabaseHandler(Context context){
		this.context = context;
		this.dbHelp = new DBHelper(this.context);
	}

	private class DBHelper extends SQLiteOpenHelper{
		DBHelper(Context con){
			super(con, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_FTS_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(DatabaseHandler.class.getName(), "Upgrading database from version " + oldVersion + " to "
			            + newVersion + ", which will destroy all old data");
			
			db.execSQL("drop table if exists " + TABLE_NOTE);

			//Recreate the db
			onCreate(db);
		}
	}

	/**
	 * @param title Title of the note to insert
	 * @param text Text of the note to insert
	 * @param l Location of the note to insert
	 * @param path ImagePath of the note to insert
	 * @param alarm Alarm date of the note to insert
	 * @param ncat Category of the note to insert
	 * @param adr Address of the note to insert
	 * @return Id or -1 if an error occurred
	 */
	public long insertNote(String title, String text, Location l, 
			String path, String alarm, NoteCategory ncat, String adr){
		//Set default values to prevent null in database
		if(title == null)
			title = "";
		if(text == null)
			text = "";
		if(l == null)
			l = new Location(0.0, 0.0);
		if(path == null)
			path = "";
		if(alarm == null)
			alarm = "";
		if(ncat == null)
			ncat = NoteCategory.NO_CATEGORY;
		if(adr == null)
			adr = "";
		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, title);
		cv.put(KEY_TEXT, text);
		cv.put(KEY_LONGITUDE, l.getLongitude());
		cv.put(KEY_LATITUDE, l.getLatitude());
		cv.put(KEY_IMAGEPATH, path);
		cv.put(KEY_ALARM, alarm);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date = new Date();
		cv.put(KEY_DATE, dateFormat.format(date));
		cv.put(KEY_CATEGORY, ncat.ordinal());
		cv.put(KEY_ADDRESS, adr);
		cv.put(KEY_REQUEST_CODE, -1);
		this.open();
		long tmp = this.db.insert(TABLE_NOTE, null, cv);
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.NEW_NOTE);
		return tmp;
	}

	/**
	 * @param  id Id of the Note to delete
	 * @return true if the whereclause is passed in, false otherwise
	 */
	public boolean deleteNote(int id){
		this.open();
		boolean b = this.db.delete(TABLE_NOTE, ID + "=" + id, null) > 0;
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.DELETED_NOTE);
		return b;
	}

	/**
	 * @param id Id of the note to delete the title from
	 * @return true if database was updated, false otherwise
	 */
	public boolean deleteTitle(int id){
		boolean b = this.updateTitle(id, "");
		return b;
	}

	/**
	 * @param id Id of the note to delete the text from
	 * @return true if database was updated, false otherwise
	 */
	public boolean deleteText(int id){
		boolean b = this.updateTitle(id, "");
		return b;
	}

	/**
	 * Sets the location to default (0.0, 0.0) rather than deleting it
	 * 
	 * @param id Id of the note to delete the location from
	 * @return true if database was updated, false otherwise
	 */
	public boolean deleteLocation(int id){
		boolean b = this.updateLocation(id, new Location(0.0, 0.0));
		return b;
	}

	/**
	 * @param id Id of the note to delete the image path from
	 * @return true if database was updated, false otherwise
	 */
	public boolean deleteImagePath(int id){
		boolean b = this.updateImagePath(id, "");
		return b;
	}

	/**
	 * @param id Id of the note to delete the alarm from
	 * @return true if database was updated, false otherwise
	 */
	public boolean deleteAlarm(int id){
		boolean b = this.updateAlarm(id, "");
		return b;
	}

	/**
	 * This method will set the category to 
	 * NoteCategory.NO_CATEGORY rather than clearing the field
	 * 
	 * @param id Id of the note to delete the category from
	 * @return true if database was updated, false otherwise
	 */
	public boolean deleteCategory(int id){
		boolean b = this.updateCategory(id, NoteCategory.NO_CATEGORY);
		return b;
	}

	/**
	 * @param id Id of the note to delete the address from
	 * @return true if database was updated, false otherwise
	 */
	public boolean deleteAddress(int id){
		boolean b = this.updateAddress(id, "");
		return b;
	}

	/**
	 * Sets the request code to -1 rather than deleting it
	 * 
	 * @param id Id of the note to delete the request code from
	 * @return true if database was updated, false otherwise
	 */
	public boolean deleteRequestCode(int id){
		boolean b = this.updateRequestCode(id, -1);
		return b;
	}

	/**
	 * Deletes all notes in the database
	 */
	public void deleteAllNotes(){
		this.open();
		this.db.delete(TABLE_NOTE, null, null);
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.DELETED_NOTE);
	}


	/**
	 * Deletes all notes in the database
	 * but does not notify observers
	 */
	public void deleteAllNotesInTestmode(){
		this.open();
		this.db.delete(TABLE_NOTE, null, null);
		this.close();
	}

	/**
	 * @return All data in the note table represented as a list of Note objects
	 */
	public List<Note> getNoteList(){
		this.open();
		Cursor c = this.getAllNotes();
		List<Note> l = this.createNoteList(c);
		this.close();
		return l;
	}

	private Cursor getAllNotes(){
		return this.db.query(TABLE_NOTE, new String[]{ ID, KEY_TITLE, KEY_TEXT,
				KEY_LONGITUDE, KEY_LATITUDE, KEY_IMAGEPATH, 
				KEY_ALARM, KEY_DATE, KEY_CATEGORY, KEY_ADDRESS, KEY_REQUEST_CODE },
				null, null,null, null, null);
	}

	/**
	 * @param id Id of the note to update
	 * @param title Title to update to
	 * @param text Text to update to
	 * @param l Location to update to
	 * @param path ImagePath to update to
	 * @param alarm Alarm to update to
	 * @param ncat Category to update to
	 * @param adr Address to update to
	 * @return true if database was updated, false otherwise
	 */
	public boolean updateNote(int id, String title, String text, Location l, 
			String path, String alarm, NoteCategory ncat, String adr){
		//Set default values to prevent null in database
		if(title == null)
			title = "";
		if(text == null)
			text = "";
		if(l == null)
			l = new Location(0.0, 0.0);
		if(path == null)
			path = "";
		if(alarm == null)
			alarm = "";
		if(ncat == null)
			ncat = NoteCategory.NO_CATEGORY;
		if(adr == null)
			adr = "";
		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, title);
		cv.put(KEY_TEXT, text);
		cv.put(KEY_LONGITUDE, l.getLongitude());
		cv.put(KEY_LATITUDE, l.getLatitude());
		cv.put(KEY_IMAGEPATH, path);
		cv.put(KEY_ALARM, alarm);
		cv.put(KEY_CATEGORY, ncat.ordinal());
		cv.put(KEY_ADDRESS, adr);
		this.open();
		boolean b = this.db.update(TABLE_NOTE, cv, ID + "=" + id, null) > 0;
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.UPDATED_NOTE);
		return b;
	}

	/**
	 * @param id Id of the note to update
	 * @param title The title to update to
	 * @return true if database was updated, false otherwise 
	 */
	public boolean updateTitle(int id, String title){
		if(title == null)
			title = "";
		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, title);
		this.open();
		boolean b = this.db.update(TABLE_NOTE, cv, ID + "=" + id, null) > 0;
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.UPDATED_NOTE);
		return b;
	}
	
	/**
	 * @param id Id of the note to update
	 * @param text The text to update to
	 * @return true if database was updated, false otherwise 
	 */
	public boolean updateText(int id, String text){
		if(text == null)
			text = "";
		ContentValues cv = new ContentValues();
		cv.put(KEY_TEXT, text);
		this.open();
		boolean b = this.db.update(TABLE_NOTE, cv, ID + "=" + id, null) > 0;
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.UPDATED_NOTE);
		return b;
	}

	/**
	 * @param id Id of the note to update
	 * @param l The Location object with the longitude and latitude to update to
	 * @return true if database was updated, false otherwise 
	 */
	public boolean updateLocation(int id, Location l){
		if(l == null)
			l = new Location(0.0, 0.0);
		ContentValues cv = new ContentValues();
		cv.put(KEY_LONGITUDE, l.getLongitude());
		cv.put(KEY_LATITUDE, l.getLatitude());
		this.open();
		boolean b = this.db.update(TABLE_NOTE, cv, ID + "=" + id, null) > 0;
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.UPDATED_LOCATION);
		return b;
	}

	/**
	 * @param id Id of the note to update
	 * @param path The image path to update to
	 * @return true if database was updated, false otherwise 
	 */
	public boolean updateImagePath(int id, String path){
		if(path == null)
			path = "";
		ContentValues cv = new ContentValues();
		cv.put(KEY_IMAGEPATH, path);
		this.open();
		boolean b = this.db.update(TABLE_NOTE, cv, ID + "=" + id, null) > 0;
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.UPDATED_NOTE);
		return b;
	}

	/**
	 * @param id Id of the note to update
	 * @param alarm The alarm date to update to
	 * @return true if database was updated, false otherwise 
	 */
	public boolean updateAlarm(int id, String alarm){
		if(alarm == null)
			alarm = "";
		ContentValues cv = new ContentValues();
		cv.put(KEY_ALARM, alarm);
		this.open();
		boolean b = this.db.update(TABLE_NOTE, cv, ID + "=" + id, null) > 0;
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.UPDATED_NOTE);
		return b;
	}

	/**
	 * @param id Id of the note which date will be refreshed
	 * @return true if database was updated, false otherwise
	 */
	public boolean refreshDate(int id){
		ContentValues cv = new ContentValues();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date = new Date();
		cv.put(KEY_DATE, dateFormat.format(date));
		this.open();
		boolean b = this.db.update(TABLE_NOTE, cv, ID + "=" + id, null) > 0;
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.UPDATED_NOTE);
		return b;
	}

	/**
	 * @param id Id of the note which category will be updated
	 * @param ncat The category to update to
	 * @return true if database was updated, false otherwise
	 */
	public boolean updateCategory(int id, NoteCategory ncat){
		if(ncat == null)
			ncat = NoteCategory.NO_CATEGORY;
		ContentValues cv = new ContentValues();
		cv.put(KEY_CATEGORY, ncat.ordinal());
		this.open();
		boolean b = this.db.update(TABLE_NOTE, cv, ID + "=" + id, null) > 0;
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.UPDATED_NOTE);
		return b;
	}

	/**
	 * @param id Id of the note which address will be updated
	 * @param adr The address to update to
	 * @return true if database was updated, false otherwise
	 */
	public boolean updateAddress(int id, String adr){
		if(adr == null)
			adr = "";
		ContentValues cv = new ContentValues();
		cv.put(KEY_ADDRESS, adr);
		this.open();
		boolean b = this.db.update(TABLE_NOTE, cv, ID + "=" + id, null) > 0;
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.UPDATED_NOTE);
		return b;
	}

	/**
	 * @param id Id of the note which request code will be updated
	 * @param adr The request code to update to
	 * @return true if database was updated, false otherwise
	 */
	public boolean updateRequestCode(int id, int rCode){
		ContentValues cv = new ContentValues();
		cv.put(KEY_REQUEST_CODE, rCode);
		this.open();
		boolean b = this.db.update(TABLE_NOTE, cv, ID + "=" + id, null) > 0;
		this.close();
		this.setChanged();
		this.notifyObservers(DatabaseUpdate.UPDATED_NOTE);
		return b;
	}
	/**
	 * @param id Id of the row to retrieve data from
	 * @return a Note object containing all data from the selected row
	 */
	public Note getNote(int id){
		this.open();
		Cursor c = this.findNoteById(id);
		c.moveToFirst();
		String title = c.getString(1);
		String text = c.getString(2);
		Double longitude = c.getDouble(3);
		Double latitude = c.getDouble(4);
		String imagePath = c.getString(5);
		String alarm = c.getString(6);
		String date = c.getString(7);
		NoteCategory ncat = NoteCategory.values()[c.getInt(8)];
		String address = c.getString(9);
		int requestCode = c.getInt(10);
		Note n = new Note(id, title, text, new Location(longitude, latitude), 
				imagePath, alarm, date, ncat, address, requestCode);
		this.close();
		return n;
	}

	private Cursor findNoteById(int id){
		return this.db.rawQuery("select " + ID + ", * from " 
				+ TABLE_NOTE + " where " + ID + "='" + id + "'", null);
	}

	/**
	 * @return The highest request code in the database
	 */
	public int getHighestRequest(){
		List<Note> nlist = this.getNoteList();
		int tmp = nlist.get(0).getRequestCode();
		for(Note n: nlist)
			if(n.getRequestCode() > tmp)
				tmp = n.getRequestCode();
		return tmp;
	}

	/**
	 * @return Id of the latest inserted Note
	 */
	public int getLastId(){
		this.open();
		Cursor c = this.db.rawQuery("select " + ID + " from " 
				+ TABLE_NOTE + " order by " + ID + " desc limit 1", null);
		c.moveToFirst();
		int id = c.getInt(0);
		this.close();
		return id;
	}

	/**
	 * @param s The string to search the database with
	 * @return A list of Note objects with at least one field matching the search
	 */
	public List<Note> search(String s){
		this.open();
		Cursor c = this.db.rawQuery("select " + ID + ", * from " + TABLE_NOTE
		+ " where " + TABLE_NOTE + " match '" + s + "*'", null);
		List<Note> l = this.createNoteList(c);
		this.close();
		return l;
	}

	/**
	 * @param c the cursor to create the note list from
	 * @return the resulting list from the cursor
	 */
	private List<Note> createNoteList(Cursor c){
		List<Note> l = new ArrayList<Note>();
		if(c.moveToFirst()){
			do{
				int id = c.getInt(0);
				String title = c.getString(1);
				String text = c.getString(2);
				Double longitude = c.getDouble(3);
				Double latitude = c.getDouble(4);
				String imagePath = c.getString(5);
				String alarm = c.getString(6);
				String date = c.getString(7);
				NoteCategory ncat = NoteCategory.values()[c.getInt(8)];
				String address = c.getString(9);
				int requestCode = c.getInt(10);
				l.add(new Note(id, title, text, new Location(longitude, latitude), 
						imagePath, alarm, date, ncat, address, requestCode));
			}while(c.moveToNext());
		}
		return l;
	}

	private DatabaseHandler open() throws SQLException{
			this.db = this.dbHelp.getWritableDatabase();
		return this;
	}

	private void close(){
		if(db != null)
			this.db.close();
	}
}
