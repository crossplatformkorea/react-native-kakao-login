import {NativeModules} from 'react-native';
import {KakaoLoginModuleInterface} from './types';

const {RNKakaoLogins} = NativeModules;

const NativeKakaoLogins: KakaoLoginModuleInterface = {
  login() {
    return RNKakaoLogins.login();
  },
  loginWithKakaoAccount() {
    return RNKakaoLogins.loginWithKakaoAccount();
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
