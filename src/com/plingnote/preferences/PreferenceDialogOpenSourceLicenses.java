package com.plingnote.preferences;

import android.app.AlertDialog;
import android.content.Context;
import android.preference.DialogPreference;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.plingnote.R;

public class PreferenceDialogOpenSourceLicenses extends DialogPreference{
	private String dialogeMessage;
	
	public PreferenceDialogOpenSourceLicenses(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.dialogeMessage = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(context);//attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "dialogMessage");
		setPersistent(false);
		setDialogLayoutResource(R.layout.preference_dialog_info);
	}
	
	@Override
	protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
		builder.setPositiveButton(null, null);
		builder.setNegativeButton(null, null);
		super.onPrepareDialogBuilder(builder);  
	}
	
	@Override
	public void onBindDialogView(View view){
		Button okButon = (Button)view.findViewById(R.id.info_ok_button);
		okButon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});
		
		TextView textView = (TextView)view.findViewById(R.id.info_textview);
		textView.setText(dialogeMessage);
		super.onBindDialogView(view);
	}
}
