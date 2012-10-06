package com.plingnote;

import android.support.v4.app.Fragment;

/**
 * An interface representing the 
 * @author Julia Gustafsson
 *
 */
public interface PluginFragment {
	
	public String getValue();
	
	public Location getLocation();
	
	public NoteExtra getKind();
	
	public void sendthisFragment();
	
}
