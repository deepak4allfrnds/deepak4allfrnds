package com.bhavesh.surveyapp.utils.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class RobotoMediumTextView  extends TextView {
    public RobotoMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto_Medium.ttf");

        setTypeface(tf);
    }
}
