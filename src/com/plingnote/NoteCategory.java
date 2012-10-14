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
 * A simple enum representing all of the note categories.
 * Each category is associated to an image name for convenience when displaying the images.
 * 
 * @author David Grankvist
 *
 */
public enum NoteCategory {
	NO_CATEGORY(""),
	Banking("bank"),
	Lunch("lunch"),
	Fun("fun"),
	Chat("chat"),
	Meeting("meeting"),
	Shop("shop"),
	Write("write")
	;
	private final String imageName;
	
	private NoteCategory(final String name){
		this.imageName = name;
	}
	
	@Override
	public String toString(){
		return this.imageName;
	}
}
