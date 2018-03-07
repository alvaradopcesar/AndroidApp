package iosco.app.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by usuario on 26/02/2016.
 */
public class TextViewPro extends TextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public TextViewPro(Context context) {
        super(context);

        applyCustomFont(context, null);
    }

    public TextViewPro(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public TextViewPro(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/"+attrs.getAttributeValue("pro","font")));
    }

}