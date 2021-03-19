package com.kakaologinexample;

import android.content.res.Configuration;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView;

public class MainActivity extends ReactActivity {

    static String currentLocale;

    /**
     * Returns the name of the main component registered from JavaScript.
     *  This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "KakaoLoginExample";
    }

    @Override
    protected ReactActivityDelegate createReactActivityDelegate() {
        return new ReactActivityDelegate(this, getMainComponentName()) {
            @Override
            protected ReactRootView createRootView() {
                return new RNGestureHandlerEnabledRootView(MainActivity.this);
            }
        };
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        String locale = newConfig.locale.toString();
        if (!locale.equals(MainActivity.currentLocale)) {
            MainActivity.currentLocale = locale;
            final ReactInstanceManager instanceManager = getReactInstanceManager();
            instanceManager.recreateReactContextInBackground();
        }
    }
}
