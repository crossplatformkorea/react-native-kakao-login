import { NativeModules } from 'react-native';

const { RNKakaoLogins } = NativeModules;

const processNativeOutput = (userCallback) => {
  return (errorString, result) => {
    if (errorString) {
      error = new Error(errorString);
      userCallback(error, undefined);
      return;
    }

    userCallback(undefined, result);
  };
}

const KakaoLogins = {
  login(callback) {
    RNKakaoLogins.login(processNativeOutput(callback));
  },
  logout(callback) {
    RNKakaoLogins.logout(processNativeOutput(callback));
  },
  getProfile(callback) {
    RNKakaoLogins.getProfile(processNativeOutput(callback));
  },
};

export default KakaoLogins;
