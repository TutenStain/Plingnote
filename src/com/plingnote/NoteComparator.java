/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Linus Karlsson
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Class comparing the dates of two notes.
 * 
 * @author Linus Karlsson
 * 
 */
public class NoteComparator implements Comparator<Note> {

	public int compare(Note n1, Note n2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		// Cast the strings to Date's. If casting fails, return 0.
		try {
			return dateFormat.parse(n2.getDate()).compareTo(
					dateFormat.parse(n1.getDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
