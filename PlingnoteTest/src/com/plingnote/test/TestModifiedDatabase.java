package com.plingnote.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.plingnote.Location;
import com.plingnote.Note;
import com.plingnote.NoteCategory;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TestModifiedDatabase {
	protected ModifiedDatabaseHandler dbHandler;

	@Before
	public void setUp() {
		dbHandler = ModifiedDatabaseHandler.getInstance(new Activity());
		//opens a temporary database
		this.dbHandler.open();
	}

	@Test
	public void testInsertNote() throws Exception{
		// some input arguments
		String inputTitle = "some title";
		String inputText = "some text";
		Location inputLocation = new Location(4.3, 2.35135);
		String inputPath = "some image path";
		String inputAlarm = "some alarm";
		NoteCategory inputCategory = NoteCategory.Fun;
		String inputAddress = "some address";
		Assert.assertEquals(dbHandler.insertNote(inputTitle, inputText, inputLocation, inputPath, 
				inputAlarm, inputCategory, inputAddress), dbHandler.getLastId());
		Assert.assertEquals(dbHandler.insertNote(inputTitle, inputText, inputLocation, inputPath, 
				inputAlarm, inputCategory, inputAddress), dbHandler.getLastId());
		Assert.assertEquals(dbHandler.insertNote(inputTitle, inputText, inputLocation, inputPath, 
				inputAlarm, inputCategory, inputAddress), dbHandler.getLastId());
	}

	@Test
	public void testDeleteNote(){
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		Assert.assertTrue(this.dbHandler.deleteNote(this.dbHandler.getLastId()));
	}

	@Test
	public void testDeleteTitle(){
		this.dbHandler.insertNote("a title", null, null, null, null, null, null);
		Assert.assertTrue(this.dbHandler.deleteTitle(this.dbHandler.getLastId()));
	}

	@Test
	public void testDeleteText(){
		this.dbHandler.insertNote(null, "a text", null, null, null, null, null);
		Assert.assertTrue(this.dbHandler.deleteText(this.dbHandler.getLastId()));
	}

	@Test
	public void testDeleteLocation(){
		this.dbHandler.insertNote(null, null, new Location(2.3, 3.42), null, null, null, null);
		Assert.assertTrue(this.dbHandler.deleteLocation(this.dbHandler.getLastId()));
	}

	@Test
	public void testDeleteImagePath(){
		this.dbHandler.insertNote(null, null, null, "an image path", null, null, null);
		Assert.assertTrue(this.dbHandler.deleteImagePath(this.dbHandler.getLastId()));
	}

	@Test
	public void testDeleteAlarm(){
		this.dbHandler.insertNote(null, null, null, null, "an alarm", null, null);
		Assert.assertTrue(this.dbHandler.deleteAlarm(this.dbHandler.getLastId()));
	}

	@Test
	public void testDeleteCategory(){
		this.dbHandler.insertNote(null, null, null, null, null, NoteCategory.Fun, null);
		Assert.assertTrue(this.dbHandler.deleteCategory(this.dbHandler.getLastId()));
	}

	@Test
	public void testDeleteAddress(){
		this.dbHandler.insertNote(null, null, null, null, null, null, "an address");
		Assert.assertTrue(this.dbHandler.deleteAddress(this.dbHandler.getLastId()));
	}

	@Test
	public void testDeleteRequestCode(){
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		Assert.assertTrue(this.dbHandler.deleteRequestCode(this.dbHandler.getLastId()));
	}
	
	@Test
	public void testDeleteAllNotes(){
		this.dbHandler.deleteAllNotes();
		Assert.assertTrue(this.dbHandler.getNoteList().isEmpty());
	}
	
	@Test
	public void testGetNoteList(){
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		List<Note> firstList = this.dbHandler.getNoteList();
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		firstList.add(this.dbHandler.getNote(this.dbHandler.getLastId()));
		List<Note> secondList = this.dbHandler.getNoteList();
		Assert.assertEquals(firstList.get(firstList.size()-1).getId(), 
				secondList.get(secondList.size()-1).getId());
	}

	@Test
	public void testUpdateNote(){
		this.dbHandler.insertNote("a title", null, null, null, null, null, null);
		Assert.assertTrue(this.dbHandler.updateNote(this.dbHandler.getLastId(), 
				null, null, null, null, null, null, null));
	}

	@Test
	public void testUpdateTitle(){
		this.dbHandler.insertNote("a title", null, null, null, null, null, null);
		Assert.assertTrue(this.dbHandler.updateTitle(this.dbHandler.getLastId(), null));
	}

	@Test
	public void testUpdateText(){
		this.dbHandler.insertNote(null, "a text", null, null, null, null, null);
		Assert.assertTrue(this.dbHandler.updateText(this.dbHandler.getLastId(), null));
	}

	@Test
	public void testUpdateLocation(){
		this.dbHandler.insertNote(null, null, new Location(4.35315, 3.3), null, null, null, null);
		Assert.assertTrue(this.dbHandler.updateLocation(this.dbHandler.getLastId(), null));
	}

	@Test
	public void testUpdateImagePath(){
		this.dbHandler.insertNote(null, null, null, "an image path", null, null, null);
		Assert.assertTrue(this.dbHandler.updateImagePath(this.dbHandler.getLastId(), null));
	}

	@Test
	public void testUpdateAlarm(){
		this.dbHandler.insertNote(null, null, null, null, "an alarm", null, null);
		Assert.assertTrue(this.dbHandler.updateAlarm(this.dbHandler.getLastId(), null));
	}

	@Test
	public void testRefreshDate(){
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		Assert.assertTrue(this.dbHandler.refreshDate(this.dbHandler.getLastId()));
	}

	@Test
	public void testUpdateCategory(){
		this.dbHandler.insertNote(null, null, null, null, null, NoteCategory.Fun, null);
		Assert.assertTrue(this.dbHandler.updateCategory(this.dbHandler.getLastId(), null));
	}

	@Test
	public void testUpdateAddress(){
		this.dbHandler.insertNote(null, null, null, null, null, null, "an adress");
		Assert.assertTrue(this.dbHandler.updateAddress(this.dbHandler.getLastId(), null));
	}

	@Test
	public void testUpdateRequestCode(){
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		Assert.assertTrue(this.dbHandler.updateRequestCode(this.dbHandler.getLastId(), 5));
	}

	@Test
	public void testGetNote(){
		String inputTitle = "some title";
		String inputText = "some text";
		Location inputLocation = new Location(4.3, 2.35135);
		String inputPath = "some image path";
		String inputAlarm = "some alarm";
		NoteCategory inputCategory = NoteCategory.Fun;
		String inputAddress = "some address";
		this.dbHandler.insertNote(inputTitle, inputText, inputLocation, 
				inputPath, inputAlarm, inputCategory, inputAddress);
		Note n = this.dbHandler.getNote(this.dbHandler.getLastId());
		Assert.assertEquals(inputTitle,	n.getTitle());
		Assert.assertEquals(inputText,	n.getText());
		Assert.assertEquals(inputLocation.getLongitude(), n.getLocation().getLongitude());
		Assert.assertEquals(inputLocation.getLatitude(), n.getLocation().getLatitude());
		Assert.assertEquals(inputPath,	n.getImagePath());
		Assert.assertEquals(inputAlarm,	n.getAlarm());
		Assert.assertEquals(inputCategory,	n.getCategory());
		Assert.assertEquals(inputAddress,	n.getAddress());
	}
	
	@Test
	public void testGetHighestRequest(){
		this.dbHandler.deleteAllNotes();
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		this.dbHandler.updateRequestCode(this.dbHandler.getLastId(), 1);
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		this.dbHandler.updateRequestCode(this.dbHandler.getLastId(), 5);
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		this.dbHandler.updateRequestCode(this.dbHandler.getLastId(), 3);
		Assert.assertEquals(5, this.dbHandler.getHighestRequest());
	}
	
	@Test
	public void testGetLastId(){
		this.dbHandler.deleteAllNotes();
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		this.dbHandler.deleteNote(2);
		Assert.assertEquals(3, this.dbHandler.getLastId());
	}
	
	@Test
	public void testSearch(){
		this.dbHandler.deleteAllNotes();
		this.dbHandler.insertNote(null, "hello", null, null, null, null, null);
		this.dbHandler.insertNote(null, null, null, null, null, null, null);
		this.dbHandler.insertNote(null, null, null, null, null, null, "hello");
		List<Note> results = this.dbHandler.search("he");
		Assert.assertEquals("hello", results.get(0).getText());
		Assert.assertEquals("hello", results.get(1).getAddress());
	}
}
