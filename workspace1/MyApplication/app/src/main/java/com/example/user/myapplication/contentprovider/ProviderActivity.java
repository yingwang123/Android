/*
package com.example.user.myapplication.contentprovider;

import android.app.Activity;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.text.TextUtils;
import android.view.View;

*/
/**
 * Created by user on 2017/4/18.
 *//*

public class ProviderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View
        if(TextUtils.isEmpty(mSearchString)){
            mSelectionClause = null;
            mSelectionArgs[0] = "";
        }else {
            mSelectionClause=UserDictionary.Words.WORD+"=?";
            mSelectionArgs[0] = mSearchString;
        }
    }



    String[] mProjection={
            UserDictionary.Words._ID,
            UserDictionary.Words.WORD,
            UserDictionary.Words.LOCALE
    };
    String mSelectionClause = null;
    String [] mSelectionArgs = {""};
    String mSearchString = mSearchWord.getText().toString();















}
*/
