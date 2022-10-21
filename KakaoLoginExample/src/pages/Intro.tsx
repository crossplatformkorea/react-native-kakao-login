import { Pressable, StyleSheet, Text, View } from 'react-native';
import React, { useState } from 'react';
import { login, logout, getProfile as getKakaoProfile, unlink } from '@react-native-seoul/kakao-login';
import ResultView from './IntroView';

const Intro = () => {
  const [result, setResult] = useState<string>('');

  const signInWithKakao = async (): Promise<void> => {
    try {
      const token = await login();
      setResult(JSON.stringify(token));
    } catch (err) {
      console.error('login err', err);
    }
  };

  const signOutWithKakao = async (): Promise<void> => {
    try {
      const message = await logout();

      setResult(message);
    } catch (err) {
      console.error('signOut error', err);
    }
  };

  const getProfile = async (): Promise<void> => {
    try {
      const profile = await getKakaoProfile();

      setResult(JSON.stringify(profile));
    } catch (err) {
      console.error('signOut error', err);
    }
  };

  const unlinkKakao = async (): Promise<void> => {
    try {
      const message = await unlink();

      setResult(message);
    } catch (err) {
      console.error('signOut error', err);
    }
  };

  return (
    <View style={styles.container}>
      <ResultView result={result} />
      <Pressable
        style={styles.button}
        onPress={() => {
          signInWithKakao();
        }}
      >
        <Text style={styles.text}>
          카카오 로그인
        </Text>
      </Pressable>
      <Pressable
        style={styles.button}
        onPress={() => getProfile()}
      >
        <Text style={styles.text}>
          프로필 조회
        </Text>
      </Pressable>
      <Pressable
        style={styles.button}
        onPress={() => unlinkKakao()}
      >
        <Text style={styles.text}>
          링크 해제
        </Text>
      </Pressable>
      <Pressable
        style={styles.button}
        onPress={() => signOutWithKakao()}
      >
        <Text style={styles.text}>
          카카오 로그아웃
        </Text>
      </Pressable>
    </View>
  );
};

export default Intro;

const styles = StyleSheet.create({
  container: {
    height: "100%",
    justifyContent: "flex-end",
    alignItems: 'center',
    paddingBottom: 100
  },
  button: {
    backgroundColor: '#FEE500',
    borderRadius: 40,
    borderWidth: 1,
    width: 250,
    height: 40,
    paddingHorizontal: 20,
    paddingVertical: 10,
    marginTop: 10
  },
  text: {
    textAlign: "center"
  }
});