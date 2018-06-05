package com.kakaologinexample;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.dooboolab.kakaologins.RNKakaoLoginsModule;
import com.facebook.react.ReactActivity;

public class MainActivity extends ReactActivity {

  private RNKakaoLoginsModule.SessionCallback callback;
    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "KakaoLoginExample";
    }
}
