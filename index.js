
import { NativeModules } from 'react-native';

const { RNKakaoLogins } = NativeModules;

const KakaoLogins = {
  login(callback) {
    RNKakaoLogins.login(callback);
  },
  logout(callback) {
    RNKakaoLogins.logout(callback);
  },
  getProfile(callback) {
    RNKakaoLogins.getProfile(callback);
  },
};

export default KakaoLogins;
