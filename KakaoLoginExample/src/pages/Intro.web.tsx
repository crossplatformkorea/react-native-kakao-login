import React, {useEffect, useState} from 'react';
import {useLocation} from 'react-router-dom';
import {View} from 'react-native';
import {
  login,
  getProfile as getKakaoProfile,
  logout,
  unlink,
} from '@react-native-seoul/kakao-login';
import ResultViewWeb from './ResultViewWeb';

const RestApiKey = 'aa0293e157b3b865a943deade9a3c1a8';
const redirectUrl = 'http://localhost:3000';
const kakaoUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${RestApiKey}&redirect_uri=${redirectUrl}&response_type=code`;

function Login() {
  const location = useLocation();
  const [token, setToken] = useState<string>('');
  const [type, setType] = useState<'login' | 'profile' | 'logout' | 'unlink'>();
  const [state, setState] = useState<any>();

  const signInWithKakao = async (codeWeb: string): Promise<void> => {
    try {
      const {access_token} = await login({
        restApiKeyWeb: RestApiKey,
        redirectUrlWeb: redirectUrl,
        codeWeb,
      });
      setType('login');
      setState(access_token);
      setToken(access_token);
    } catch (error) {
      console.log(error, 'error');
    }
  };

  const signOutWithKakao = async (tokenWeb: string): Promise<void> => {
    if (!tokenWeb) {
      alert('로그인을 먼저 진행해 주세요');
      return;
    }
    try {
      const message = await logout(tokenWeb);
      setType('logout');
      setState(message);
      setToken('');
    } catch (err) {
      console.error('signOut error', err);
    }
  };

  const getProfile = async (tokenWeb: string): Promise<void> => {
    if (!tokenWeb) {
      alert('로그인을 먼저 진행해 주세요');
      return;
    }
    try {
      const data = await getKakaoProfile(tokenWeb);
      setType('profile');
      setState(data.properties);
    } catch (error) {
      console.log(error, 'error');
    }
  };

  const unlinkKakao = async (tokenWeb: string): Promise<void> => {
    if (!tokenWeb) {
      alert('로그인을 먼저 진행해 주세요');
      return;
    }
    try {
      const message = await unlink(tokenWeb);
      setType('unlink');
      setState(message);
      setToken('');
    } catch (err) {
      console.error('signOut error', err);
    }
  };

  useEffect(() => {
    if (location.search) {
      signInWithKakao(location.search.split('code=')[1]);
    }
  }, []);

  return (
    <View
      style={{
        width: 786,
        height: '100vh',
        justifyContent: 'center',
        alignItems: 'center',
        alignSelf: 'center',
      }}>
      <ResultViewWeb type={type} state={state} />
      <a
        href={kakaoUrl}
        style={{
          minWidth: 250,
          padding: '10px 0px',
          borderRadius: 5,
          backgroundColor: '#F1DC11',
          fontSize: 20,
          textDecoration: 'none',
          textAlign: 'center',
        }}>
        카카오 로그인
      </a>
      <button
        onClick={() => getProfile(token)}
        style={{
          minWidth: 250,
          marginTop: 10,
          padding: '10px 20px',
          borderRadius: 5,
          backgroundColor: '#F1DC11',
          fontSize: 20,
          border: 'none',
          cursor: 'pointer',
        }}>
        프로필 조회
      </button>
      <button
        onClick={() => unlinkKakao(token)}
        style={{
          minWidth: 250,
          marginTop: 10,
          padding: '10px 20px',
          borderRadius: 5,
          backgroundColor: '#F1DC11',
          fontSize: 20,
          border: 'none',
          cursor: 'pointer',
        }}>
        링크 해제
      </button>
      <button
        onClick={() => signOutWithKakao(token)}
        style={{
          minWidth: 250,
          marginTop: 10,
          padding: '10px 20px',
          borderRadius: 5,
          backgroundColor: '#F1DC11',
          fontSize: 20,
          border: 'none',
          cursor: 'pointer',
        }}>
        로그아웃
      </button>
    </View>
  );
}

export default Login;
