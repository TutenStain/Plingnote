/**
 * This file is part of Plingnote.
 * Copyright (C) 2012 Barnabas Sapan
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

package com.plingnote.search;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Our content provider class that is needed for 
 * recent query suggestions.
 * 
 * @author Barnabas Sapan
 */
public class SearchSuggestionsRecentProvider extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = "com.plingnote.search.SearchSuggestionsRecentProvider";
	public final static int MODE = DATABASE_MODE_QUERIES;

	public SearchSuggestionsRecentProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}
}
