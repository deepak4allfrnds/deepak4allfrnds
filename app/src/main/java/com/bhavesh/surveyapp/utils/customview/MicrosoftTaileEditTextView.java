package com.bhavesh.surveyapp.utils.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class MicrosoftTaileEditTextView extends EditText {
    public MicrosoftTaileEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Microsoft_taile.ttf");
        setTypeface(tf);
    }
}
