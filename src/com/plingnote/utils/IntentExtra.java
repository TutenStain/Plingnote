/**
* This file is part of Plingnote.
* Copyright (C) 2012 Julia Gustafsson
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
package com.plingnote.utils;

/**
 * Containg string constans often usen with intent
 * @author Julia Gustafsson
 *
 */
public enum IntentExtra {
	id("id"),
	reminderDone("reminderDone"),
	longitude("longitude"),
	latitude("latitude"),
	city("city"),
	justId("justID"),
	requestCode("requestCode");
	;
	private final String text;
	private IntentExtra(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
