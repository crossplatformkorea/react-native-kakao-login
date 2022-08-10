import React, {useState} from 'react';
import {
  getProfile as getKakaoProfile,
  login,
  logout,
  unlink,
} from '@react-native-seoul/kakao-login';

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
  background-color: white;

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
    try {
      const token = await login();

      setResult(JSON.stringify(token));
    } catch (err) {
      // eslint-disable-next-line no-console
      console.error('login err', err);
    }
  };

  const signOutWithKakao = async (): Promise<void> => {
    try {
      const message = await logout();

      setResult(message);
    } catch (err) {
      // eslint-disable-next-line no-console
      console.error('signOut error', err);
    }
  };

  const getProfile = async (): Promise<void> => {
    try {
      const profile = await getKakaoProfile();

      setResult(JSON.stringify(profile));
    } catch (err) {
      // eslint-disable-next-line no-console
      console.error('signOut error', err);
    }
  };

  const unlinkKakao = async (): Promise<void> => {
    try {
      const message = await unlink();

      setResult(message);
    } catch (err) {
      // eslint-disable-next-line no-console
      console.error('signOut error', err);
    }
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
        {/* @ts-ignore */}
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
        {/* @ts-ignore */}
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
        {/* @ts-ignore */}
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
        {/* @ts-ignore */}
        <View style={{marginTop: 40}} />
      </ButtonWrapper>
    </Container>
  );
}

export default withScreen(Intro);
