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
): Promise<KakaoOAuthWebToken> => {
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
    .map((k: any) => encodeURIComponent(k) + '=' + encodeURIComponent(data[k]))
    .join('&');

  try {
    const result = await fetch('https://kauth.kakao.com/oauth/token', {
      method: 'post',
      body: JSON.stringify(queryString),
      headers: {
        'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
      },
    });

    return result.json();
  } catch (err) {
    throw err;
  }
};

export const logout = async (tokenWeb?: string): Promise<string> => {
  try {
    const result = await fetch('https://kapi.kakao.com/v1/user/logout', {
      method: 'post',
      headers: {
        Authorization: `Bearer ${tokenWeb}`,
      },
    });

    return result.json();
  } catch (err) {
    throw err;
  }
};

export const unlink = async (tokenWeb?: string): Promise<string> => {
  try {
    const result = await fetch('https://kapi.kakao.com/v1/user/unlink', {
      method: 'post',
      headers: {
        Authorization: `Bearer ${tokenWeb}`,
      },
    });

    return result.json();
  } catch (err) {
    throw err;
  }
};

export const getProfile = async (
  token?: string,
): Promise<KakaoProfileWebType> => {
  try {
    const result = await fetch('https://kapi.kakao.com/v2/user/me', {
      method: 'get',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
      },
    });

    return result.json();
  } catch (err) {
    throw err;
  }
};
