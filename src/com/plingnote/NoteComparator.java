package com.plingnote;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Class comparing the dates of two notes.
 * @author Linus Karlsson
 *
 */
public class NoteComparator implements Comparator<Note>{

	public int compare(Note n1, Note n2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		
		// Cast the strings to Date's. If casting fails, return 0.
		try {
			return dateFormat.parse(n2.getDate()).compareTo(dateFormat.parse(n1.getDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
