package com.dooboolab.kakaologins;

/**
 * Created by hyochan on 2017. 12. 12..
 */

import android.app.Application;
import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;

public class GlobalApplication extends Application {
  private static Context context;

  private static class KakaoSDKAdapter extends KakaoAdapter {
    /**
     * Session Config에 대해서는 default값들이 존재한다.
     * 필요한 상황에서만 override해서 사용하면 됨.
     * @return Session의 설정값.
     */
    @Override
    public ISessionConfig getSessionConfig() {
      return new ISessionConfig() {
        @Override
        public AuthType[] getAuthTypes() {
          return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
        }

        @Override
        public boolean isUsingWebviewTimer() {
          return false;
        }

        @Override
        public boolean isSecureMode() {
          return false;
        }

        @Override
        public ApprovalType getApprovalType() {
          return ApprovalType.INDIVIDUAL;
        }

        @Override
        public boolean isSaveFormData() {
          return true;
        }
      };
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

  @Override
  public void onCreate() {
    super.onCreate();
    context = this.getApplicationContext();
    KakaoSDK.init(new KakaoSDKAdapter());
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    Session.getCurrentSession().removeCallback(RNKakaoLoginsModule.callback);
  }


}