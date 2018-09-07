import { NativeModules } from 'react-native';

const { RNKakaoLogins } = NativeModules;

const processNativeOutput = (userCallback) => {
  return (errorString, resultString) => {
    let error;
    let resultJSON;

    if (errorString) {
      error = new Error(errorString);
      userCallback(error, undefined);

      return;
    }

    try {
      resultJSON = JSON.parse(resultString);
    } catch (_) {
      error = new Error(resultString);
      userCallback(error, undefined);

      return;
    }

    userCallback(undefined, resultJSON);
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
