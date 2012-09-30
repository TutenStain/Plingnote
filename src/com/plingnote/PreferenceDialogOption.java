package com.plingnote;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * A custom dialog that displays a confirmation message
 * before the value is persisted.
 */
public class PreferenceDialogOption extends DialogPreference {

    public PreferenceDialogOption(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        persistBoolean(positiveResult);
    }

}

