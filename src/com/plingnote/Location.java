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
 * This class respresents a location on the map
 * 
 * @author David Grankvist
 *
 */
public class Location {
	private Double longitude;
	private Double latitude;

	/**
	 * 
	 * @param lng longitude of this Location
	 * @param lat latitude of this Location
	 */
	public Location(Double lng, Double lat){
		this.longitude = lng;
		this.latitude = lat;
	}

	/**
	 * 
	 * @return longitude of this Location
	 */
	public Double getLongitude(){
		return this.longitude;
	}

	/**
	 * 
	 * @return latitude of this Location
	 */
	public Double getLatitude(){
		return this.latitude;
	}
}
