package iosco.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by usuario on 16/02/2016.
 */
public class Global {
    public static String getUserToken(Context c){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(c);
        return pref.getString("token_type","nope")+" "+pref.getString("access_token","nope");
    }


    public static long getSurveyEnableDate(Context c){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(c);

        return pref.getLong("surveyDateEnabled",0 );

    }
}
