import { ConfigPlugin, createRunOncePlugin } from '@expo/config-plugins';
import { withAndroidKakaoLogin } from './android/withAndroidKakaoLogin';
import { withIosKakaoLogin } from './ios/withIosKakaoLogin';

export interface KakaoLoginPluginProps {
  kakaoAppKey: string;
  overrideKakaoSDKVersion?: string;
}

const withExpoConfigPlugins: ConfigPlugin<KakaoLoginPluginProps> = (
  config,
  props,
) => {
  config = withIosKakaoLogin(config, props);
  config = withAndroidKakaoLogin(config, props);

  return config;
};

const pak = require('@react-native-seoul/kakao-login/package.json');
export default createRunOncePlugin(
  withExpoConfigPlugins,
  pak.name,
  pak.version,
);
