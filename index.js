import { NativeModules } from 'react-native';

const { RNKakaoLogins } = NativeModules;

const processNativeOutput = (userCallback) => {
  return (errorString, resultString) => {
    let error;

    if (errorString) {
      error = new Error(errorString);
    }

    const resultJSON = JSON.parse(resultString);

    userCallback(error, resultJSON);
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
