package com.bhavesh.surveyapp.utils.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class RobotoRegularTextView extends TextView {
    public RobotoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto_Regular.ttf");

        setTypeface(tf);
    }
}
