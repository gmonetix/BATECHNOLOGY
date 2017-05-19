package com.gmonetix.batechnology.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gmonetix.batechnology.R;

public class Utils {

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void openInBrowser(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Ops! , Cannot open url", Toast.LENGTH_LONG).show();
        }
    }

    public static void setFont(Context _context, TextView textView) {
        Typeface roboto = Typeface.createFromAsset(_context.getAssets(), "font/RobotoLight.ttf");
        textView.setTypeface(roboto);
    }

    public static void applyFontForToolbarTitle(Activity context){
        Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);
        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);
            if(view instanceof TextView){
                TextView tv = (TextView) view;
                if(tv.getText().equals(toolbar.getTitle())){
                    setFont(context,tv);
                    break;
                }
            }
        }
    }

}
