import {NativeModules} from 'react-native';
import {KakaoLoginModuleInterface, KakaoLoginOptions, KaKaoLoginWebType} from './types';

const {RNKakaoLogins} = NativeModules;

const NativeKakaoLogins: KakaoLoginModuleInterface = {
  login(props?: KakaoLoginOptions | KaKaoLoginWebType) {
    if (!props) {
      return RNKakaoLogins.login(null);
    }
    
    const kakaoLoginOptions = props as KakaoLoginOptions;
    
    return RNKakaoLogins.login(kakaoLoginOptions.nonce || null);
  },
  loginWithKakaoAccount(props?: KakaoLoginOptions) {
    if (!props) {
      return RNKakaoLogins.loginWithKakaoAccount(null);
    }
    
    return RNKakaoLogins.loginWithKakaoAccount(props.nonce || null);
  },
  logout() {
    return RNKakaoLogins.logout();
  },
  unlink() {
    return RNKakaoLogins.unlink();
  },
  getProfile() {
    return RNKakaoLogins.getProfile();
  },
  getAccessToken() {
    return RNKakaoLogins.getAccessToken();
  },
  shippingAddresses() {
    return RNKakaoLogins.shippingAddresses();
  },
  serviceTerms() {
    return RNKakaoLogins.serviceTerms();
  },
};

export const login = NativeKakaoLogins.login;
export const loginWithKakaoAccount = NativeKakaoLogins.loginWithKakaoAccount;
export const logout = NativeKakaoLogins.logout;
export const unlink = NativeKakaoLogins.unlink;
export const getProfile = NativeKakaoLogins.getProfile;
export const getAccessToken = NativeKakaoLogins.getAccessToken;
export const shippingAddresses = NativeKakaoLogins.shippingAddresses;
export const serviceTerms = NativeKakaoLogins.serviceTerms;

export * from './types';
