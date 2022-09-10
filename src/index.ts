import {NativeModules, Platform} from 'react-native';

const {RNKakaoLogins} = NativeModules;

export type KakaoOAuthToken = {
  accessToken: string;
  refreshToken: string;
  idToken: string;
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
  birthday: string;
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

export type KakaoProfileNoneAgreement = {
  id: string;
};
export type KakaoOAuthWebToken = {
  access_token: string;
  expires_in: number;
  refresh_token: string;
  refresh_token_expires_in: number;
  scope: string;
  token_type: string;
};

export type KaKaoLoginWebType = {
  restApiKeyWeb?: string;
  redirectUrlWeb?: string;
  codeWeb?: string;
};

export type KakaoProfileWebType = {
  properties: {
    nickname: string;
    profile_image: string;
    thumbnail_image: string;
  };
};

export const login = async (
  props?: KaKaoLoginWebType,
): Promise<KakaoOAuthWebToken | KakaoOAuthToken> => {
  try {
    if (Platform.OS === 'web') {
      if (!props) {
        throw new Error('Web parameters are not provided');
      }

      const {restApiKeyWeb, redirectUrlWeb, codeWeb} = props;

      if (!restApiKeyWeb || !redirectUrlWeb || !codeWeb) {
        throw new Error('Web parameters are not provided');
      }

      const data: any = {
        grant_type: 'authorization_code',
        client_id: restApiKeyWeb,
        redirect_uri: redirectUrlWeb,
        code: codeWeb,
      };

      const queryString = Object.keys(data)
        .map(
          (k: any) => encodeURIComponent(k) + '=' + encodeURIComponent(data[k]),
        )
        .join('&');

      const result = await fetch('https://kauth.kakao.com/oauth/token', {
        method: 'post',
        body: queryString,
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
        },
      });

      return result.json();
    }

    return RNKakaoLogins.login();
  } catch (err) {
    throw err;
  }
};

export const loginWithKakaoAccount = async (): Promise<KakaoOAuthToken> => {
  if (Platform.OS === 'web') {
    throw new Error('Web does not support `loginWithKakaoAccount`');
  }

  try {
    const result: KakaoOAuthToken = await RNKakaoLogins.loginWithKakaoAccount();

    return result;
  } catch (err) {
    throw err;
  }
};

export const logout = async (tokenWeb?: string): Promise<string> => {
  try {
    if (Platform.OS === 'web') {
      const result = await fetch('https://kapi.kakao.com/v1/user/logout', {
        method: 'post',
        headers: {
          Authorization: `Bearer ${tokenWeb}`,
        },
      });

      return result.json();
    }

    return RNKakaoLogins.logout();
  } catch (err) {
    throw err;
  }
};

export const unlink = async (tokenWeb?: string): Promise<string> => {
  try {
    if (Platform.OS === 'web') {
      const result = await fetch('https://kapi.kakao.com/v1/user/unlink', {
        method: 'post',
        headers: {
          Authorization: `Bearer ${tokenWeb}`,
        },
      });

      return result.json();
    }

    return RNKakaoLogins.unlink();
  } catch (err) {
    throw err;
  }
};

export const getProfile = async (
  token?: string,
): Promise<KakaoProfileWebType> => {
  try {
    if (Platform.OS === 'web') {
      const result = await fetch('https://kapi.kakao.com/v2/user/me', {
        method: 'get',
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
        },
      });

      return result.json();
    }

    return RNKakaoLogins.getProfile();
  } catch (err) {
    throw err;
  }
};

export const getAccessToken = async (): Promise<KakaoAccessTokenInfo> => {
  if (Platform.OS === 'web') {
    throw new Error('Web does not support `getAccessToken`');
  }

  try {
    const result: KakaoAccessTokenInfo = await RNKakaoLogins.getAccessToken();

    return result;
  } catch (err) {
    throw err;
  }
};
