import { NativeModules } from 'react-native';

const { RNKakaoLogins } = NativeModules;

export interface ICallback<T> {
  (error: Error | undefined, result: T | undefined): void;
}

export interface ITokenInfo {
  token: string;
}

export interface IProfile {
  id: string;
  nickname: string;
  email: string | null;
  display_id: string | null;
  phone_number: string | null;
  email_verified: boolean;
  kakaotalk_user: boolean;
  profile_image_path: string | null;
  thumb_image_path: string | null;
  has_signed_up: boolean;
}

const processNativeOutput = (
  userCallback: ICallback<string | ITokenInfo | IProfile>,
) => {
  return (errorString: string, result: string): Error | void => {
    if (errorString) {
      const error = new Error(errorString);
      userCallback(error, undefined);
      return;
    }

    userCallback(undefined, result);
  };
};

const KakaoLogins = {
  /**
   * login
   * @param {callbackType} callback callback received from native module
   * @returns {void}
   */
  login(callback: ICallback<ITokenInfo>): void {
    RNKakaoLogins.login(processNativeOutput(callback));
  },

  /**
   * logout
   * @param {callbackType} callback callback received from native module
   * @returns {void}
   */
  logout(callback: ICallback<string>): void {
    RNKakaoLogins.logout(processNativeOutput(callback));
  },

  /**
   * getProfile
   * @param {callbackType} callback callback received from native module. id, nickname, email, display_id, phone_number, email_verified, kakatalk_user, profile_image_path, has_signed_up values will be received with json string in result field.
   * @returns {void}
   */
  getProfile(callback: ICallback<IProfile>): void {
    RNKakaoLogins.getProfile(processNativeOutput(callback));
  },
};

export default KakaoLogins;
