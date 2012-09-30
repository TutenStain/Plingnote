package com.plingnote;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;


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

