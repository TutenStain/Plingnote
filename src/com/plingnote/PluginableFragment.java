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
package com.plingnote;

/**
 * An interface representing the 
 * @author Julia Gustafsson
 *
 */
public interface PluginableFragment {
	
	public String getValue();
	
	public int getRequestCode();
	
	public NoteCategory getCategory();
	
	public NoteExtra getKind();
	
	public void replaceBackFragment();
	
	
}
