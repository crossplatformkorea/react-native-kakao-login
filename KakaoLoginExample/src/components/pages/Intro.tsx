import {
  KakaoOAuthToken,
  KakaoProfile,
  getProfile as getKakaoProfile,
  login,
  logout,
  unlink,
} from '@react-native-seoul/kakao-login';
import React, {useState} from 'react';

import Button from '../uis/Button';
import {IC_MASK} from '../../utils/Icons';
import ResultView from '../uis/IntroTemp';
import {View} from 'react-native';
import styled from 'styled-components/native';
import {withScreen} from '../../utils/wrapper';

const Container = styled.View`
  flex: 1;
  align-self: stretch;
  overflow: scroll;
  background-color: ${({theme}) => theme.background};

  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  overflow: hidden;
`;

const ButtonWrapper = styled.View`
  position: absolute;
  flex-direction: column;
  bottom: 40px;
  width: 85%;
  align-self: center;
`;

function Intro(): React.ReactElement {
  const [result, setResult] = useState<string>('');

  const signInWithKakao = async (): Promise<void> => {
    const token: KakaoOAuthToken = await login();

    setResult(JSON.stringify(token));
  };

  const signOutWithKakao = async (): Promise<void> => {
    const message = await logout();

    setResult(message);
  };

  const getProfile = async (): Promise<void> => {
    const profile: KakaoProfile = await getKakaoProfile();

    setResult(JSON.stringify(profile));
  };

  const unlinkKakao = async (): Promise<void> => {
    const message = await unlink();

    setResult(message);
  };

  return (
    <Container>
      <ResultView result={result} />
      <ButtonWrapper>
        <Button
          testID="btn-login"
          style={{
            backgroundColor: '#FEE500',
            borderRadius: 40,
            borderWidth: 1,
          }}
          textStyle={{
            color: 'black',
            fontSize: 16,
          }}
          imgLeftSrc={IC_MASK}
          onPress={() => signInWithKakao()}
          text={'카카오 로그인'}
        />
        <View style={{marginTop: 12}} />
        <Button
          testID="btn-login"
          style={{
            backgroundColor: '#FEE500',
            borderRadius: 40,
            borderWidth: 1,
          }}
          textStyle={{
            color: 'black',
            fontSize: 16,
          }}
          imgLeftSrc={IC_MASK}
          onPress={() => getProfile()}
          text={'프로필 조회'}
        />
        <View style={{marginTop: 12}} />
        <Button
          testID="btn-login"
          style={{
            backgroundColor: '#FEE500',
            borderRadius: 40,
            borderWidth: 1,
          }}
          textStyle={{
            color: 'black',
            fontSize: 16,
          }}
          imgLeftSrc={IC_MASK}
          onPress={() => unlinkKakao()}
          text={'링크 해제'}
        />
        <View style={{marginTop: 12}} />
        <Button
          testID="btn-login"
          style={{
            backgroundColor: '#FEE500',
            borderRadius: 40,
            borderWidth: 1,
          }}
          textStyle={{
            color: 'black',
            fontSize: 16,
          }}
          imgLeftSrc={IC_MASK}
          onPress={() => signOutWithKakao()}
          text={'카카오 로그아웃'}
        />
        <View style={{marginTop: 40}} />
      </ButtonWrapper>
    </Container>
  );
}

export default withScreen(Intro);
