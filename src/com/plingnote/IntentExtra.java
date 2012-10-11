package com.plingnote;

public enum IntentExtra {
	id("id"),
	reminderDone("reminderDone"),
	longitude("longitude"),
	latitude("latitude"),
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