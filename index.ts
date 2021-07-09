import {NativeModules} from 'react-native';

const {RNKakaoLogins} = NativeModules;

export type KakaoOAuthToken = {
  accessToken: string;
  refreshToken: string;
  accessTokenExpiresAt: Date;
  refreshTokenExpiresAt: Date;
  scopes: string[];
};

export type KakaoAccessTokenInfo = {
  accessToken: string;
  expiresIn: string;
};

export type KakaoProfile = {
  id: string;
  email: string;
  nickname: string;
  profileImageUrl: string;
  thumbnailImageUrl: string;
  phoneNumber: string;
  ageRange: string;
  birthDay: string;
  birthdayType: string;
  birthyear: string;
  gender: string;
  isEmailValid: boolean;
  isEmailVerified: boolean;
  isKorean: boolean;
  ageRangeNeedsAgreement?: boolean;
  birthdayNeedsAgreement?: boolean;
  birthyearNeedsAgreement?: boolean;
  emailNeedsAgreement?: boolean;
  genderNeedsAgreement?: boolean;
  isKoreanNeedsAgreement?: boolean;
  phoneNumberNeedsAgreement?: boolean;
  profileNeedsAgreement?: boolean;
};

export const login = async (): Promise<KakaoOAuthToken> => {
  try {
    const result: KakaoOAuthToken = await RNKakaoLogins.login();

    return result;
  } catch (err) {
    throw err;
  }
};

export const loginWithKakaoAccount = async (): Promise<KakaoOAuthToken> => {
  try {
    const result: KakaoOAuthToken = await RNKakaoLogins.loginWithKakaoAccount();

    return result;
  } catch (err) {
    throw err;
  }
};

export const logout = async (): Promise<string> => {
  try {
    const result: string = await RNKakaoLogins.logout();

    return result;
  } catch (err) {
    throw err;
  }
};

export const unlink = async (): Promise<string> => {
  try {
    const result: string = await RNKakaoLogins.unlink();

    return result;
  } catch (err) {
    throw err;
  }
};

export const getProfile = async (): Promise<KakaoProfile> => {
  try {
    const result: KakaoProfile = await RNKakaoLogins.getProfile();

    return result;
  } catch (err) {
    throw err;
  }
};

export const getAccessToken = async (): Promise<KakaoAccessTokenInfo> => {
  try {
    const result: KakaoAccessTokenInfo = await RNKakaoLogins.getAccessToken();

    return result;
  } catch (err) {
    throw err;
  }
};
