/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Barnabas sapan
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

package com.plingnote.map;

/**
 * Interface to indicate that this overlay can be
 * updated. Not guaranteed that the overlay is updated
 * via the location argument.
 * 
 * @author Barnabas Sapan
 */

public interface UpdatableOverlay {
	public void update(android.location.Location location);
}
