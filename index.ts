import { NativeModules } from "react-native";
const { RNKakaoLogins } = NativeModules;

export interface ICallback<T> {
  (error: Error | undefined, result: T | undefined): void;
}

export interface ITokenInfo {
  accessTokenExpiresAt: string;
  refreshToken: string;
  refreshTokenExpiresAt: string | null;
  accessToken: string;
  scopes?: string[]; // iOS only
}

export interface IProfile {
  id: string;
  nickname: string | null;
  profile_image_url: string | null;
  thumb_image_url: string | null;
  email: string | null;
  display_id: string | null;
  phone_number: string | null;
  is_email_verified: boolean | null;
  is_kakaotalk_user: boolean | null;
  has_signed_up: boolean | null;
  birthday: string | null;
  gender: string | null;
  age_range: string | null;
}

/**
 * KAKAO_ERROR 에러 코드
 * 모든 에러코드가 등록되어있지는 않습니다. 아래의 링크를 참조하세요
 *
 * SHARED   : 공통 에러
 * IOS      : iOS 에러코드
 * ANDROID  : Android 에러코드
 *
 * @url Android : https://developers.kakao.com/docs/android-reference/com/kakao/auth/ApiErrorCode.html
 * @url iOS     : https://developers.kakao.com/docs/ios-reference/KOError_h/index.html#//apple_ref/c/tdef/KOErrorCode
 * */
export enum KAKAO_ERROR {
  // SHARED
  E_UNKNOWN = "E_UNKNOWN",
  E_CANCELLED_OPERATION = "E_CANCELLED_OPERATION",
  E_ILLEGAL_STATE = "E_ILLEGAL_STATE",

  // IOS
  E_IN_PROGRESS_OPERATION = "E_IN_PROGRESS_OPERATION",
  E_TOKEN_NOT_FOUND = "E_TOKEN_NOT_FOUND",
  E_DEACTIVATED_SESSION = "E_DEACTIVATED_SESSION",
  E_ALREADY_LOGINED = "E_ALREADY_LOGINED",
  E_HTTP_ERROR = "E_HTTP_ERROR",
  E_BAD_RESPONSE = "E_BAD_RESPONSE",
  E_NETWORK_ERROR = "E_NETWORK_ERROR",
  E_NOT_SUPPORTED = "E_NOT_SUPPORTED",
  E_BAD_PARAMETER = "E_BAD_PARAMETER",

  // ANDROID
  E_ILLEGAL_ARGUMENT = "E_ILLEGAL_ARGUMENT",
  E_MISS_CONFIGURATION = "E_MISS_CONFIGURATION",
  E_AUTHORIZATION_FAILED = "E_AUTHORIZATION_FAILED",
  E_JSON_PARSING_ERROR = "E_JSON_PARSING_ERROR",
  E_URI_LENGTH_EXCEEDED = "E_URI_LENGTH_EXCEEDED",
  E_KAKAOTALK_NOT_INSTALLED = "E_KAKAOTALK_NOT_INSTALLED",
}

/**
 * KAKAO_AUTH_TYPES 인증 수단
 * iOS 기준의 값이며, Android도 지원합니다
 *
 * @url Android : https://developers.kakao.com/sdk/reference/android-legacy/release/index.html
 * @url iOS     : https://developers.kakao.com/sdk/reference/ios-legacy/release/Constants/KOAuthType.html
 */
export enum KAKAO_AUTH_TYPES {
  Talk = 2,
  Story = 4,
  Account = 8,
}

function isFunction(item): boolean {
  return item ? typeof item === "function" : false;
}

/**
 * login
 * @param {ICallback<ITokenInfo>} [callback] callback function
 * @param {Array<KAKAO_AUTH_TYPES>} authTypes authorization methods array
 * @returns {Promise<ITokenInfo>}
 */
export function login(
  callback?: ICallback<ITokenInfo>,
  authTypes?: Array<KAKAO_AUTH_TYPES>
): Promise<ITokenInfo> {
  const authTypesWithDefault = authTypes || [];
  return RNKakaoLogins.login(authTypesWithDefault)
    .then((result: ITokenInfo) => {
      const timeReFormattedResult = {
        ...result,
        accessTokenExpiresAt: result.accessTokenExpiresAt.replace(" ", "T"),
        refreshTokenExpiresAt: result.refreshTokenExpiresAt.replace(" ", "T"),
      };
      if (isFunction(callback)) {
        callback(null, timeReFormattedResult);
      }

      return timeReFormattedResult;
    })
    .catch((error) => {
      if (isFunction(callback)) {
        callback(error, null);
      }

      throw error;
    });
}

/**
 * logout
 * @param {ICallback<string>} [callback] callback function
 * @returns {Promise<string>}
 */
export function logout(callback?: ICallback<string>): Promise<string> {
  return RNKakaoLogins.logout()
    .then((result) => {
      if (isFunction(callback)) {
        callback(null, result);
      }

      return result;
    })
    .catch((error) => {
      if (isFunction(callback)) {
        callback(error, null);
      }

      throw error;
    });
}

/**
 * getProfile
 * @param {ICallback<IProfile>} [callback] callback function. id, nickname, email, display_id, phone_number, email_verified, kakatalk_user, profile_image_path, has_signed_up values will be received with json string in result field.
 * @returns {Promise<ITokenInfo>}
 */
export function getProfile(callback?: ICallback<IProfile>): Promise<IProfile> {
  return RNKakaoLogins.getProfile()
    .then((result: IProfile) => {
      if (isFunction(callback)) {
        callback(null, result);
      }

      return result;
    })
    .catch((error) => {
      if (isFunction(callback)) {
        callback(error, null);
      }

      throw error;
    });
}

/**
 * unlink
 * @param {ICallback<string>} [callback] callback function
 * @returns {Promise<string>}
 */
export function unlink(callback?: ICallback<string>): Promise<string> {
  return RNKakaoLogins.unlink()
    .then((result) => {
      if (isFunction(callback)) {
        callback(null, result);
      }

      return result;
    })
    .catch((error) => {
      if (isFunction(callback)) {
        callback(error, null);
      }

      throw error;
    });
}

/**
 * updateScopes
 * @param {Array<string>} scopes request scopes
 * @param {ICallback<ITokenInfo>} [callback] callback function
 * @returns {Promise<ITokenInfo>}
 */
export function updateScopes(
  scopes: Array<string>,
  callback?: ICallback<ITokenInfo>
): Promise<ITokenInfo> {
  return RNKakaoLogins.updateScopes(scopes)
    .then((result) => {
      if (isFunction(callback)) {
        callback(null, result);
      }

      return result;
    })
    .catch((error) => {
      if (isFunction(callback)) {
        callback(error, null);
      }

      throw error;
    });
}

export function getTokens(
  callback?: ICallback<{ accessToken: string; refreshToken: string }>
): Promise<{ accessToken: string; refreshToken: string }> {
  return RNKakaoLogins.getTokens()
    .then((result) => {
      if (isFunction(callback)) {
        callback(null, result);
      }
      return result;
    })
    .catch((error) => {
      if (isFunction(callback)) {
        callback(error, null);
      }
      throw error;
    });
}

const KakaoLogins = {
  login,
  logout,
  getProfile,
  unlink,
  updateScopes,
  getTokens,
};

export default KakaoLogins;
