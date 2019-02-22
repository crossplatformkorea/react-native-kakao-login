package com.dooboolab.kakaologins;

import android.content.Context;

import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;

public class KakaoSDKAdapter extends KakaoAdapter{
    private Context context;

    public KakaoSDKAdapter(Context mContext) {
        context = mContext;
    }

    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {
            @Override
            public Context getApplicationContext() {
                return context;
            }
        };
    }
}
