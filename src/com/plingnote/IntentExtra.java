package com.plingnote;

public enum IntentExtra {

	rowId("rowId"),
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
