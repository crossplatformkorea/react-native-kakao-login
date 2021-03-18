import KakaoLogins, {KAKAO_AUTH_TYPES} from '@react-native-seoul/kakao-login';
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

  const signInWithKakao = (): void => {
    KakaoLogins.login([KAKAO_AUTH_TYPES.Talk, KAKAO_AUTH_TYPES.Account])
      .then((signInResult) => {
        setResult(JSON.stringify(signInResult));
      })
      .catch((err) => {
        console.log(`Sign in error: ${err.message}`);
      });
  };

  const signOutWithKakao = (): void => {
    KakaoLogins.logout()
      .then((signOutResult) => {
        setResult(JSON.stringify(signOutResult));
      })
      .catch((err) => {
        console.log(`Sign out error: ${err.message}`);
      });
  };

  const getProfile = (): void => {
    KakaoLogins.getProfile()
      .then((profileResult) => {
        setResult(JSON.stringify(profileResult));
      })
      .catch((err) => {
        console.log(`Sign out error: ${err.message}`);
      });
  };

  const unlinkKakao = (): void => {
    KakaoLogins.unlink()
      .then((unlinkResult) => {
        setResult(JSON.stringify(unlinkResult));
      })
      .catch((err) => {
        console.log(`Sign out error: ${err.message}`);
      });
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
          onPress={(): void => signInWithKakao()}
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
          onPress={(): void => getProfile()}
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
          onPress={(): void => unlinkKakao()}
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
          onPress={(): void => signOutWithKakao()}
          text={'카카오 로그아웃'}
        />
        <View style={{marginTop: 40}} />
      </ButtonWrapper>
    </Container>
  );
}

export default withScreen(Intro);
