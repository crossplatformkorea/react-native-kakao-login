import {withAndroidKakaoLogin} from './android/withAndroidKakaoLogin';
import {withIosKakaoLogin} from './ios/withIosKakaoLogin';
import {ConfigPlugin, createRunOncePlugin} from '@expo/config-plugins';

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

const pak = require('@react-native-seoul/kakao-login/package.json');
export default createRunOncePlugin(
  withExpoConfigPlugins,
  pak.name,
  pak.version,
);
