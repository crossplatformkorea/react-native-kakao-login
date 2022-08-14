import {withAndroidKakaoLogin} from './android/withAndroidKakaoLogin';
import {withIosKakaoLogin} from './ios/withIosKakaoLogin';
import type {ConfigPlugin} from '@expo/config-plugins';

export interface KakaoLoginPluginProps {
  kakaoAppKey: string;
  overrideKakaoSDKVersion?: string;
  kotlinVersion?: string;
}

const withExpoConfigPlugins: ConfigPlugin<KakaoLoginPluginProps> = (
  config,
  props,
) => {
  config = withIosKakaoLogin(config, props);
  config = withAndroidKakaoLogin(config, props);

  return config;
};

export default withExpoConfigPlugins;
